package fr.acyll.moviit.features.main.home

import fr.acyll.moviit.domain.model.Memories

data class HomeState(
    val isLoading: Boolean = false,
    val memories: List<Memories> = emptyList()
)

sealed class HomeEvent {
    data class OnMovieTitleChange(val value: String): HomeEvent()
}

sealed class HomeEffect {
    data class ShowError(val error: Exception): HomeEffect()
}