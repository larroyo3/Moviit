package fr.acyll.moviit.domain.model.movies

import fr.acyll.moviit.data.remote.dto.Credits
import fr.acyll.moviit.data.remote.dto.MovieDTO


data class MovieEntity(
    val id: Int,
    val title: String,
    val poster: String? = null,
    val releaseDate: String? = null,
    val credits: Credits? = null,
    val overview: String? = null
) {
    companion object {
        fun fromDTO(dto: MovieDTO): MovieEntity {
            return MovieEntity(
                id = dto.id,
                title = dto.title,
                poster = TMDB_POSTER_URL + dto.poster,
                releaseDate = dto.releaseDate,
                credits = dto.credits,
                overview = dto.overview
            )
        }

        private const val TMDB_POSTER_URL = "https://image.tmdb.org/t/p/w500"
    }
}
