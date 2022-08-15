package com.devhassan.dotpay.model.entity

import com.squareup.moshi.Json

data class Product (
    val id: Long = 0,
    val brand: String? = "null",
    val name: String? = null,
    val price: String? = null,

    @Json(name = "price_sign")
    val priceSign: String? = null,

    val currency: String? = null,

    @Json(name = "image_link")
    val imageLink: String? = null,

    @Json(name = "product_link")
    val productLink: String? = null,

    @Json(name = "website_link")
    val websiteLink: String? = null,

    val description: String? = null,
    val rating: Double? = null,
    val category: String? = null,

    @Json(name = "product_type")
    val productType: String? = null,

    @Json(name = "tag_list")
    val tagList: List<String>? = emptyList(),

    @Json(name = "created_at")
    val createdAt: String? = null,

    @Json(name = "updated_at")
    val updatedAt: String? = null,

    @Json(name = "product_api_url")
    val productAPIURL: String? = null,

    @Json(name = "api_featured_image")
    val apiFeaturedImage: String? = null,

    @Json(name = "product_colors")
    val productColors: List<ProductColor>? = emptyList()
)