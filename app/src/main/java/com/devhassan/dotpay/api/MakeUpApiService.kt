package com.devhassan.dotpay.api

import com.devhassan.dotpay.model.entity.Product
import retrofit2.http.*

interface MakeUpApiService {

    @GET("products.json")
    suspend fun getProducts(): List<Product>

}