package fr.acyll.moviit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun PrimaryScreenContainer(
    title: String = "",
    subtitle: String? = null,
    isLoading: Boolean = false,
    onPrimaryButtonClick: (() -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit
) {
    val systemUiController = rememberSystemUiController()
    val statusBarColor = MaterialTheme.colorScheme.primaryContainer
    SideEffect {
        systemUiController.setSystemBarsColor(statusBarColor)
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = title,
                subtitle = subtitle,
                onPrimaryButtonClick = onPrimaryButtonClick
            )
        }
    ) { paddingValues ->
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
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewPrimaryScreenContainer() {
    PrimaryScreenContainer(
        title = "Contribuer",
        subtitle = "Ajouter un lieu de tournage",
        isLoading = false,
        onPrimaryButtonClick = {}
    ) {}
}