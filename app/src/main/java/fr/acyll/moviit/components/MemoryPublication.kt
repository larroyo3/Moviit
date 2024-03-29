package fr.acyll.moviit.components

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import fr.acyll.moviit.R
import fr.acyll.moviit.domain.model.Memories
import fr.acyll.moviit.utils.ComposableDateUtils
import java.util.Date

@Composable
fun MemoryPublication(
    memory: Memories,
    context: Context,
    profileSection: Boolean = true
) {
    Column(
        modifier = Modifier
            .padding(vertical = 8.dp),
    ) {
        if (profileSection) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
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
                        text = ComposableDateUtils.getLabelFromDate(date = memory.creationDate),
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onBackground.copy(
                                alpha = 0.6F
                            )
                        ),
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        AsyncImage(
            model = ImageRequest
                .Builder(context)
                .data(memory.imageURL)
                .crossfade(true)
                .build(),
            modifier = Modifier.fillMaxWidth(),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
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

@Preview(showBackground = true)
@Composable
fun PreviewMemoryPublication() {
    MemoryPublication(
        memory = Memories(
            title = "Chateau",
            description = "très beau chateau",
            author = "Moviit team",
            creationDate = Date()
        ),
        context = LocalContext.current
    )
}