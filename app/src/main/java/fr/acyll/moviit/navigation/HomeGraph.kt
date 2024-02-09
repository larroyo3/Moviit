package fr.acyll.moviit.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import fr.acyll.moviit.features.main.home.HomeScreen
import fr.acyll.moviit.features.main.map.MapScreen
import fr.acyll.moviit.features.main.profile.ProfileScreen

fun NavGraphBuilder.home(
    navController: NavHostController,
) {
    navigation(
        route = NavGraphs.HOME,
        startDestination = Screen.Splash.route
    ) {
        composable(
            route = Screen.Home.route
        ) {
            HomeScreen()
        }

        composable(
            route = Screen.Map.route
        ) {
            MapScreen()
        }

        composable(
            route = Screen.Profile.route
        ) {
            ProfileScreen()
        }
    }
}