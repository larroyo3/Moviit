package fr.acyll.moviit.features.onboarding.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.acyll.moviit.R
import fr.acyll.moviit.ui.theme.MoviitTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navigateToNextScreen: () -> Unit
) {
    val scale = remember { Animatable(0.0f) }

    LaunchedEffect(key1 = Unit) {
        scale.animateTo(
            targetValue = 0.7f,
            animationSpec = tween(800, easing = {
                OvershootInterpolator(4f).getInterpolation(it)
            })
        )
        delay(1000)
        navigateToNextScreen()
    }

    Scaffold {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding(), bottom = it.calculateBottomPadding())
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            ScreenContent(scale)
        }
    }
}

@Composable
fun ScreenContent(
    scale: Animatable<Float, AnimationVector1D>
) {
    Box(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_app),
            contentDescription = "",
            alignment = Alignment.Center, modifier = Modifier
                .fillMaxSize()
                .padding(40.dp)
                .scale(scale.value)
        )

        Text(
            text = "Version - 0.0.1",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
    }
}

@Preview
@Composable
fun MainPreview() {
    MoviitTheme {
        ScreenContent(
            scale = remember { Animatable(0.0f) }
        )
    }
}
