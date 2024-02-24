package fr.acyll.moviit.features.main.account

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.identity.Identity
import fr.acyll.moviit.features.onboarding.auth.GoogleAuthUiClient
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AccountViewModel(
    context: Context
): ViewModel() {
    private val _state = MutableStateFlow(AccountState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<AccountEffect>()
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

    fun onEvent(even: AccountEvent) {
        when (even) {
            is AccountEvent.OnContinueClick -> {
            }
        }
    }

    private fun emitEffect(effect: AccountEffect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }
}