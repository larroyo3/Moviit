package fr.acyll.moviit.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MoviesDTO(
    val page: Int? = null,
    val results: List<MovieDTO>

)

data class MovieDTO(
    val id: Int,
    val title: String,
    @SerializedName("poster_path") val poster: String? = null,
    @SerializedName("release_date") val releaseDate: String? = null
)

