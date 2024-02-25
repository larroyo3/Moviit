package fr.acyll.moviit.features.contribute

data class ContributeState(
    val isLoading: Boolean = false,

    val title: String = ""
)

sealed class ContributeEvent {
    data object OnTitleChange: ContributeEvent()
}

sealed class ContributeEffect {
    data object ShowError: ContributeEffect()
}