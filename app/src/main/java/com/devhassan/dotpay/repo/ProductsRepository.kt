package com.devhassan.dotpay.repo

import com.devhassan.dotpay.APIResponse
import com.devhassan.dotpay.NetworkConnectivityManager
import com.devhassan.dotpay.adapter.RemoteDataSource
import com.devhassan.dotpay.model.result.ApiResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class ProductsRepository @Inject constructor(
    private val dataSource: RemoteDataSource,
    private val networkConnectivityManager: NetworkConnectivityManager,
    private val dispatcher: CoroutineDispatcher
) : BaseRepository() {
    suspend fun fetchProducts() = withContext(dispatcher) {
        when (val apiResult = coroutineHandler(dispatcher, networkConnectivityManager) {
            dataSource.getProducts()
        }) {
            is ApiResult.Success -> {
                Timber.d("$apiResult")
                APIResponse(status = true, data = apiResult.response)
            }
            is ApiResult.Error -> {
                Timber.d("$apiResult")
                APIResponse(status = false, message = apiResult.message)
            }
        }
    }

}