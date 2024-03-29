package fr.acyll.moviit.navigation

sealed class Screen(val route: String) {

    // Feature Onboarding
    data object Splash: Screen("splash_screen")
    data object Auth: Screen("auth_screen")

    data object Contribute: Screen("contribute_screen")
    data object Publish: Screen("publish_screen?id={id}") {
        fun routeWithArguments(id: String) = "publish_screen?id=$id"
    }
}