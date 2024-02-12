package fr.acyll.moviit.navigation.graphs

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import fr.acyll.moviit.navigation.BottomNavScreen

@Composable
fun MainGraph() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomBar(navController = navController)}
    ) {
        BottomNavGraph(
            navController = navController,
            modifier = Modifier.padding(top = it.calculateTopPadding(), bottom = it.calculateBottomPadding())
        )
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        BottomNavScreen::class.sealedSubclasses.forEach {
            AddItem(
                screen = it.objectInstance!!,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomNavScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

    NavigationBarItem(
        icon = {
            Icon(
                imageVector = if (selected) screen.selectedIcon else screen.icon,
                contentDescription = stringResource(id = screen.title)
            )
        },
        label = {
            Text(
                text = stringResource(id = screen.title)
            )
        },
        selected = selected,
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        }
    )
}