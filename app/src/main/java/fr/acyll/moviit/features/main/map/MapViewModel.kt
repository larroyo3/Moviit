package fr.acyll.moviit.features.main.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import fr.acyll.moviit.domain.model.Memories
import fr.acyll.moviit.domain.model.ShootingPlace
import fr.acyll.moviit.domain.repository.MovieRepository
import fr.acyll.moviit.features.main.home.HomeEffect
import fr.acyll.moviit.features.main.home.HomeEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MapViewModel(
    private val movieRepository: MovieRepository,
): ViewModel() {

    private val _state = MutableStateFlow(MapState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<MapEffect>()
    val effect = _effect.asSharedFlow()

    init {
        _state.update { it.copy(isLoading = true) }
        getShootingPlaces()
    }

    fun onEvent(event: MapEvent) {
        when (event) {
            is MapEvent.OnMarkerClick -> {
                _state.update {
                    it.copy(
                        showMarkerBottomSheet = true,
                        selectedShootingPlace = event.value
                    )
                }
            }

            is MapEvent.OnQueryChange -> {
                _state.update {
                    it.copy(
                        query = event.value
                    )
                }

                searchMoviesByTitle(event.value)
            }

            is MapEvent.OnSearch -> {
                _state.update {
                    it.copy(
                        query = event.value,
                        active = false
                    )
                }

                getShootingPLacesByFilmTitle(event.value)
                searchMoviesByTitle(event.value)
            }

            is MapEvent.OnDeleteQuery -> {
                _state.update {
                    it.copy(
                        query = "",
                    )
                }
            }

            is MapEvent.OnCloseSearch -> {
                _state.update {
                    it.copy(
                        active = false,
                    )
                }

                getShootingPlaces()
            }

            is MapEvent.OnActiveChange -> {
                _state.update {
                    it.copy(
                        active = event.value,
                    )
                }

                if (!_state.value.active) {
                    getShootingPlaces()
                    _state.update {
                        it.copy(
                            query = "",
                        )
                    }
                }
            }

            is MapEvent.OnDismissMarkerBottomSheet -> {
                _state.update {
                    it.copy(
                        showMarkerBottomSheet = false,
                        selectedShootingPlace = null
                    )
                }
            }

            is MapEvent.OnPublishClick -> {
                emitEffect(MapEffect.GoToPublishPage(event.value))
            }
        }
    }

    private fun emitEffect(effect: MapEffect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }

    private fun getShootingPlaces() {
        val shootingPlacesCollection = Firebase.firestore.collection("shooting_place")
        shootingPlacesCollection.get()
            .addOnSuccessListener { result ->
                val shootingPlaces: MutableList<ShootingPlace> = mutableListOf()

                for (document in result) {
                    val shootingPlace = document.toObject<ShootingPlace>()
                    shootingPlaces.add(shootingPlace.copy(id = document.id))
                }

                _state.update {
                    it.copy(
                        shootingPlaces = shootingPlaces,
                        isLoading = false
                    )
                }
            }
            .addOnFailureListener { exception ->
                emitEffect(MapEffect.ShowError(exception))
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
                        emitEffect(MapEffect.ShowError(it))
                    }
                }
        }
    }

    private fun getShootingPLacesByFilmTitle(filmTitle: String) {
        val shootingPlaceCollection = Firebase.firestore.collection("shooting_place")
        shootingPlaceCollection
            .whereEqualTo("title", filmTitle)
            .get()
            .addOnSuccessListener { result ->
                val shootingPlaces: MutableList<ShootingPlace> = mutableListOf()
                if (!result.isEmpty) {
                    for (document in result) {
                        val shootingPlace = document.toObject<ShootingPlace>()
                        shootingPlaces.add(shootingPlace.copy(id = document.id))
                    }

                    _state.update {
                        it.copy(
                            shootingPlaces = shootingPlaces
                        )
                    }
                }
            }
            .addOnFailureListener { exception ->
                emitEffect(MapEffect.ShowError(exception))
            }
    }
}