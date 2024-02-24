package fr.acyll.moviit.features.main.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import fr.acyll.moviit.features.main.account.AccountState
import fr.acyll.moviit.features.main.account.AccountViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    redirectToOnboarding: () -> Unit
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
            }
        )
    }
}

@Composable
fun ScreenContent(
    state: SettingsState,
    onEvent: (SettingsEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
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
        onEvent = {}
    )
}