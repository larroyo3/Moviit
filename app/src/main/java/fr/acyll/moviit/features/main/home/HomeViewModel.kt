package fr.acyll.moviit.features.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import fr.acyll.moviit.domain.model.Memories
import fr.acyll.moviit.domain.repository.MovieRepository
import fr.acyll.moviit.features.contribute.ContributeEffect
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val movieRepository: MovieRepository,
): ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<HomeEffect>()
    val effect = _effect.asSharedFlow()

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnQueryChange -> {
                _state.update {
                    it.copy(
                        query = event.value
                    )
                }

                searchMoviesByTitle(event.value)
            }

            is HomeEvent.OnSearch -> {
                _state.update {
                    it.copy(
                        query = event.value,
                        active = false
                    )
                }

                getPublicationsByFilmTitle(event.value)
                searchMoviesByTitle(event.value)
            }

            is HomeEvent.OnDeleteQuery -> {
                _state.update {
                    it.copy(
                        query = "",
                    )
                }
            }

            is HomeEvent.OnCloseSearch -> {
                _state.update {
                    it.copy(
                        active = false,
                    )
                }

                getPublications()
            }

            is HomeEvent.OnActiveChange -> {
                _state.update {
                    it.copy(
                        active = event.value,
                    )
                }

                if (!_state.value.active) {
                    getPublications()
                    _state.update {
                        it.copy(
                            query = "",
                        )
                    }
                }
            }
        }
    }

    fun refreshData() {
        _state.update { it.copy(isLoading = true) }
        getPublications()
    }

    private fun emitEffect(effect: HomeEffect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }

    private fun getPublications() {
        val publicationCollection = Firebase.firestore.collection("memories")
        publicationCollection.get()
            .addOnSuccessListener { result ->
                val memories: MutableList<Memories> = mutableListOf()
                for (document in result) {
                    memories.add(document.toObject(Memories::class.java))
                }

                _state.update {
                    it.copy(
                        memories = memories.sortedByDescending { date -> date.creationDate },
                        isLoading = false
                    )
                }
            }
            .addOnFailureListener { exception ->
                emitEffect(HomeEffect.ShowError(exception))
            }
    }

    private fun getPublicationsByFilmTitle(filmTitle: String) {
        val shootingPlaceCollection = Firebase.firestore.collection("shooting_place")
        shootingPlaceCollection
            .whereEqualTo("title", filmTitle)
            .get()
            .addOnSuccessListener { result ->
                val shootingPlaceIds: MutableList<String> = mutableListOf()
                if (!result.isEmpty) {
                    for (document in result) {
                        val shootingPlaceId = document.id
                        shootingPlaceIds.add(shootingPlaceId)
                    }

                    val publicationCollection = Firebase.firestore.collection("memories")
                    publicationCollection
                        .whereIn(
                            "shootingPlaceId",
                            shootingPlaceIds
                        )
                        .get()
                        .addOnSuccessListener { results ->
                            val memories: MutableList<Memories> = mutableListOf()
                            for (document in results) {
                                memories.add(document.toObject(Memories::class.java))
                            }

                            _state.update {
                                it.copy(
                                    memories = memories.sortedByDescending { date -> date.creationDate },
                                    isLoading = false
                                )
                            }
                        }
                        .addOnFailureListener { exception ->
                            emitEffect(HomeEffect.ShowError(exception))
                        }
                }
            }
            .addOnFailureListener { exception ->
                emitEffect(HomeEffect.ShowError(exception))
            }
    }

    private fun searchMoviesByTitle(query: String) {
        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            movieRepository.searchMoviesByTitle(query)
                .collect { result ->
                    result.onSuccess { data ->
                        _state.update {
                            it.copy(
                                isLoading = false,
                                searchResult = data.take(10)
                            )
                        }
                    }
                    result.onFailure {
                        emitEffect(HomeEffect.ShowError(it))
                    }
                }
        }
    }
}