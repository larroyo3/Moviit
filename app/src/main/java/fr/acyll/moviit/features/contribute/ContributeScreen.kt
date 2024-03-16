package fr.acyll.moviit.features.contribute

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import fr.acyll.moviit.R
import fr.acyll.moviit.components.MovieResultsAutoComplete
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (state.selectedMovie == null) {
            SectionTitle(title = stringResource(R.string.select_movie))

            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = state.shootingPlace.title,
                onValueChange = { onEvent(ContributeEvent.OnSearchQueryChange(it)) },
                label = stringResource(R.string.title),
            )
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    modifier = Modifier.zIndex(1f)
                ) {
                    AnimatedVisibility(
                        visible = state.searchResult.isNotEmpty()
                    ) {
                        MovieResultsAutoComplete(
                            items = state.searchResult,
                            onItemClick = { onEvent(ContributeEvent.OnSelectMovie(it)) }
                        )
                    }
                }
            }
        } else {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    AsyncImage(
                        model = ImageRequest
                            .Builder(context)
                            .data(state.selectedMovie.poster)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .widthIn(max = ComposableUtils.ScreenWidth() * 0.33f),
                        contentScale = ContentScale.FillWidth
                    )

                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = state.selectedMovie.title,
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Text(
                            text = state.selectedMovie.credits?.crew?.find { it.job == "Director" }?.name ?: "",
                            style = MaterialTheme.typography.labelMedium
                        )
                        Text(
                            text = state.selectedMovie.releaseDate ?: "",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onBackground.copy(
                                    alpha = 0.6F
                                )
                            ),
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = state.selectedMovie.overview ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(24.dp))
                SectionTitle(title = stringResource(R.string.place_informations))

                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = state.shootingPlace.place,
                    onValueChange = { onEvent(ContributeEvent.OnPlaceChange(it)) },
                    label = stringResource(R.string.name),
                )
                Spacer(modifier = Modifier.height(10.dp))
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
            }
        }



        Spacer(modifier = Modifier.weight(1f))
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
