package com.devhassan.dotpay.model.uistate

sealed class AppUIState {
    data class Success(
        val brandFragmentUIState: BrandFragmentUIState?,
        val productTypeFragmentUIState: ProductTypeFragmentUIState?,
        val productFragmentUIState: ProductFragmentUIState?,
        val productDetailsFragmentUIState: ProductDetailsFragmentUIState?
    ): AppUIState()
    data class Error(val errorMessage: String): AppUIState()
    object Loading : AppUIState()
}