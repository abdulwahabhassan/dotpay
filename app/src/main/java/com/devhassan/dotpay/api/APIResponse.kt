package com.devhassan.dotpay.api

import com.devhassan.dotpay.model.entity.Product

data class APIResponse(
    var status: Boolean = false,
    var message: String? = null,
    val data: List<Product> = emptyList()
)
