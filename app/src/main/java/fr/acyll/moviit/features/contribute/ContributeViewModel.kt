package fr.acyll.moviit.features.contribute

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.identity.Identity
import fr.acyll.moviit.features.main.settings.SettingsEffect
import fr.acyll.moviit.features.main.settings.SettingsEvent
import fr.acyll.moviit.features.main.settings.SettingsState
import fr.acyll.moviit.features.onboarding.auth.GoogleAuthUiClient
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/*
class ContributeViewModel: ViewModel() {

    private val _state = MutableStateFlow(ContributeState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<ContributeEffect>()
    val effect = _effect.asSharedFlow()

    fun onEvent(event: ContributeEvent) {
        when (event) {
            is ContributeEvent.OnMovieTitleChange -> {
                _state.update { it.copy(publication = it.publication.copy(movieTitle = event.value)) }
            }

            is ContributeEvent.OnMovieDirectorChange -> _state.update { it.copy(
                    publication = it.publication.copy(
                        movieDirector = event.value
                    )
                )
            }

            is ContributeEvent.OnMovieReleaseDateChange -> {
                _state.update { it.copy(publication = it.publication.copy(releaseDate = event.value)) }
            }

            is ContributeEvent.OnSynopsisChange -> {
                _state.update { it.copy(publication = it.publication.copy(synopsis = event.value)) }
            }

            is ContributeEvent.OnTitleChange -> {
                _state.update { it.copy(publication = it.publication.copy(title = event.value)) }
            }

            is ContributeEvent.OnDescriptionChange -> {
                _state.update { it.copy(publication = it.publication.copy(description = event.value)) }
            }

            is ContributeEvent.OnLatitudeChange -> {
                _state.update { it.copy(publication = it.publication.copy(latitude = event.value)) }
            }

            is ContributeEvent.OnLongitudesChange -> {
                _state.update { it.copy(publication = it.publication.copy(longitude = event.value)) }
            }

            is ContributeEvent.OnAddClick -> {

            }
        }
    }

    private fun emitEffect(effect: ContributeEffect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }
}

 */