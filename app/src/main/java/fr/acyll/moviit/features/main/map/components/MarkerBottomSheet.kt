package fr.acyll.moviit.features.main.map.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import fr.acyll.moviit.R
import fr.acyll.moviit.components.BottomSheet
import fr.acyll.moviit.components.PrimaryButton
import fr.acyll.moviit.components.buttons.SecondaryButton
import fr.acyll.moviit.domain.model.ShootingPlace
import fr.acyll.moviit.utils.ComposableDateUtils

@Composable
fun MarkerBottomSheet(
    onDismiss: () -> Unit,
    onPublishClick: () -> Unit,
    shootingPlace: ShootingPlace
) {
    val context = LocalContext.current

    BottomSheet(
        title = shootingPlace.title,
        onDismiss = onDismiss,
        body =
        {
            Row(
               modifier = Modifier
                   .fillMaxWidth()
            ) {
               AsyncImage(
                   model = ImageRequest
                       .Builder(context)
                       .data(shootingPlace.moviePosterUrl)
                       .crossfade(true)
                       .build(),
                   contentDescription = null,
                   modifier = Modifier
                       .width(140.dp)
               )

               Spacer(modifier = Modifier.width(12.dp))
               Column {
                   Text(
                       text = shootingPlace.director,
                       style = MaterialTheme.typography.labelMedium
                   )
                   Text(
                       text = shootingPlace.releaseDate,
                       style = MaterialTheme.typography.bodySmall.copy(
                           color = MaterialTheme.colorScheme.onBackground.copy(
                               alpha = 0.6F
                           )
                       ),
                   )

                   Spacer(modifier = Modifier.height(16.dp))
                   Text(
                       text = shootingPlace.synopsis,
                       style = MaterialTheme.typography.bodyMedium,
                       maxLines = 6,
                       overflow = TextOverflow.Ellipsis
                   )
               }
            }
        },
        footer = {
            PrimaryButton(
                label = stringResource(id = R.string.publish_memories),
                onClick = {
                    onDismiss()
                    onPublishClick()
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            SecondaryButton(
                label = stringResource(id = R.string.close),
                onClick = onDismiss
            )
        }
    )
}