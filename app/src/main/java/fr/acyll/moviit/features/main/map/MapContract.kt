package fr.acyll.moviit.features.main.map

import com.google.firebase.firestore.DocumentReference
import fr.acyll.moviit.domain.model.ShootingPlace
import fr.acyll.moviit.domain.model.movies.MovieEntity
import fr.acyll.moviit.features.main.home.HomeEvent

data class MapState(
    val isLoading: Boolean = false,
    val showMarkerBottomSheet: Boolean = false,

    val shootingPlaces: List<ShootingPlace> = emptyList(),
    val selectedShootingPlace: ShootingPlace? = null,

    val query: String = "",
    val active: Boolean = false,
    val searchResult: List<MovieEntity> = emptyList()
)

sealed class MapEvent {
    data class OnMarkerClick(val value: ShootingPlace): MapEvent()
    data object OnDismissMarkerBottomSheet: MapEvent()
    data class OnPublishClick(val value: String): MapEvent()
    data class OnQueryChange(val value: String): MapEvent()
    data class OnSearch(val value: String): MapEvent()
    data class OnActiveChange(val value: Boolean): MapEvent()
    data object OnDeleteQuery: MapEvent()
    data object OnCloseSearch: MapEvent()
}

sealed class MapEffect {
    data class ShowError(val error: Throwable): MapEffect()
    data class GoToPublishPage(val value: String): MapEffect()
}