package fr.acyll.moviit.features.main.settings

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.identity.Identity
import fr.acyll.moviit.features.onboarding.auth.GoogleAuthUiClient
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    context: Context
): ViewModel() {
    private val _state = MutableStateFlow(SettingsState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<SettingsEffect>()
    val effect = _effect.asSharedFlow()

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = context,
            oneTapClient = Identity.getSignInClient(context)
        )
    }

    fun onEvent(even: SettingsEvent) {
        when (even) {
            is SettingsEvent.OnLogOutClick -> {
                viewModelScope.launch {
                    googleAuthUiClient.signOut()
                    emitEffect(SettingsEffect.RedirectToOnboarding)
                }
            }
        }
    }

    private fun emitEffect(effect: SettingsEffect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }
}