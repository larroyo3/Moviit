package fr.acyll.moviit.features.onboarding.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.acyll.moviit.components.PrimaryButton
import fr.acyll.moviit.R
import fr.acyll.moviit.components.NoActionBarScreenContainer
import fr.acyll.moviit.ui.theme.MoviitTheme

@Composable
fun AuthScreen(
    navigateToHomeScreen: () -> Unit,
) {
    NoActionBarScreenContainer {
        ScreenContent(
            navigateToHomeScreen = navigateToHomeScreen
        )
    }
}

@Composable
fun ScreenContent(
    navigateToHomeScreen: () -> Unit
) {
    Column(
      modifier = Modifier
          .fillMaxSize()
          .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        PrimaryButton(
            label = stringResource(id = R.string.continuer),
            onClick = navigateToHomeScreen
        )
    }

}

@Preview
@Composable
fun MainPreview() {
    MoviitTheme {
        AuthScreen(
            navigateToHomeScreen = {}
        )
    }
}