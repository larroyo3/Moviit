package fr.acyll.moviit.features.contribute

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import fr.acyll.moviit.domain.repository.MovieRepository
import fr.acyll.moviit.features.onboarding.auth.GoogleAuthUiClient
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.internal.userAgent

class ContributeViewModel(
    private val movieRepository: MovieRepository,
    context: Context
): ViewModel() {

    private val _state = MutableStateFlow(ContributeState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<ContributeEffect>()
    val effect = _effect.asSharedFlow()

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = context,
            oneTapClient = Identity.getSignInClient(context)
        )
    }

    init {
        _state.update {
            it.copy(
                userData = googleAuthUiClient.getSignedInUser()
            )
        }
    }

    fun onEvent(event: ContributeEvent) {
        when (event) {
            is ContributeEvent.OnSearchQueryChange -> {
                _state.update {
                    it.copy(
                        shootingPlace = it.shootingPlace.copy(
                            title = event.value
                        )
                    )
                }

                searchMoviesByTitle(event.value)
            }

            is ContributeEvent.OnSelectMovie -> {
                getMoviesById(event.value.id)
            }

            is ContributeEvent.OnLatitudeChange -> {
                _state.update {
                    it.copy(
                        shootingPlace = it.shootingPlace.copy(
                            latitude = event.value
                        )
                    )
                }
            }

            is ContributeEvent.OnPlaceChange -> {
                _state.update {
                    it.copy(
                        shootingPlace = it.shootingPlace.copy(
                            place = event.value
                        )
                    )
                }
            }

            is ContributeEvent.OnLongitudesChange -> {
                _state.update {
                    it.copy(
                        shootingPlace = it.shootingPlace.copy(
                            longitude = event.value
                        )
                    )
                }
            }

            is ContributeEvent.OnAddClick -> {
                publishMemory()
            }
        }
    }

    private fun emitEffect(effect: ContributeEffect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }

    private fun publishMemory() {
        val memoriesCollection = Firebase.firestore.collection("shooting_place")

        val selectedMovie = _state.value.selectedMovie
        memoriesCollection.add(
            _state.value.shootingPlace.copy(
                title = selectedMovie?.title ?: "",
                director = selectedMovie?.credits?.crew?.find { it.job == "Director" }?.name ?: "",
                releaseDate = selectedMovie?.releaseDate ?: "",
                synopsis = selectedMovie?.overview ?: "",
                contributorId = _state.value.userData?.userId ?: "",
                moviePosterUrl = selectedMovie?.poster
            )
        )
            .addOnSuccessListener {
                _state.update { it.copy(isLoading = false) }
                emitEffect(ContributeEffect.NavigateBack)
            }.addOnFailureListener { e ->
                _state.update { it.copy(isLoading = false) }
                emitEffect(ContributeEffect.ShowError(e))
            }
    }

    private fun getMoviesById(id: Int) {
        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            movieRepository.getMoviesById(id)
                .collect { result ->
                    result.onSuccess { data ->
                        _state.update {
                            it.copy(
                                isLoading = false,
                                selectedMovie = data
                            )
                        }
                    }
                    result.onFailure { e ->
                        _state.update { it.copy(isLoading = false) }
                        emitEffect(ContributeEffect.ShowError(e))
                    }
                }
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
                        emitEffect(ContributeEffect.ShowError(it))
                    }
                }
        }
    }
}