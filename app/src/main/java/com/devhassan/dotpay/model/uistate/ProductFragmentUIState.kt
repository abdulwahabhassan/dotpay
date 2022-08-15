package com.devhassan.dotpay.model.uistate

import com.devhassan.dotpay.model.Product

data class ProductFragmentUIState(
    val products: List<Product>? = null,
    val errorMessage: String? = null
)
