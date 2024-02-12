package fr.acyll.moviit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun NoActionBarScreenContainer(
    isLoading: Boolean = false,
    content: @Composable (PaddingValues) -> Unit
) {

    Scaffold { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                )
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            Box {
                content(paddingValues)
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        progress = 0.7F
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun NoActionBarScreenContainerPreview() {
    NoActionBarScreenContainer {

    }
}