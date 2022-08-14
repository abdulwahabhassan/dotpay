package com.devhassan.dotpay

import com.squareup.moshi.Json

data class Product (
    val id: Long,
    val brand: String? = null,
    val name: String,
    val price: String? = null,

    @Json(name = "price_sign")
    val priceSign: String? = null,

    val currency: String? = null,

    @Json(name = "image_link")
    val imageLink: String,

    @Json(name = "product_link")
    val productLink: String,

    @Json(name = "website_link")
    val websiteLink: String,

    val description: String? = null,
    val rating: Double? = null,
    val category: String? = null,

    @Json(name = "product_type")
    val productType: String,

    @Json(name = "tag_list")
    val tagList: List<String>,

    @Json(name = "created_at")
    val createdAt: String,

    @Json(name = "updated_at")
    val updatedAt: String,

    @Json(name = "product_api_url")
    val productAPIURL: String,

    @Json(name = "api_featured_image")
    val apiFeaturedImage: String,

    @Json(name = "product_colors")
    val productColors: List<ProductColor>
) {
    companion object {
        val products = listOf<Product>(
            Product(
                0,
                "My brand",
                "Chopsticks",
                "3900",
                "#",
                "CAD",
                "",
                "",
                "",
                "",
                3.0,
                "",
                "",
                emptyList(),
                "",
                "",
                "",
                "",
                emptyList(),
            ),
            Product(
                1,
                "My brand",
                "Chopsticks",
                "3900",
                "#",
                "CAD",
                "",
                "",
                "",
                "",
                3.0,
                "",
                "",
                emptyList(),
                "",
                "",
                "",
                "",
                emptyList(),
            ),
            Product(
                2,
                "My brand",
                "Chopsticks",
                "3900",
                "#",
                "CAD",
                "",
                "",
                "",
                "",
                3.0,
                "",
                "",
                emptyList(),
                "",
                "",
                "",
                "",
                emptyList(),
            ),
            Product(
                3,
                "My brand",
                "Chopsticks",
                "3900",
                "#",
                "CAD",
                "",
                "",
                "",
                "",
                3.0,
                "",
                "",
                emptyList(),
                "",
                "",
                "",
                "",
                emptyList(),
            ),
            Product(
                4,
                "My brand",
                "Chopsticks",
                "3900",
                "#",
                "CAD",
                "",
                "",
                "",
                "",
                3.0,
                "",
                "",
                emptyList(),
                "",
                "",
                "",
                "",
                emptyList(),
            )
        )
    }
}