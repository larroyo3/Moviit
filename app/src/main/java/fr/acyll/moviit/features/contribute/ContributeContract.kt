package fr.acyll.moviit.features.contribute

data class ContributeState(
    val isLoading: Boolean = false,
)

sealed class ContributeEvent {
    data class OnMovieTitleChange(val value: String): ContributeEvent()
    data class OnMovieDirectorChange(val value: String): ContributeEvent()
    data class OnMovieReleaseDateChange(val value: String): ContributeEvent()
    data class OnSynopsisChange(val value: String): ContributeEvent()
    data class OnTitleChange(val value: String): ContributeEvent()
    data class OnDescriptionChange(val value: String): ContributeEvent()
    data class OnLatitudeChange(val value: String): ContributeEvent()
    data class OnLongitudesChange(val value: String): ContributeEvent()
    data object OnAddClick: ContributeEvent()
}

sealed class ContributeEffect {
    data object ShowError: ContributeEffect()
}