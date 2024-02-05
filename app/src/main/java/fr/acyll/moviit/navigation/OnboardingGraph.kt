package fr.acyll.moviit.navigation

import android.content.Context
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import fr.acyll.moviit.features.onboarding.auth.AuthScreen
import fr.acyll.moviit.features.onboarding.splash.SplashScreen

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
            SplashScreen()
        }

        composable(
            route = Screen.Auth.route
        ) {
            AuthScreen()
        }
    }
}