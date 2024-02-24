package fr.acyll.moviit.navigation.graphs

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.google.android.gms.auth.api.identity.Identity
import fr.acyll.moviit.features.onboarding.auth.AuthScreen
import fr.acyll.moviit.features.onboarding.auth.AuthViewModel
import fr.acyll.moviit.features.onboarding.auth.GoogleAuthUiClient
import fr.acyll.moviit.features.onboarding.splash.SplashScreen
import fr.acyll.moviit.navigation.NavGraphs
import fr.acyll.moviit.navigation.Screen
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.onboardingGraph(
    navController: NavHostController,
    lifecycleScope: LifecycleCoroutineScope,
    googleAuthUiClient: GoogleAuthUiClient
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
                    if(googleAuthUiClient.getSignedInUser() != null) {
                        navController.navigate(NavGraphs.MAIN) {
                            popUpTo(Screen.Splash.route) {
                                inclusive = true
                            }
                        }
                    } else {
                        navController.navigate(Screen.Auth.route) {
                            popUpTo(Screen.Splash.route) {
                                inclusive = true
                            }
                        }
                    }

                }
            )
        }

        composable(
            route = Screen.Auth.route
        ) {
            val authViewModel: AuthViewModel = koinViewModel()

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult(),
                onResult = { result ->
                    if(result.resultCode == Activity.RESULT_OK) {
                        lifecycleScope.launch {
                            val signInResult = googleAuthUiClient.signInWithIntent(
                                intent = result.data ?: return@launch
                            )
                            authViewModel.onSignInResult(signInResult)
                        }
                    }
                }
            )

            AuthScreen(
                viewModel = authViewModel,
                navigateToHomeScreen = {
                    navController.navigate(NavGraphs.MAIN) {
                        popUpTo(Screen.Splash.route) {
                            inclusive = true
                        }
                    }
                },
                onContinueClick = {
                    lifecycleScope.launch {
                        val signInIntentSender = googleAuthUiClient.signIn()
                        launcher.launch(
                            IntentSenderRequest.Builder(
                                signInIntentSender ?: return@launch
                            ).build()
                        )
                    }
                }
            )
        }
    }
}