package com.devhassan.dotpay.repo

import com.devhassan.dotpay.network.NetworkConnectivityManager
import com.devhassan.dotpay.model.result.ApiResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import timber.log.Timber

@Suppress("UNCHECKED_CAST")
open class BaseRepository {

    suspend fun <T> coroutineHandler(
        dispatcher: CoroutineDispatcher,
        networkConnectivityManager: NetworkConnectivityManager,
        apiRequest: suspend () -> T
    ): ApiResult<T> {
        return withContext(dispatcher) {
            if (!networkConnectivityManager.isNetworkAvailable()) {
                ApiResult.Error("Check your internet connection!")
            } else {
                try {
                    ApiResult.Success(apiRequest.invoke())
                } catch (e: HttpException) {
                    Timber.d("Http Error Http Status Code - ${e.code()} ")
                    ApiResult.Error(e.message())
                } catch (e: Exception) {
                    Timber.d("Exception Error ${e.message}")
                    ApiResult.Error(e.localizedMessage)
                }
            }
        }
    }

}