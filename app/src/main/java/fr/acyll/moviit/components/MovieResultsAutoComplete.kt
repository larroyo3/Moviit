package fr.acyll.moviit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import fr.acyll.moviit.R
import fr.acyll.moviit.domain.model.movies.MovieEntity

@Composable
fun MovieResultsAutoComplete(
    items: List<MovieEntity>,
    onItemClick: (MovieEntity) -> Unit
) {
    val context = LocalContext.current

    Surface(
        shadowElevation = 4.dp,
        tonalElevation = 10.dp
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 240.dp)
                .background(Color.White)
                .border(width = 1.dp, color = Color.LightGray)
        ) {
            items(items) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .height(75.dp)
                        .clickable {
                            onItemClick(it)
                        }
                        .padding(4.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = ImageRequest
                                .Builder(context)
                                .data(it.poster)
                                .crossfade(true)
                                .placeholder(R.drawable.ic_account)
                                .build(),
                            contentDescription = null,
                            modifier = Modifier
                                .size(75.dp),
                            contentScale = ContentScale.FillHeight
                        )

                        Spacer(modifier = Modifier.width(4.dp))
                        Column {
                            Text(
                                text = it.title,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = it.releaseDate ?: "",
                                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6F)),
                            )
                        }
                    }
                }

                if (items.indexOf(it) < items.size - 1) {
                    HorizontalDivider()
                }
            }
        }
    }

}

@Preview
@Composable
fun MovieResultsAutoCompletePreview() {
    MovieResultsAutoComplete(
        items = emptyList(),
        onItemClick = {}
    )
}