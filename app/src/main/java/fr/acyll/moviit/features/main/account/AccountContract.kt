package fr.acyll.moviit.features.main.account

import fr.acyll.moviit.domain.model.Memories
import fr.acyll.moviit.domain.model.onboarding.UserData

data class AccountState(
    val isLoading: Boolean = false,
    val userData: UserData? = null,
    val memories: List<Memories> = emptyList()
)

sealed class AccountEvent {
    data object OnContinueClick: AccountEvent()
}

sealed class AccountEffect {
    data class ShowError(val error: Exception): AccountEffect()
}