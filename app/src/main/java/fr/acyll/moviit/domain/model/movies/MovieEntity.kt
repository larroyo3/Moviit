package fr.acyll.moviit.domain.model.movies

import fr.acyll.moviit.data.remote.dto.MovieDTO

data class MovieEntity(
    val id: Int,
    val title: String,
    val poster: String? = null,
    val releaseDate: String? = null
) {
    companion object {
        fun fromDTO(dto: MovieDTO): MovieEntity {
            return MovieEntity(
                id = dto.id,
                title = dto.title,
                poster = dto.poster,
                releaseDate = dto.releaseDate
            )
        }
    }
}
