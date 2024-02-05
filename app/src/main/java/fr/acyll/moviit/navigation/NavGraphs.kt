package fr.acyll.moviit.navigation

object NavGraphs {
    const val route: String = "root"
    val startGraph: BaseNavGraph = OnboardingGraph

    val navGraphs: List<BaseNavGraph> = listOf(
        OnboardingGraph,
        HomeGraph
    )
}