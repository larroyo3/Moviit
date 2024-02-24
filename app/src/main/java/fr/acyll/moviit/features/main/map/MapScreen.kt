package fr.acyll.moviit.features.main.map

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import fr.acyll.moviit.components.NoActionBarScreenContainer
import fr.acyll.moviit.ui.theme.MoviitTheme

@Composable
fun MapScreen(

) {
    NoActionBarScreenContainer {
        ScreenContent()
    }
}

@Composable
fun ScreenContent() {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(45.763420, 4.834277), 9f)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        GoogleMap(
            cameraPositionState = cameraPositionState,
        ) {

        }
    }
}

@Preview
@Composable
fun MainPreview() {
    ScreenContent()
}