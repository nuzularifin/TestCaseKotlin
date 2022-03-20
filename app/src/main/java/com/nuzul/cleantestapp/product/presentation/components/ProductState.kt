package com.nuzul.cleantestapp.product.presentation.components

import com.nuzul.cleantestapp.product.domain.model.Product

data class ProductState(
    val isLoading: Boolean = false,
    val listProduct: List<Product> = emptyList(),
    val error: String = "",
)