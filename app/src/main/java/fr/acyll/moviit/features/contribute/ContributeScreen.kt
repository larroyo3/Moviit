package fr.acyll.moviit.features.contribute

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.acyll.moviit.R
import fr.acyll.moviit.components.OutlinedTextField
import fr.acyll.moviit.components.PrimaryButton
import fr.acyll.moviit.components.PrimaryScreenContainer
import fr.acyll.moviit.components.SectionTitle
import fr.acyll.moviit.features.main.settings.ScreenContent
import fr.acyll.moviit.features.main.settings.SettingsEffect
import fr.acyll.moviit.features.main.settings.SettingsEvent
import fr.acyll.moviit.features.main.settings.SettingsState
import fr.acyll.moviit.utils.ComposableUtils
import kotlinx.coroutines.flow.collectLatest

/*
@Composable
fun ContributeScreen(
    viewModel: ContributeViewModel,
    onNavigateBack: () -> Unit
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

    PrimaryScreenContainer(
        title = stringResource(R.string.contribute),
        onPrimaryButtonClick = onNavigateBack
    ) {
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
    state: ContributeState,
    onEvent: (ContributeEvent) -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        SectionTitle(title = stringResource(R.string.movie_information))

        OutlinedTextField(
            value = state.publication.movieTitle,
            onValueChange = { onEvent(ContributeEvent.OnMovieTitleChange(it)) },
            label = stringResource(R.string.title),
        )
        OutlinedTextField(
            value = state.publication.movieDirector,
            onValueChange = { onEvent(ContributeEvent.OnMovieDirectorChange(it)) },
            label = stringResource(R.string.director),
        )
        OutlinedTextField(
            value = state.publication.releaseDate,
            onValueChange = { onEvent(ContributeEvent.OnMovieReleaseDateChange(it)) },
            label = stringResource(R.string.release_date),
        )
        OutlinedTextField(
            value = state.publication.synopsis,
            onValueChange = { onEvent(ContributeEvent.OnSynopsisChange(it)) },
            label = stringResource(R.string.synopsis),
            singleLine = false
        )
        OutlinedTextField(
            value = "",
            label = "Affiche du film",
            placeHolder = "XXXXXXXXXXXXXXXXXXXXXXXX",
        )

        Spacer(modifier = Modifier.height(16.dp))
        SectionTitle(title = stringResource(R.string.shooting_place))
        OutlinedTextField(
            value = state.publication.title,
            onValueChange = { onEvent(ContributeEvent.OnTitleChange(it)) },
            label = stringResource(id = R.string.title),
        )
        OutlinedTextField(
            value = state.publication.description,
            onValueChange = { onEvent(ContributeEvent.OnDescriptionChange(it)) },
            label = stringResource(R.string.description),
            singleLine = false
        )

        Text(
            text = stringResource(R.string.localisation),
            style = MaterialTheme.typography.bodyMedium
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.width((ComposableUtils.ScreenWidth() - 32.dp) * 0.5f),
                value = state.publication.latitude,
                onValueChange = { onEvent(ContributeEvent.OnLatitudeChange(it)) },
                keyboardType = KeyboardType.Decimal,
                placeHolder = stringResource(R.string.latitude),
            )
            OutlinedTextField(
                modifier = Modifier.width((ComposableUtils.ScreenWidth() - 32.dp) * 0.5f),
                value = state.publication.longitude,
                onValueChange = { onEvent(ContributeEvent.OnLongitudesChange(it)) },
                keyboardType = KeyboardType.Decimal,
                placeHolder = stringResource(R.string.longitude),
            )
        }
        OutlinedTextField(
            value = "",
            label = "Photo",
            placeHolder = "xxxxxxxxxxxxxx",
        )

        Spacer(modifier = Modifier.height(24.dp))
        PrimaryButton(
            label = stringResource(id = R.string.confirm),
            onClick = { onEvent(ContributeEvent.OnAddClick) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewContributeScree() {
    ScreenContent(
        state = ContributeState(),
        onEvent = {}
    )
}

 */