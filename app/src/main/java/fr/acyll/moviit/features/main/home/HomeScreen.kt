package fr.acyll.moviit.features.main.home

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
           state = state
       )
    }
}

@Composable
fun ScreenContent(
    state: HomeState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.bodyLarge
        )

        LazyColumn {
            items(state.memories) { publication ->
                Text(text = publication.title)
                Text(text = publication.description)
                Text(text = getLabelFromDate(publication.creationDate))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    ScreenContent(
        state = HomeState()
    )
}