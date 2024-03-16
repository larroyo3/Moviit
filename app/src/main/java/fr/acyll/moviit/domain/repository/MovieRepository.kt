package fr.acyll.moviit.domain.repository

import fr.acyll.moviit.domain.model.movies.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getMoviesById(
        id: Int
    ): Flow<Result<MovieEntity>>

    suspend fun searchMoviesByTitle(
        query: String
    ): Flow<Result<List<MovieEntity>>>
}