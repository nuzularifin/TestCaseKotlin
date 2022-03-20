package com.nuzul.cleantestapp.product.data.remote

import com.nuzul.cleantestapp.core.utils.WrappedListResponse
import com.nuzul.cleantestapp.product.domain.model.Product
import okhttp3.RequestBody
import okhttp3.Response
import retrofit2.http.*

interface ProductAPI {

    @GET("items")
    suspend fun getProductList() : List<Product>

    @Multipart
    @POST("item/add")
    suspend fun deleteProduct(
        @Header("Authorization") token: String,
        @Part("sku") sku: RequestBody,
    ) : Map<String, Any?>

    @Multipart
    @POST("item/add")
    suspend fun addProduct(
        @Header("Authorization") token: String,
        @Part("sku") sku: RequestBody,
        @Part("product_name") productName: RequestBody,
        @Part("qty") qty: RequestBody,
        @Part("price") price: RequestBody,
        @Part("unit") unit: RequestBody,
        @Part("status") int: RequestBody,
    ) : Map<String, Any?>

    @Multipart
    @POST("item/update")
    suspend fun updateProduct(
        @Header("Authorization") token: String,
        @Part("sku") sku: RequestBody,
        @Part("product_name") productName: RequestBody,
        @Part("qty") qty: RequestBody,
        @Part("price") price: RequestBody,
        @Part("unit") unit: RequestBody,
        @Part("status") int: RequestBody,
    ) : Map<String, Any>

    @Multipart
    @POST("item/search")
    suspend fun searchProductList(
        @Header("Authorization") token: String,
        @Part("sku") query: RequestBody,
    ): Map<String, Any?>
}