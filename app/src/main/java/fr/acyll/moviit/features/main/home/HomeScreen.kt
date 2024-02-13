package fr.acyll.moviit.features.main.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.acyll.moviit.components.NoActionBarScreenContainer
import fr.acyll.moviit.ui.theme.MoviitTheme

@Composable
fun HomeScreen() {
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
        Text(text = "coucou home")
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    ScreenContent()
}