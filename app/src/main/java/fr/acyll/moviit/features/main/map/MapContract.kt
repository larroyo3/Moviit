package fr.acyll.moviit.features.main.map

import fr.acyll.moviit.domain.model.ShootingPlace

data class MapState(
    val isLoading: Boolean = false,
    val showMarkerBottomSheet: Boolean = false,

    val shootingPlaces: List<ShootingPlace> = emptyList(),
    val selectedShootingPlace: ShootingPlace? = null
)

sealed class MapEvent {
    data class OnMarkerClick(val value: ShootingPlace): MapEvent()
    data object OnDismissMarkerBottomSheet: MapEvent()
}

sealed class MapEffect {
    data object ShowError: MapEffect()
}