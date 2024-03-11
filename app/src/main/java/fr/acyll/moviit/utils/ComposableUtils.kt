package fr.acyll.moviit.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object ComposableUtils {

    @Composable
    fun ScreenWidth(): Dp {
        val screenWidth = LocalConfiguration.current.screenWidthDp
        return screenWidth.dp
    }

    @Composable
    fun ScreenHeight(): Dp {
        val screenHeight = LocalConfiguration.current.screenHeightDp
        return screenHeight.dp
    }
}