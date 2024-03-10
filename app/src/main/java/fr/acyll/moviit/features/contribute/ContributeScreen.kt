package fr.acyll.moviit.features.contribute

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import fr.acyll.moviit.R
import fr.acyll.moviit.components.OutlinedTextField
import fr.acyll.moviit.components.PrimaryButton
import fr.acyll.moviit.components.PrimaryScreenContainer
import fr.acyll.moviit.components.SectionTitle
import fr.acyll.moviit.features.main.settings.ScreenContent
import fr.acyll.moviit.features.main.settings.SettingsEffect
import fr.acyll.moviit.features.main.settings.SettingsEvent
import fr.acyll.moviit.features.main.settings.SettingsState
import fr.acyll.moviit.features.publish.PublishEffect
import fr.acyll.moviit.features.publish.PublishEvent
import fr.acyll.moviit.utils.ComposableUtils
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ContributeScreen(
    viewModel: ContributeViewModel,
    onNavigateBack: () -> Unit
) {
    val state: ContributeState by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is ContributeEffect.ShowError -> {
                    Toast.makeText(context, "Exception: ${effect.error}", Toast.LENGTH_SHORT).show()
                }
                is ContributeEffect.NavigateBack -> {
                    onNavigateBack()
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
            context = context,
            onEvent = {
                viewModel.onEvent(it)
            }
        )
    }
}

@Composable
fun ScreenContent(
    state: ContributeState,
    context: Context,
    onEvent: (ContributeEvent) -> Unit
) {
    val scrollState = rememberScrollState()
    val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            onEvent(ContributeEvent.OnAddImage(uri))
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        SectionTitle(title = stringResource(R.string.movie_information))

        if (state.imageUri != null) {
            AsyncImage(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                model = ImageRequest
                    .Builder(context)
                    .data(state.imageUri)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(ComposableUtils.ScreenWidth() * 0.8f)
                    .clickable {
                        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    }
                    .background(
                        color = MaterialTheme.colorScheme.tertiaryContainer,
                        shape = MaterialTheme.shapes.small
                    )
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.tertiary,
                        shape = MaterialTheme.shapes.small
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.AddAPhoto,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onTertiaryContainer,
                    modifier = Modifier.size(45.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(id = R.string.add_image),
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }

        OutlinedTextField(
            value = state.shootingPlace.title,
            onValueChange = { onEvent(ContributeEvent.OnTitleChange(it)) },
            label = stringResource(R.string.title),
        )

        OutlinedTextField(
            value = state.shootingPlace.director,
            onValueChange = { onEvent(ContributeEvent.OnDirectorChange(it)) },
            label = stringResource(R.string.director),
        )

        OutlinedTextField(
            value = state.shootingPlace.releaseDate,
            onValueChange = { onEvent(ContributeEvent.OnReleaseDateChange(it)) },
            label = stringResource(R.string.release_date),
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                modifier = Modifier.width((ComposableUtils.ScreenWidth() - 40.dp) * 0.5F),
                value = state.shootingPlace.latitude,
                onValueChange = { onEvent(ContributeEvent.OnLatitudeChange(it)) },
                label = stringResource(R.string.latitude),
            )

            OutlinedTextField(
                modifier = Modifier.width((ComposableUtils.ScreenWidth() - 32.dp) * 0.5F),
                value = state.shootingPlace.longitude,
                onValueChange = { onEvent(ContributeEvent.OnLongitudesChange(it)) },
                label = stringResource(R.string.longitude),
            )
        }

        OutlinedTextField(
            value = state.shootingPlace.synopsis,
            onValueChange = { onEvent(ContributeEvent.OnSynopsisChange(it)) },
            label = stringResource(R.string.synopsis),
            singleLine = false
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
        context = LocalContext.current,
        onEvent = {}
    )
}
