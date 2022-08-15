package com.devhassan.dotpay.model.uistate

import com.devhassan.dotpay.model.Product

data class ProductDetailsFragmentUIState(
    val product: Product? = null,
    val errorMessage: String? = null
)
