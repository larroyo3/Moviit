package fr.acyll.moviit.navigation

sealed class Screen(val route: String) {

    // Feature Onboarding
    data object Splash: Screen("splash_screen")
    data object Auth: Screen("auth_screen")
    data object HomeNavigator: Screen("home_navigator")
}