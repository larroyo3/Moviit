package fr.acyll.moviit.features.main.settings

data class SettingsState(
    val isLoading: Boolean = false
)

sealed class SettingsEvent {
    data object OnLogOutClick: SettingsEvent()
}

sealed class SettingsEffect {
    data object RedirectToOnboarding: SettingsEffect()
}