package fr.acyll.moviit.features.main.account

import fr.acyll.moviit.domain.model.onboarding.UserData

data class AccountState(
    val isLoading: Boolean = false,
    val userData: UserData? = null
)

sealed class AccountEvent {
    data object OnContinueClick: AccountEvent()
}

sealed class AccountEffect {
    data object ShowError: AccountEffect()
}