package fr.acyll.moviit.features.main.map

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import fr.acyll.moviit.components.NoActionBarScreenContainer
import fr.acyll.moviit.features.main.map.components.MarkerBottomSheet
import fr.acyll.moviit.navigation.Screen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MapScreen(
    viewModel: MapViewModel,
    navigateToScreen: (String) -> Unit
) {
    val state: MapState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is MapEffect.ShowError -> {

                }
            }
        }
    }

    NoActionBarScreenContainer {
        ScreenContent(
            state = state,
            onEvent = {
                viewModel.onEvent(it)
            },
            onNavigateToScreen = {
                // TODO : add event on publish click and effect navigate to screen
                navigateToScreen(it)
            }
        )
    }
}

@Composable
fun ScreenContent(
    state: MapState,
    onEvent: (MapEvent) -> Unit,
    onNavigateToScreen: (String) -> Unit
) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(45.763420, 4.834277), 9f)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        GoogleMap(
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(
                compassEnabled = true,
                indoorLevelPickerEnabled = true,
                mapToolbarEnabled = true,
                myLocationButtonEnabled = true,
                rotationGesturesEnabled = true,
                scrollGesturesEnabled = true,
                scrollGesturesEnabledDuringRotateOrZoom = true,
                tiltGesturesEnabled = true,
                zoomControlsEnabled = false,
                zoomGesturesEnabled = true
            )
        ) {
            state.shootingPlaces.forEach { publication ->
                MarkerComposable(
                    state = MarkerState(position = LatLng(publication.latitude.toDouble(), publication.longitude.toDouble())),
                    onClick = {
                        onEvent(MapEvent.OnMarkerClick(publication))
                        false
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
        }

        if (state.showMarkerBottomSheet) {
            MarkerBottomSheet(
                onDismiss = { onEvent(MapEvent.OnDismissMarkerBottomSheet) },
                onPublishClick = { onNavigateToScreen(Screen.Publish.route) },
                shootingPlace = state.selectedShootingPlace!!
            )
        }
    }
}

@Preview
@Composable
fun MainPreview() {
    ScreenContent(
        state = MapState(),
        onEvent = {},
        onNavigateToScreen = {}
    )
}