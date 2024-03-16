package fr.acyll.moviit.domain.model

data class ShootingPlace(
    val title: String = "",
    val director: String = "",
    val releaseDate: String = "",
    val synopsis: String = "",
    val latitude: String = "",
    val longitude: String = "",
    val place: String = "",
    val contributorId: String = "",
    val moviePosterUrl: String? = null,
    val id: String = "",
)
