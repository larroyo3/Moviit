package fr.acyll.moviit.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

@Composable
fun AppNavigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        route = NavGraphs.ROOT,
        startDestination = NavGraphs.ONBOARDING
    ) {
        onboardingGraph(navController = navController)

        composable(route = NavGraphs.HOME) {

        }
    }
}
