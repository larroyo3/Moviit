package fr.acyll.moviit.data.repository

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class BaseRepository {

    protected val scope = CoroutineScope(Dispatchers.Default)

    private fun <T> invoke(
        query: Flow<T>,
        onLoading: (Boolean) -> Unit,
        onEach: (T) -> Unit,
        onCompletion: () -> Unit,
        onError: (Throwable) -> Unit
    ): Job {
        return scope.launch {
            query
                .onStart { onLoading(true) }
                .catch { error ->
                    onLoading(false)
                    logError(error)
                    onError(error)
                }
                .onEach { onEach(it) }
                .onCompletion {
                    onLoading(false)
                    onCompletion()
                }
                .flowOn(Dispatchers.Main)
                .collect()
        }
    }

    suspend fun <T> safeApiCall(
        query: Flow<T>
    ) : Result<T> {
        return try {
            query
                .map { Result.success(it) }
                .catch { error ->
                    logError(error)
                    emit(Result.failure(error))
                }
                .flowOn(Dispatchers.Main)
                .single()
        } catch (error: Throwable) {
            logError(error)
            Result.failure(error)
        }
    }

    private fun logError(throwable: Throwable) {
        when (throwable) {
            else -> Log.d("Moviit", throwable.message.toString())
        }
    }

    internal fun <T> Flow<T>.asCallback(
        onLoading: (Boolean) -> Unit,
        onEach: (T) -> Unit,
        onCompletion: () -> Unit,
        onError: (Throwable) -> Unit
    ): Job {
        return invoke(
            query = this,
            onLoading = onLoading,
            onEach = onEach,
            onCompletion = onCompletion,
            onError = onError
        )
    }
}