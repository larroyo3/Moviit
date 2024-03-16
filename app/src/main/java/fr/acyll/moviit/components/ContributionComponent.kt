package fr.acyll.moviit.components

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import fr.acyll.moviit.domain.model.ShootingPlace
import fr.acyll.moviit.utils.ComposableUtils

@Composable
fun ContributionComponent(
    shootingPlace: ShootingPlace,
    context: Context,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest
                .Builder(context)
                .data(shootingPlace.moviePosterUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .widthIn(max = ComposableUtils.ScreenWidth() * 0.20f),
            contentScale = ContentScale.FillWidth
        )

        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = shootingPlace.title,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = shootingPlace.director + " - " + shootingPlace.releaseDate,
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6F)),
            )

            Spacer(modifier = Modifier.height(10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = shootingPlace.place,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview
@Composable
fun ContributionPreview() {
    ContributionComponent(
        context = LocalContext.current,
        shootingPlace = ShootingPlace()
    )
}