package fr.acyll.moviit.features.onboarding.auth

data class AuthState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)

sealed class AuthEvent {
    data object OnContinueClick: AuthEvent()
}

sealed class AuthEffect {
    data object NavigateToNextStep: AuthEffect()
}