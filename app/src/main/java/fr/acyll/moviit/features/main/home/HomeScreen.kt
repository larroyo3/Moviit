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
import fr.acyll.moviit.components.MemoryPublication
import fr.acyll.moviit.components.NoActionBarScreenContainer
import fr.acyll.moviit.domain.model.Memories
import fr.acyll.moviit.features.contribute.ContributeEffect
import fr.acyll.moviit.features.contribute.ContributeState
import fr.acyll.moviit.ui.theme.MoviitTheme
import fr.acyll.moviit.utils.ComposableDateUtils.getLabelFromDate
import kotlinx.coroutines.flow.collectLatest
import java.util.Date

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
                MemoryPublication(memory = memory, context = context)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    ScreenContent(
        state = HomeState(
            memories = listOf(
                Memories(
                    title = "Chateau",
                    description = "très beau chateau",
                    author = "Moviit team",
                    creationDate = Date()
                ),
                Memories(
                    title = "Chateau",
                    description = "très beau chateau",
                    author = "Moviit team",
                    creationDate = Date()
                ),
                Memories(
                    title = "Chateau",
                    description = "très beau chateau",
                    author = "Moviit team",
                    creationDate = Date()
                ),
            )
        ),
        context = LocalContext.current
    )
}