package fr.acyll.moviit.features.publish

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import fr.acyll.moviit.components.OutlinedTextField
import fr.acyll.moviit.components.PrimaryScreenContainer
import fr.acyll.moviit.components.SectionTitle
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PublishScreen(
    viewModel: PublishViewModel,
    onNavigateBack: () -> Unit
) {
    val state: PublishState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is PublishEffect.ShowError -> {

                }
            }
        }
    }

    PrimaryScreenContainer(
        title = stringResource(R.string.publication),
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
    state: PublishState,
    onEvent: (PublishEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SectionTitle(title = stringResource(R.string.memories))
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = state.memories.title,
            onValueChange = { onEvent(PublishEvent.OnTitleChange(it)) },
            label = stringResource(id = R.string.title),
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = state.memories.description,
            onValueChange = { onEvent(PublishEvent.OnDescriptionChange(it)) },
            label = stringResource(R.string.description),
            singleLine = false
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    ScreenContent(
        state = PublishState(),
        onEvent = {}
    )
}