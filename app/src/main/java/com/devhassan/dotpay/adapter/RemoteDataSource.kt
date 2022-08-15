package com.devhassan.dotpay.datasource

import com.devhassan.dotpay.MakeUpApiService
import com.devhassan.dotpay.model.Product
import javax.inject.Inject

// Manages all remote data sources that connect to various api services consumed by the app
class RemoteDataSource @Inject constructor(
    private val makeUpApiService: MakeUpApiService
) {

    suspend fun getProducts(): List<Product> {
        return makeUpApiService.getProducts()
    }


}
