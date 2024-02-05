package fr.acyll.moviit.features.onboarding.auth

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import fr.acyll.moviit.ui.theme.MoviitTheme

@Composable
fun AuthScreen(

) {
    Text("auth screen")
}

@Preview
@Composable
fun MainPreview() {
    MoviitTheme {
        AuthScreen()
    }
}