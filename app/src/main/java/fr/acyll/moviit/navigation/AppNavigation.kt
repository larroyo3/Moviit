package fr.acyll.moviit.navigation

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import fr.acyll.moviit.features.onboarding.auth.AuthScreen
import fr.acyll.moviit.features.onboarding.auth.AuthViewModel
import fr.acyll.moviit.features.onboarding.auth.GoogleAuthUiClient
import fr.acyll.moviit.features.onboarding.splash.SplashScreen
import fr.acyll.moviit.navigation.graphs.MainGraph
import fr.acyll.moviit.navigation.graphs.onboardingGraph
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

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
            MainGraph(rootNavController = navController)
        }
    }
}
