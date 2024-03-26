package fr.acyll.moviit.features.main.settings

import android.content.Context
import fr.acyll.moviit.languages.Languages

data class SettingsState(
    val isLoading: Boolean = false,
    val selectedLanguages: Languages = Languages.FRANCAIS,
    val showLanguagePopUp: Boolean = true,
    val languageTmp: Languages? = null,
    val context: Context? = null
)

sealed class SettingsEvent {
    data object OnLogOutClick: SettingsEvent()
    data class OnSelectLanguage(val value: Languages): SettingsEvent()
    data class OnConfirmLanguage(val value: Languages): SettingsEvent()
    data object OnDismissChange: SettingsEvent()
}

sealed class SettingsEffect {
    data object RedirectToOnboarding: SettingsEffect()
}