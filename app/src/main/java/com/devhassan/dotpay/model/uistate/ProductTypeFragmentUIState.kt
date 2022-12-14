package com.devhassan.dotpay.model.uistate

import com.devhassan.dotpay.model.entity.ProductType

data class ProductTypeFragmentUIState(
    val productTypes: List<ProductType>? = null,
    val errorMessage: String? = null
)
