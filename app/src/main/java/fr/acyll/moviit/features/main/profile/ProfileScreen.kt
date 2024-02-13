package fr.acyll.moviit.features.main.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.acyll.moviit.components.NoActionBarScreenContainer

@Composable
fun ProfileScreen(

) {
    NoActionBarScreenContainer {
        ScreenContent()
    }
}

@Composable
fun ScreenContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "coucou profile")

    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    ScreenContent()
}