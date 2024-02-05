package fr.acyll.moviit.features.main.map

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import fr.acyll.moviit.ui.theme.MoviitTheme

@Composable
fun MapScreen(

) {
    Text(text = "coucou map")
}

@Preview
@Composable
fun MainPreview() {
    MoviitTheme {
        MapScreen()
    }
}