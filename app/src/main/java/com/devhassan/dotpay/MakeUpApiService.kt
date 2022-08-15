package com.devhassan.dotpay

import com.devhassan.dotpay.model.Product
import retrofit2.http.*

interface MakeUpApiService {

    @GET("products.json")
    suspend fun getProducts(): List<Product>

}