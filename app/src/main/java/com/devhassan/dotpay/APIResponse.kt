package com.devhassan.dotpay

import com.devhassan.dotpay.model.Product

data class APIResponse(
    var status: Boolean = false,
    var message: String? = null,
    val data: List<Product> = emptyList()
)
