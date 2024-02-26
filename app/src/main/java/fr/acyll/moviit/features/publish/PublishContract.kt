package fr.acyll.moviit.features.publish

import fr.acyll.moviit.domain.model.Memories
import fr.acyll.moviit.domain.model.ShootingPlace
import fr.acyll.moviit.domain.model.onboarding.UserData

data class PublishState(
    val isLoading: Boolean = false,
    val memories: Memories = Memories(),
    val shootingPlace: ShootingPlace? = null,
    val userData: UserData? = null
)

sealed class PublishEvent {
    data class OnTitleChange(val value: String): PublishEvent()
    data class OnDescriptionChange(val value: String): PublishEvent()
    data object OnPublishClick: PublishEvent()
}

sealed class PublishEffect {
    data class ShowError(val error: Exception): PublishEffect()
    data object NavigateBack: PublishEffect()
}