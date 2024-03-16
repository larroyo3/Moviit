package fr.acyll.moviit.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MoviesDTO(
    val page: Int? = null,
    val results: List<MovieDTO>

)

data class MovieDTO(
    val id: Int,
    val title: String,
    val overview: String? = null,
    @SerializedName("poster_path") val poster: String? = null,
    @SerializedName("release_date") val releaseDate: String? = null,
    @SerializedName("credits") val credits: Credits? = null
)

data class Credits(
    val crew: List<Crew>? = null
)

data class Crew(
    val name: String? = null,
    val job: String? = null
)

