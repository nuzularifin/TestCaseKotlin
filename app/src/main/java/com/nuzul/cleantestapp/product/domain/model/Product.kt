package com.nuzul.cleantestapp.product.domain.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    @SerializedName("id") var id: Int,
    @SerializedName("sku") var sku: String,
    @SerializedName("product_name") var name: String,
    var qty: Int,
    var price: Int,
    var unit: String,
    var status: Int
) : Parcelable