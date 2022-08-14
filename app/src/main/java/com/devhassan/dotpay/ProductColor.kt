package com.devhassan.dotpay

import com.squareup.moshi.Json

data class ProductColor(
    @Json(name = "hex_value")
    val hexValue: String,

    @Json(name = "colour_name")
    val colourName: String? = null
)