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
import kotlinx.coroutines.launch

class ContributeViewModel: ViewModel() {

    private val _state = MutableStateFlow(ContributeState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<ContributeEffect>()
    val effect = _effect.asSharedFlow()

    fun onEvent(even: ContributeEvent) {
        when (even) {
            is ContributeEvent.OnTitleChange -> {

            }
        }
    }

    private fun emitEffect(effect: ContributeEffect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }
}