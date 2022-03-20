package com.nuzul.cleantestapp.product.presentation.components

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nuzul.cleantestapp.authentication.presentation.RegisterViewModel
import com.nuzul.cleantestapp.core.Screen
import com.nuzul.cleantestapp.product.presentation.ProductViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DashboardProductScreen(
    navController: NavController,
    viewModel: ProductViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val state = viewModel.state.value
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is ProductViewModel.UIEvent.ShowToast
                -> {
                    Toast.makeText(
                        context,
                        event.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
    LaunchedEffect(key1 = true) {
        viewModel.refreshFlow.run {
            viewModel.getProductList()
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = "Data Product") },
                backgroundColor = Color.White,
                navigationIcon = if (navController.previousBackStackEntry != null) {
                    {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                } else {
                    null
                },
                actions = {
                     IconButton(onClick = {
                         navController.navigate(Screen.AddProductScreen.route)
                     }) {
                         Icon(
                             imageVector = Icons.Filled.Add,
                             contentDescription = "Add Product"
                         )
                     }
                }
            )
        },
    ) {
        Box(modifier = Modifier.fillMaxSize()){
            Column(){
                TextField(value = viewModel.searchQuery.value,
                    onValueChange = viewModel::onSearch,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(text = "Search by SKU")
                    })
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn(modifier = Modifier.fillMaxHeight()){
                    items(items = state.listProduct) {
                            product -> ProductItem(product = product, onItemClick = {
                                navController.currentBackStackEntry?.savedStateHandle?.set("product", product)
                                navController.navigate(Screen.UpdateProductScreen.route)
                    })
                    }
                }
            }
            if (state.error.isNotBlank()) {
                Text(text = state.error, color = MaterialTheme.colors.error, textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.Center))
            }
            if (state.isLoading){
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }

}