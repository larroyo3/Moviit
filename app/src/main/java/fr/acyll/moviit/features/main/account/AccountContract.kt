package fr.acyll.moviit.features.main.account

import fr.acyll.moviit.domain.model.Memories
import fr.acyll.moviit.domain.model.ShootingPlace
import fr.acyll.moviit.domain.model.onboarding.UserData

data class AccountState(
    val isLoading: Boolean = false,
    val userData: UserData? = null,
    val memories: List<Memories> = emptyList(),
    val shootingPlace: List<ShootingPlace> = emptyList(),
    val selectedIndex: Int = 0
)

sealed class AccountEvent {
    data class OnChangeTabs(val value: Int): AccountEvent()
}

sealed class AccountEffect {
    data class ShowError(val error: Exception): AccountEffect()
}