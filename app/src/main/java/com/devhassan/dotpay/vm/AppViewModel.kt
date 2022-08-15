package com.devhassan.dotpay.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devhassan.dotpay.model.entity.Brand
import com.devhassan.dotpay.model.entity.Product
import com.devhassan.dotpay.model.entity.ProductType
import com.devhassan.dotpay.model.uistate.*
import com.devhassan.dotpay.repo.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val transactionsRepository: ProductsRepository
) : ViewModel() {

    private var _appUIState:
            MutableLiveData<AppUIState> =
        MutableLiveData(AppUIState.Loading)
    val appUIState: MutableLiveData<AppUIState> = _appUIState

    private val _selectedBrand = MutableStateFlow("")
    private val _selectedProductType = MutableStateFlow("")
    private val _selectedProduct = MutableStateFlow(Product())

    init {
        retrieveProducts()
    }

    fun retrieveProducts() {
        viewModelScope.launch {
            combine(
                flowOf(transactionsRepository.fetchProducts())
                    .shareIn(viewModelScope, SharingStarted.WhileSubscribed()),
                _selectedBrand,
                _selectedProductType,
                _selectedProduct
            ) { apiResponse, selectedBrand, selectedProductType, selectedProduct ->

                Timber.d(
                    "$selectedBrand " +
                            "$selectedProductType " +
                            "$selectedProduct " +
                            "$apiResponse"
                )

                when (apiResponse.status) {
                    true -> {
                        val brands = apiResponse.data.groupBy { product -> product.brand }.map { map ->
                            map.key?.let { Brand(it, map.value) }
                        }.filterNotNull()

                        val productTypes = brands.find { brand -> brand.name == selectedBrand }
                            ?.products?.groupBy { product -> product.productType }?.map { map ->
                                map.key?.let { ProductType(it, map.value) }
                            }?.filterNotNull()

                        val products = productTypes?.find { productType ->
                            productType.name == selectedProductType
                        }?.products

                        AppUIState.Success(
                            brandFragmentUIState = BrandFragmentUIState(
                                brands = brands
                            ),
                            productDetailsFragmentUIState = ProductDetailsFragmentUIState(
                                product = selectedProduct
                            ),
                            productFragmentUIState = ProductFragmentUIState(
                                products = products
                            ),
                            productTypeFragmentUIState = ProductTypeFragmentUIState(
                                productTypes = productTypes
                            )
                        )
                    }
                    false -> {
                        AppUIState.Error(
                            errorMessage = apiResponse.message ?: "Unknown Error"
                        )
                    }
                }

            }.collectLatest { appUiState ->
                _appUIState.value = appUiState
            }
        }
    }

    fun updateSelectedBrand(brand: String) {
        _selectedBrand.value = brand
    }

    fun updateSelectedProductType(productType: String) {
        _selectedProductType.value = productType
    }

    fun updateCurrentProduct(product: Product) {
        _selectedProduct.value = product
    }

}