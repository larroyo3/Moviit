package fr.acyll.moviit.features.publish

import fr.acyll.moviit.domain.model.Memories

data class PublishState(
    val isLoading: Boolean = false,
    val memories: Memories = Memories()
)

sealed class PublishEvent {
    data class OnTitleChange(val value: String): PublishEvent()
    data class OnDescriptionChange(val value: String): PublishEvent()
}

sealed class PublishEffect {
    data object ShowError: PublishEffect()
}