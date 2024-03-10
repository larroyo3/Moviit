package fr.acyll.moviit.features.main.account

import android.content.Context
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
import androidx.compose.material3.HorizontalDivider
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
import fr.acyll.moviit.components.MemoryPublication
import fr.acyll.moviit.components.NoActionBarScreenContainer
import fr.acyll.moviit.components.SectionTitle
import fr.acyll.moviit.domain.model.onboarding.UserData
import fr.acyll.moviit.features.main.home.HomeEffect
import fr.acyll.moviit.features.onboarding.auth.AuthState
import fr.acyll.moviit.utils.ComposableDateUtils
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AccountScreen(
    viewModel: AccountViewModel,
) {
    val state: AccountState by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.refreshData()
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is AccountEffect.ShowError -> {
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
    state: AccountState,
    context: Context
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest
                    .Builder(context)
                    .data(state.userData?.profilePictureUrl)
                    .crossfade(true)
                    .placeholder(R.drawable.ic_account)
                    .build(),
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(75.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = state.userData?.username ?: "",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "${state.memories.size} souvenirs",
                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6F)),
                )
            }
        }

        SectionTitle(
            title = stringResource(R.string.memories),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
        )

        LazyColumn {
            items(state.memories) { memory ->
                MemoryPublication(memory = memory, context = context, profileSection = false)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    ScreenContent(
        context = LocalContext.current,
        state = AccountState(
            userData = UserData(
                userId = "12",
                username = "Lucas Arroyo",
                profilePictureUrl = null
            )
        )
    )
}