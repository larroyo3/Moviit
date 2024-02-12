package fr.acyll.moviit.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings

import androidx.compose.ui.graphics.vector.ImageVector
import fr.acyll.moviit.R

sealed class BottomNavScreen(
    val route: String,
    val title: Int,
    val icon: ImageVector,
    val selectedIcon: ImageVector
) {
    data object Home: BottomNavScreen(
        route = "home_screen",
        title = R.string.home,
        icon = Icons.Outlined.Home,
        selectedIcon = Icons.Default.Home
    )

    data object Map: BottomNavScreen(
        route = "map_screen",
        title = R.string.map,
        icon = Icons.Outlined.Explore,
        selectedIcon = Icons.Default.Explore
    )

    data object Profile: BottomNavScreen(
        route = "profile_screen",
        title = R.string.profile,
        icon = Icons.Outlined.Person,
        selectedIcon = Icons.Default.Person
    )

    data object Settings: BottomNavScreen(
        route = "settings_screen",
        title = R.string.settings,
        icon = Icons.Outlined.Settings,
        selectedIcon = Icons.Default.Settings
    )
}
