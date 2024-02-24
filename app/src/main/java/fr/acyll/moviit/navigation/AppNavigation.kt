package fr.acyll.moviit.navigation

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import fr.acyll.moviit.features.onboarding.auth.GoogleAuthUiClient
import fr.acyll.moviit.navigation.graphs.MainGraph
import fr.acyll.moviit.navigation.graphs.onboardingGraph

@Composable
fun AppNavigation(
    navController: NavHostController,
    lifecycleScope: LifecycleCoroutineScope,
    googleAuthUiClient: GoogleAuthUiClient
) {
    NavHost(
        navController = navController,
        route = NavGraphs.ROOT,
        startDestination = NavGraphs.ONBOARDING
    ) {
        onboardingGraph(
            navController = navController,
            lifecycleScope = lifecycleScope,
            googleAuthUiClient = googleAuthUiClient
        )

        composable(route = NavGraphs.MAIN) {
            MainGraph()
        }
    }
}
