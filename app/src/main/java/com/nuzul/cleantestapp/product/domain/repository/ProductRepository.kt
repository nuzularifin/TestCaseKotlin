package com.nuzul.cleantestapp.product.domain.repository

import com.nuzul.cleantestapp.product.domain.model.Product

interface ProductRepository {
    suspend fun getProductList(): List<Product>
    suspend fun searchProductList(query: String): Map<String, Any?>
    suspend fun addProduct(
        sku: String,
        name: String,
        qty: Int,
        price: Int,
        unit: String,
        status: Int
    ): Map<String, Any?>

    suspend fun deleteProduct(sku: String): Map<String, Any?>
    suspend fun updateProduct(
        sku: String,
        name: String,
        qty: Int,
        price: Int,
        unit: String,
        status: Int
    ): Map<String, Any?>
}