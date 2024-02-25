package fr.acyll.moviit.features.main.settings

import android.graphics.drawable.Icon
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.acyll.moviit.R
import fr.acyll.moviit.components.NoActionBarScreenContainer
import fr.acyll.moviit.components.PrimaryButton
import fr.acyll.moviit.navigation.Screen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    redirectToOnboarding: () -> Unit,
    navigateToScreen: (String) -> Unit
) {
    val state: SettingsState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is SettingsEffect.RedirectToOnboarding -> {
                    redirectToOnboarding()
                }
            }
        }
    }
    NoActionBarScreenContainer {
        ScreenContent(
            state = state,
            onEvent = {
                viewModel.onEvent(it)
            },
            navigateToScreen = {
                navigateToScreen(it)
            }
        )
    }
}

@Composable
fun ScreenContent(
    state: SettingsState,
    onEvent: (SettingsEvent) -> Unit,
    navigateToScreen: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.contribute),
            style = MaterialTheme.typography.titleMedium
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { navigateToScreen(Screen.Contribute.route) }
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.add_shooting_location),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null)
        }
        HorizontalDivider()

        Spacer(modifier = Modifier.weight(1f))
        PrimaryButton(
            label = stringResource(id = R.string.log_out),
            onClick = { onEvent(SettingsEvent.OnLogOutClick) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsDefaultPreview() {
    ScreenContent(
        state = SettingsState(),
        onEvent = {},
        navigateToScreen = {}
    )
}