package fr.acyll.moviit.features.main.profile

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import fr.acyll.moviit.ui.theme.MoviitTheme

@Composable
fun ProfileScreen(

) {
    Text(text = "coucou profile")
}

@Preview
@Composable
fun MainPreview() {
    MoviitTheme {
        ProfileScreen()
    }
}