package fr.acyll.moviit.features.main.settings

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.identity.Identity
import fr.acyll.moviit.features.onboarding.auth.GoogleAuthUiClient
import fr.acyll.moviit.languages.Languages
import fr.acyll.moviit.languages.LocaleHelper
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    context: Context
): ViewModel() {
    private val _state = MutableStateFlow(SettingsState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<SettingsEffect>()
    val effect = _effect.asSharedFlow()

    init {
        val defaultLanguage = LocaleHelper.getLanguage(context)
        _state.update {
            it.copy(
                selectedLanguages = when (defaultLanguage) {
                    "fr" -> Languages.FRANCAIS
                    "en" -> Languages.ENGLISH
                    "es" -> Languages.ESPANOL
                    else -> Languages.FRANCAIS
                },
                context = context
            )
        }
    }
    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = context,
            oneTapClient = Identity.getSignInClient(context)
        )
    }

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.OnLogOutClick -> {
                viewModelScope.launch {
                    googleAuthUiClient.signOut()
                    emitEffect(SettingsEffect.RedirectToOnboarding)
                }
            }

            is SettingsEvent.OnSelectLanguage -> {
                _state.update {
                    it.copy(
                        showLanguagePopUp = true,
                        languageTmp = event.value
                    )
                }
            }

            is SettingsEvent.OnDismissChange -> {
                _state.update {
                    it.copy(
                        showLanguagePopUp = false,
                        languageTmp = null
                    )
                }
            }

            is SettingsEvent.OnConfirmLanguage -> {
                //editorLanguage?.putInt("language_selected", event.value.ordinal)?.apply()
                //updateLanguage(event.value, _state.value.context!!)
            }
        }
    }

    private fun updateLanguage(language: Languages, context: Context) {
        //editorLanguage?.putInt("language_selected", language.ordinal)?.apply()
    }

    private fun emitEffect(effect: SettingsEffect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }
}