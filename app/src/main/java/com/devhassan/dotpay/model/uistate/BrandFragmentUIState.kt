package com.devhassan.dotpay.model.uistate

import com.devhassan.dotpay.model.entity.Brand

data class BrandFragmentUIState(
    val brands: List<Brand>?,
    val errorMessage: String? = null
)

