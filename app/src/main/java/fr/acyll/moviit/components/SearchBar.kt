package fr.acyll.moviit.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import fr.acyll.moviit.R
import fr.acyll.moviit.domain.model.movies.MovieEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarM3(
    query: String,
    active: Boolean,
    searchResult: List<MovieEntity>,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    onCloseSearch: () -> Unit,
    onDeleteQuery: () -> Unit,
    onActiveChange: (Boolean) -> Unit
) {
    val context = LocalContext.current

    SearchBar(
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.padding(horizontal = if (active) 0.dp else 8.dp, vertical = if (active) 0.dp else 8.dp),
        query = query,
        onQueryChange = onQueryChange,
        onSearch = onSearch,
        active = active,
        onActiveChange = onActiveChange,
        placeholder = {
            Text(text = "Search")
        },
        leadingIcon = {
            Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
        },
        trailingIcon = {
            Row {
                IconButton(onClick = { /* open mic dialog */ }) {
                    Icon(imageVector = Icons.Default.Mic, contentDescription = "Mic")
                }
                if (active) {
                    IconButton(
                        onClick = { if (query.isNotEmpty()) onDeleteQuery() else onCloseSearch() }
                    ) {
                        Icon(imageVector = Icons.Filled.Close, contentDescription = "Close")
                    }
                }
            }
        }
    ) {
        searchResult.forEach { item ->
            ListItem(
                modifier = Modifier
                    .clickable { onSearch(item.title) },
                headlineContent = { Text(text = item.title) },
                leadingContent = {
                    AsyncImage(
                        model = ImageRequest
                            .Builder(context)
                            .data(item.poster)
                            .crossfade(true)
                            .placeholder(R.drawable.ic_account)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .size(75.dp),
                        contentScale = ContentScale.FillHeight
                    )
                }
            )
        }
    }
}