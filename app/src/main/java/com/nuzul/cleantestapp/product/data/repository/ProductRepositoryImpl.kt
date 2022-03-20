package com.nuzul.cleantestapp.product.data.repository

import android.content.Context
import com.nuzul.cleantestapp.core.utils.SharedPrefs
import com.nuzul.cleantestapp.product.data.remote.ProductAPI
import com.nuzul.cleantestapp.product.domain.model.Product
import com.nuzul.cleantestapp.product.domain.repository.ProductRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: ProductAPI,
    private val context: Context
): ProductRepository{
    override suspend fun getProductList(): List<Product> {
        return api.getProductList();
    }

    override suspend fun searchProductList(query: String): Map<String, Any?> {
        val querySearch = query.toRequestBody("text/plain".toMediaTypeOrNull())
        val token = SharedPrefs(context).getToken();
        return api.searchProductList(token, querySearch)
    }

    override suspend fun addProduct(sku: String, name: String, qty: Int, price: Int, unit: String, status: Int): Map<String, Any?> {
        val token = SharedPrefs(context).getToken();
        val skuData = sku.toRequestBody("text/plain".toMediaTypeOrNull())
        val nameData = name.toRequestBody("text/plain".toMediaTypeOrNull())
        val qtyData = qty.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val priceData = price.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val unitData = unit.toRequestBody("text/plain".toMediaTypeOrNull())
        val statusData = status.toString().toRequestBody("text/plain".toMediaTypeOrNull())
       return api.addProduct(
           token, skuData, nameData, qtyData, priceData, unitData, statusData
       )
    }

    override suspend fun deleteProduct(sku: String) : Map<String, Any?> {
        val token = SharedPrefs(context).getToken();
        val skuData = sku.toRequestBody("text/plain".toMediaTypeOrNull())
        return api.deleteProduct(token, skuData)
    }

    override suspend fun updateProduct(
        sku: String,
        name: String,
        qty: Int,
        price: Int,
        unit: String,
        status: Int
    ): Map<String, Any?> {
        val token = SharedPrefs(context).getToken();
        val skuData = sku.toRequestBody("text/plain".toMediaTypeOrNull())
        val nameData = name.toRequestBody("text/plain".toMediaTypeOrNull())
        val qtyData = qty.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val priceData = price.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val unitData = unit.toRequestBody("text/plain".toMediaTypeOrNull())
        val statusData = status.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        return api.updateProduct(
            token, skuData, nameData, qtyData, priceData, unitData, statusData
        )
    }

}