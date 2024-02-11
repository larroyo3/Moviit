package fr.acyll.moviit.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import fr.acyll.moviit.features.onboarding.auth.AuthScreen
import fr.acyll.moviit.features.onboarding.splash.SplashScreen
import fr.acyll.moviit.navigation.NavGraphs
import fr.acyll.moviit.navigation.Screen

fun NavGraphBuilder.onboardingGraph(
    navController: NavHostController,
) {
    navigation(
        route = NavGraphs.ONBOARDING,
        startDestination = Screen.Splash.route
    ) {
        composable(
            route = Screen.Splash.route
        ) {
            SplashScreen(
                navigateToNextScreen = {
                    navController.navigate(Screen.Auth.route) {
                        popUpTo(Screen.Splash.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(
            route = Screen.Auth.route
        ) {
            AuthScreen(
                navigateToHomeScreen = {
                    navController.navigate(NavGraphs.MAIN)
                }
            )
        }
    }
}