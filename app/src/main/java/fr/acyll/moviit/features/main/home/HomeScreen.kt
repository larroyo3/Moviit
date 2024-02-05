package fr.acyll.moviit.features.main.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import fr.acyll.moviit.ui.theme.MoviitTheme

@Composable
fun HomeScreen(

) {
    Text(text = "coucou home")
}

@Preview
@Composable
fun MainPreview() {
    MoviitTheme {
        HomeScreen()
    }
}