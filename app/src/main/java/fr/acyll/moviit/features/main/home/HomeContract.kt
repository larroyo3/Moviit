package fr.acyll.moviit.features.main.home

import fr.acyll.moviit.domain.model.Memories
import fr.acyll.moviit.domain.model.movies.MovieEntity

data class HomeState(
    val isLoading: Boolean = false,
    val memories: List<Memories> = emptyList(),

    val query: String = "",
    val active: Boolean = false,
    val searchResult: List<MovieEntity> = emptyList()
)

sealed class HomeEvent {
    data class OnQueryChange(val value: String): HomeEvent()
    data class OnSearch(val value: String): HomeEvent()
    data class OnActiveChange(val value: Boolean): HomeEvent()
    data object OnDeleteQuery: HomeEvent()
    data object OnCloseSearch: HomeEvent()
}

sealed class HomeEffect {
    data class ShowError(val error: Throwable): HomeEffect()
}