package com.devhassan.dotpay.adapter

import com.devhassan.dotpay.MakeUpApiService
import com.devhassan.dotpay.model.Product
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val makeUpApiService: MakeUpApiService
) {

    suspend fun getProducts(): List<Product> {
        return makeUpApiService.getProducts()
    }

}
