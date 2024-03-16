package fr.acyll.moviit.di

import android.util.Log
import fr.acyll.moviit.BuildConfig.TMBD_API_KEY
import fr.acyll.moviit.data.remote.services.MoviesApiService
import fr.acyll.moviit.data.remote.services.MoviesService
import fr.acyll.moviit.data.repository.MoviesRepositoryImp
import fr.acyll.moviit.domain.repository.MovieRepository
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.nio.charset.Charset


val coreModule = module {
    single { provideRetrofit(TMBD_API_KEY) }
    single { provideMoviesApiService(get()) }

}

val logicModule = module {
    single { MoviesService(get()) }
}

val repositoryModule = module {
    single<MovieRepository> { MoviesRepositoryImp(moviesService = get()) }
}

fun provideRetrofit(apiKey: String): Retrofit {
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(apiKey))
        .addInterceptor(LoggingInterceptor())
        .build()

    return Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
}

fun provideMoviesApiService(retrofit: Retrofit): MoviesApiService {
    return retrofit.create(MoviesApiService::class.java)
}

class AuthInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val urlWithApiKey = originalUrl.newBuilder()
            .addQueryParameter("api_key", apiKey)
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(urlWithApiKey)
            .build()

        return chain.proceed(newRequest)
    }
}

class LoggingInterceptor : Interceptor {

    private val logger = HttpLoggingInterceptor.Logger { message ->
        // Log personnalisé pour inclure la requête et la réponse
        println(message)
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBody = request.body

        // Log de la requête
        logger.log("Sending request: ${request.method} ${request.url}")
        if (requestBody != null) {
            val buffer = Buffer()
            requestBody.writeTo(buffer)
            val charset: Charset = requestBody.contentType()?.charset(Charset.forName("UTF-8")) ?: Charset.forName("UTF-8")
            logger.log(buffer.readString(charset))
        }

        // Procéder à la requête
        val response = chain.proceed(request)

        // Log de la réponse
        logger.log("Received response: ${response.code} ${response.message} ${response.request.url}")
        val responseBody = response.body
        if (responseBody != null) {
            val source = responseBody.source()
            source.request(Long.MAX_VALUE) // Buffer the entire body.
            val buffer = source.buffer
            val charset: Charset = responseBody.contentType()?.charset(Charset.forName("UTF-8")) ?: Charset.forName("UTF-8")
            logger.log(buffer.clone().readString(charset))
        }

        return response
    }
}