package fr.acyll.moviit.features.main.account

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import fr.acyll.moviit.R
import fr.acyll.moviit.components.NoActionBarScreenContainer
import fr.acyll.moviit.domain.model.onboarding.UserData
import fr.acyll.moviit.features.onboarding.auth.AuthState

@Composable
fun AccountScreen(
    viewModel: AccountViewModel,
) {
    val state: AccountState by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

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
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
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
            Text(
                text = state.userData?.username ?: "",
                style = MaterialTheme.typography.titleLarge
            )
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