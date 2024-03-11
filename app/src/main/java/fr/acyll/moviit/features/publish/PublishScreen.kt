package fr.acyll.moviit.features.publish

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
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
import fr.acyll.moviit.utils.ComposableUtils
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PublishScreen(
    viewModel: PublishViewModel,
    shootingPlaceId: String,
    onNavigateBack: () -> Unit
) {
    val state: PublishState by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.getShootingPlaceById(shootingPlaceId)
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is PublishEffect.ShowError -> {
                    Toast.makeText(context, "Exception: ${effect.error}", Toast.LENGTH_SHORT).show()
                }

                is PublishEffect.NavigateBack -> {
                    onNavigateBack()
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
            context = context,
            onEvent = {
                viewModel.onEvent(it)
            }
        )
    }
}

@Composable
fun ScreenContent(
    state: PublishState,
    context: Context,
    onEvent: (PublishEvent) -> Unit
) {
    val scrollState = rememberScrollState()
    val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            onEvent(PublishEvent.OnAddImage(uri))
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        SectionTitle(title = stringResource(R.string.memory))
        Spacer(modifier = Modifier.height(16.dp))

        if (state.imageUri != null) {
            AsyncImage(
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

        Spacer(modifier = Modifier.height(36.dp))
        PrimaryButton(
            label = stringResource(id = R.string.publish_memories),
            onClick = { onEvent(PublishEvent.OnPublishClick) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    ScreenContent(
        state = PublishState(),
        onEvent = {},
        context = LocalContext.current
    )
}