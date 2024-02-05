package fr.acyll.moviit.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import fr.acyll.moviit.features.onboarding.auth.AuthScreen
import fr.acyll.moviit.features.onboarding.splash.SplashScreen

object OnboardingGraph: BaseNavGraph {
    override val startDestination: String = Screen.Splash.route
    override val graphRoute: String = "onboarding"

    override fun buildNavGraph(
        navController: NavHostController,
        navGraphBuilder: NavGraphBuilder
    ) {
        navGraphBuilder.navigation(
            startDestination = startDestination,
            route = graphRoute
        ) {

            composable(
                route = Screen.Splash.route
            ) {
                SplashScreen()
            }

            composable(
                route = Screen.Auth.route
            ) {
                AuthScreen()
            }
        }
    }
}