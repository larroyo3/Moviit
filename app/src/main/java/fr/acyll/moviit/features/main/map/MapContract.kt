package fr.acyll.moviit.features.main.map

import com.google.firebase.firestore.DocumentReference
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
    data class OnPublishClick(val value: String): MapEvent()
}

sealed class MapEffect {
    data class ShowError(val error: Exception): MapEffect()
    data class GoToPublishPage(val value: String): MapEffect()
}