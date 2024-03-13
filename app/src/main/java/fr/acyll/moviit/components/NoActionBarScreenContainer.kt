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
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun NoActionBarScreenContainer(
    isLoading: Boolean = false,
    isMaps: Boolean = false,
    content: @Composable (PaddingValues) -> Unit
) {
    val systemUiController = rememberSystemUiController()
    val statusBarColor = MaterialTheme.colorScheme.background
    SideEffect {
        systemUiController.setSystemBarsColor(statusBarColor)
    }

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
                        progress = {
                            0.7F
                        },
                        modifier = Modifier.align(Alignment.Center),
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun NoActionBarScreenContainerPreview() {
    NoActionBarScreenContainer(

    ) {

    }
}