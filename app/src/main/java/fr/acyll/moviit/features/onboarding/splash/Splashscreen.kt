package fr.acyll.moviit.features.onboarding.splash

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import fr.acyll.moviit.ui.theme.MoviitTheme

@Composable
fun SplashScreen(

) {
    Scaffold {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it.calculateTopPadding())
        ) {
            Text(text = "splash screen")
        }
    }
}

@Preview
@Composable
fun MainPreview() {
    MoviitTheme {
        SplashScreen()
    }
}
