package fr.acyll.moviit.features.main.home

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import fr.acyll.moviit.R
import fr.acyll.moviit.components.NoActionBarScreenContainer
import fr.acyll.moviit.features.contribute.ContributeEffect
import fr.acyll.moviit.features.contribute.ContributeState
import fr.acyll.moviit.ui.theme.MoviitTheme
import fr.acyll.moviit.utils.ComposableDateUtils.getLabelFromDate
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(
    viewModel: HomeViewModel
) {
    val state: HomeState by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.refreshData()
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is HomeEffect.ShowError -> {
                    Toast.makeText(context, "Exception: ${effect.error}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    NoActionBarScreenContainer {
       ScreenContent(
           state = state,
           context = context
       )
    }
}

@Composable
fun ScreenContent(
    state: HomeState,
    context: Context
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn {
            items(state.memories) { memory ->
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = ImageRequest
                                .Builder(context)
                                .data(memory.authorProfilePictureUrl)
                                .crossfade(true)
                                .placeholder(R.drawable.ic_account)
                                .build(),
                            contentDescription = "Profile picture",
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = memory.author,
                                style = MaterialTheme.typography.labelMedium
                            )
                            Text(
                                text = getLabelFromDate(date = memory.creationDate),
                                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6F)),
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = memory.title,
                        style = MaterialTheme.typography.labelLarge
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = memory.description,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    ScreenContent(
        state = HomeState(),
        context = LocalContext.current
    )
}