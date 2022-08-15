package com.devhassan.dotpay.datasource

import com.devhassan.dotpay.api.MakeUpApiService
import com.devhassan.dotpay.model.entity.Product
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val makeUpApiService: MakeUpApiService
) {

    suspend fun getProducts(): List<Product> {
        return makeUpApiService.getProducts()
    }

}
