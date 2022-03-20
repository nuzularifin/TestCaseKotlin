package com.nuzul.cleantestapp.core

sealed class Screen(
    val route: String,
) {
    object LoginScreen: Screen("login")
    object RegisterScreen: Screen("register")

    object DashboardProductScreen: Screen("dashboard")
    object AddProductScreen: Screen("addProduct")
    object UpdateProductScreen : Screen("updateProduct")
}