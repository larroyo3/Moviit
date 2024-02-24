package fr.acyll.moviit.features.onboarding.auth

import android.app.Activity
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.acyll.moviit.components.PrimaryButton
import fr.acyll.moviit.R
import fr.acyll.moviit.components.NoActionBarScreenContainer
import fr.acyll.moviit.ui.theme.MoviitTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AuthScreen(
    viewModel: AuthViewModel,
    navigateToHomeScreen: () -> Unit,
    onContinueClick: () -> Unit
) {
    val state: AuthState by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let {error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    LaunchedEffect(key1 = state.isSignInSuccessful) {
        if(state.isSignInSuccessful) {
            Toast.makeText(
                context,
                "Sign in successful",
                Toast.LENGTH_LONG
            ).show()

            navigateToHomeScreen()
            viewModel.resetState()
        }
    }

    NoActionBarScreenContainer {
        ScreenContent(
            state = state,
            event = {
                viewModel.onEvent(it)
            },
            onContinueClick = onContinueClick
        )
    }
}

@Composable
fun ScreenContent(
    state: AuthState,
    event: (AuthEvent) -> Unit,
    onContinueClick: () -> Unit
) {
    Column(
      modifier = Modifier
          .fillMaxSize()
          .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.weight(0.5F))

        Image(
            painter = painterResource(id = R.drawable.illustration_movie_set),
            contentDescription = null,
        )

        Spacer(modifier = Modifier.padding(16.dp))
        Text(
            text = stringResource(id = R.string.auth_title),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text = stringResource(id = R.string.auth_desc),
            style = MaterialTheme.typography.bodyMedium
        )


        Spacer(modifier = Modifier.weight(1f))
        PrimaryButton(
            label = stringResource(id = R.string.continuer),
            onClick = onContinueClick
        )
    }

}

@Preview
@Composable
fun MainPreview() {
    ScreenContent(
        state = AuthState(),
        event = {},
        onContinueClick = {}
    )
}