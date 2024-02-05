package fr.acyll.moviit.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import fr.acyll.moviit.features.main.home.HomeScreen
import fr.acyll.moviit.features.main.map.MapScreen
import fr.acyll.moviit.features.main.profile.ProfileScreen

object HomeGraph: BaseNavGraph {
    override val startDestination: String = Screen.Home.route
    override val graphRoute: String = "home"

    override fun buildNavGraph(
        navController: NavHostController,
        navGraphBuilder: NavGraphBuilder
    ) {
        navGraphBuilder.navigation(
            startDestination = OnboardingGraph.startDestination,
            route = OnboardingGraph.graphRoute
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
}