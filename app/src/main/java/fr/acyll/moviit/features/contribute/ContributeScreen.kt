package fr.acyll.moviit.features.contribute

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.acyll.moviit.features.main.settings.SettingsEffect
import fr.acyll.moviit.features.main.settings.SettingsState
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ContributeScreen(
    viewModel: ContributeViewModel
) {
    val state: ContributeState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is ContributeEffect.ShowError -> {
                }
            }
        }
    }
}