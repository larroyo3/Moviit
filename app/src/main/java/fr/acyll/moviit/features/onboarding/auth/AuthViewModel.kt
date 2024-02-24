package fr.acyll.moviit.features.onboarding.auth

import android.app.Activity.RESULT_OK
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.identity.Identity
import fr.acyll.moviit.domain.model.onboarding.SignInResult
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class AuthViewModel(
    context: Context
): ViewModel() {
    private val _state = MutableStateFlow(AuthState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<AuthEffect>()
    val effect = _effect.asSharedFlow()

    fun onEvent(even: AuthEvent) {
        when (even) {
            is AuthEvent.OnContinueClick -> {

            }
        }
    }

    private fun emitEffect(effect: AuthEffect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }

    fun onSignInResult(result: SignInResult) {
        _state.update {
            it.copy(
                isSignInSuccessful =  result.data != null,
                signInError = result.errorMessage
            )
        }
    }

    fun resetState() {
        _state.update { AuthState() }
    }
}