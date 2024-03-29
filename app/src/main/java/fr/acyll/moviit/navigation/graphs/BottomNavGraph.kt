package fr.acyll.moviit.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import fr.acyll.moviit.features.contribute.ContributeScreen
import fr.acyll.moviit.features.contribute.ContributeViewModel
import fr.acyll.moviit.features.main.home.HomeScreen
import fr.acyll.moviit.features.main.map.MapScreen
import fr.acyll.moviit.features.main.account.AccountScreen
import fr.acyll.moviit.features.main.account.AccountViewModel
import fr.acyll.moviit.features.main.home.HomeViewModel
import fr.acyll.moviit.features.main.map.MapViewModel
import fr.acyll.moviit.features.main.settings.SettingsScreen
import fr.acyll.moviit.features.main.settings.SettingsViewModel
import fr.acyll.moviit.features.publish.PublishScreen
import fr.acyll.moviit.features.publish.PublishViewModel
import fr.acyll.moviit.navigation.BottomNavScreen
import fr.acyll.moviit.navigation.NavGraphs
import fr.acyll.moviit.navigation.Screen
import org.koin.androidx.compose.koinViewModel

@Composable
fun BottomNavGraph(
    rootNavController: NavHostController,
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = BottomNavScreen.Home.route
    ) {
        composable(route = BottomNavScreen.Home.route) {
            val homeViewModel: HomeViewModel = koinViewModel()

            HomeScreen(
                viewModel = homeViewModel
            )
        }

        composable(route = BottomNavScreen.Map.route) {
            val mapViewModel: MapViewModel = koinViewModel()

            MapScreen(
                viewModel = mapViewModel,
                navigateToPublishScreen = {
                    navController.navigate(Screen.Publish.routeWithArguments(it))
                }
            )
        }

        composable(
            route = BottomNavScreen.P_Account.route
        ) {
            val accountViewModel: AccountViewModel = koinViewModel()

            AccountScreen(
                viewModel = accountViewModel
            )
        }

        composable(
            route = BottomNavScreen.Settings.route
        ) {
            val settingsViewModel: SettingsViewModel = koinViewModel()

            SettingsScreen(
                viewModel = settingsViewModel,
                redirectToOnboarding = {
                    rootNavController.navigate(Screen.Auth.route) {
                        popUpTo(NavGraphs.MAIN) {
                            inclusive = true
                        }
                    }
                },
                navigateToScreen = {
                    navController.navigate(it)
                }
            )
        }

        composable(
            route = Screen.Publish.route,
            arguments = listOf(
                navArgument("id") { type = NavType.StringType }
            )
        ) {
            val publishViewModel: PublishViewModel = koinViewModel()
            val shootingPlaceId = it.arguments?.getString("id") ?: ""

            PublishScreen(
                viewModel = publishViewModel,
                shootingPlaceId = shootingPlaceId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.Contribute.route
        ) {
            val contributeViewModel: ContributeViewModel = koinViewModel()

            ContributeScreen(
                viewModel = contributeViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}