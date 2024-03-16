package fr.acyll.moviit.data.repository

import fr.acyll.moviit.data.remote.services.MoviesService
import fr.acyll.moviit.domain.model.movies.MovieEntity
import fr.acyll.moviit.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MoviesRepositoryImp(
    val moviesService: MoviesService
): MovieRepository, BaseRepository() {

    override suspend fun getMoviesById(
        id: Int
    ): Flow<Result<MovieEntity>> = flowOf(
        safeApiCall(
            moviesService.getMovieById(id)
        ).map { result ->
            MovieEntity.fromDTO(result)
        }
    )

    override suspend fun searchMoviesByTitle(
        query: String
    ): Flow<Result<List<MovieEntity>>> = flowOf(
        safeApiCall(
            moviesService.searchMovieByTitle(query)
        ).map { result ->
            result.results.map {
                MovieEntity.fromDTO(it)
            }
        }
    )
}