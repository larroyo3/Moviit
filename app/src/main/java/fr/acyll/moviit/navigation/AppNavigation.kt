package fr.acyll.moviit.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController

@Composable
fun AppNavigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = NavGraphs.startGraph.graphRoute,
        route = NavGraphs.route
    ) {
        NavGraphs.navGraphs.forEach { navGraph ->
            navGraph.buildNavGraph(navController = navController, this)
        }
    }
}
