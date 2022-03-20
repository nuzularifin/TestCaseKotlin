package com.nuzul.cleantestapp.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.nuzul.cleantestapp.authentication.presentation.components.LoginScreen
import com.nuzul.cleantestapp.authentication.presentation.components.RegisterScreen
import com.nuzul.cleantestapp.core.utils.SharedPrefs
import com.nuzul.cleantestapp.product.domain.model.Product
import com.nuzul.cleantestapp.product.presentation.components.AddProductScreen
import com.nuzul.cleantestapp.product.presentation.components.DashboardProductScreen
import com.nuzul.cleantestapp.product.presentation.components.UpdateProductScreen

@Composable
fun NavigationComponent(
    navController: NavHostController,
) {

    val token = SharedPrefs(LocalContext.current).getToken();
    var startDestination: String = ""
    startDestination = if (token == "") {
        Screen.LoginScreen.route;
    } else {
        Screen.DashboardProductScreen.route;
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController = navController)
        }
        composable(route = Screen.RegisterScreen.route) {
            RegisterScreen(navController = navController)
        }
        composable(route = Screen.DashboardProductScreen.route) {
            DashboardProductScreen(navController = navController)
        }
        composable(route = Screen.AddProductScreen.route) {
            AddProductScreen(navController = navController)
        }
        composable(route = Screen.UpdateProductScreen.route) {
            val productModel = navController.previousBackStackEntry?.savedStateHandle?.get<Product>("product")
            productModel?.let { prd -> UpdateProductScreen(navController = navController, product = prd) }
        }
    }
}

