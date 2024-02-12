package fr.acyll.moviit.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fr.acyll.moviit.features.main.home.HomeScreen
import fr.acyll.moviit.features.main.map.MapScreen
import fr.acyll.moviit.features.main.profile.ProfileScreen
import fr.acyll.moviit.navigation.BottomNavScreen

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = BottomNavScreen.Home.route
    ) {
        composable(route = BottomNavScreen.Home.route) {
            HomeScreen()
        }

        composable(route = BottomNavScreen.Map.route) {
            MapScreen()
        }

        composable(route = BottomNavScreen.Profile.route) {
            ProfileScreen()
        }
    }
}