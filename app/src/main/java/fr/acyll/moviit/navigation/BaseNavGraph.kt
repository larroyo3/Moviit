package fr.acyll.moviit.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

interface BaseNavGraph {
    val startDestination: String
    val graphRoute: String

    fun buildNavGraph(
        navController: NavHostController,
        navGraphBuilder: NavGraphBuilder
    )
}