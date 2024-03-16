package fr.acyll.moviit.data.remote.services

import android.util.Log
import fr.acyll.moviit.data.remote.dto.MovieDTO
import fr.acyll.moviit.data.remote.dto.MoviesDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApiService {
    @GET("movie/{id}")
    suspend fun getMovieById(
        @Path("id") id: Int,
        @Query("append_to_response") credit: String = "credits",
        @Query("language") language: String = "fr-FR"
    ): MovieDTO

    @GET("search/movie")
    suspend fun searchMovieByTitle(
        @Query("query") query: String,
        @Query("language") language: String = "fr-FR",
    ): MoviesDTO
}

class MoviesService(
    private val apiService: MoviesApiService
) {
    fun getMovieById(
        id: Int
    ): Flow<MovieDTO> {
        Log.i("Moviit", "Get movie with id $id")

        return flow {
            val response = apiService.getMovieById(id)
            emit(response)
        }
    }

    fun searchMovieByTitle(
        query: String
    ): Flow<MoviesDTO> {
        Log.i("Moviit", "Get movies with query $query")

        return flow {
            val response = apiService.searchMovieByTitle(query)
            emit(response)
        }
    }
}