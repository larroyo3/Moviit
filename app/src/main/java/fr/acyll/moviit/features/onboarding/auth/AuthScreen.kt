package fr.acyll.moviit.features.onboarding.auth

import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
        // TODO : mettre vrai typo
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.weight(0.3F))

        Image(
            painter = painterResource(id = R.drawable.illustration_movie_set),
            contentDescription = null,
        )

        // TODO : update text style
        Spacer(modifier = Modifier.padding(16.dp))
        Text(
            text = stringResource(id = R.string.auth_title),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text = stringResource(id = R.string.auth_desc),
            style = MaterialTheme.typography.bodyLarge
        )


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
    AuthScreen(
        navigateToHomeScreen = {}
    )

}