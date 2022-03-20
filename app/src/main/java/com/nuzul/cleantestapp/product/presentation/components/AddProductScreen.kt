package com.nuzul.cleantestapp.product.presentation.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.nuzul.cleantestapp.authentication.presentation.LoginViewModel
import com.nuzul.cleantestapp.core.Screen
import com.nuzul.cleantestapp.product.domain.model.Product
import com.nuzul.cleantestapp.product.presentation.ProductViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddProductScreen(
    navController: NavHostController,
    viewModel: ProductViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    var context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    var sku by remember {
        mutableStateOf("")
    }
    var name by remember {
        mutableStateOf("")
    }
    var qty by remember {
        mutableStateOf("")
    }
    var price by remember {
        mutableStateOf("")
    }
    var unit by remember {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = true) {
        viewModel.sharedFlow.collectLatest { event ->
            when (event) {
                is ProductViewModel.NavEvent.Navigate
                -> {
                    navController.navigateUp()
                }
            }
        }
    }

    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is ProductViewModel.UIEvent.ShowToast -> {
                    Toast.makeText(
                        context,
                        event.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = "Add Product") },
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
                }
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxSize()
            ) {
                OutlinedTextField(
                    value = sku,
                    placeholder = { Text(text = "OBT-000") },
                    label = { Text(text = "SKU") },
                    onValueChange = {
                        sku = it
                    },
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = name,
                    placeholder = { Text(text = "Product Name") },
                    label = { Text(text = "Product Name") },
                    onValueChange = {
                        name = it
                    },
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = qty,
                    placeholder = { Text(text = "Quantity") },
                    label = { Text(text = "Qty") },
                    onValueChange = {
                        qty = it
                    },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = price,
                    placeholder = { Text(text = "OBT-000") },
                    label = { Text(text = "Price") },
                    onValueChange = {
                        price = it
                    },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = unit,
                    placeholder = { Text(text = "Carbon") },
                    label = { Text(text = "Unit") },
                    onValueChange = {
                        unit = it
                    },
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(16.dp))
                if (state.isLoading){
                    CircularProgressIndicator()
                } else {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                            .padding(12.dp),
                        onClick = {
                            if (sku.isNotBlank() && name.isNotBlank() && qty.isNotBlank() && price.isNotBlank() && unit.isNotBlank()) {
                                viewModel.addProduct(sku, name, Integer.parseInt(qty), Integer.parseInt(price), unit)
                            } else {
                                Toast.makeText(
                                    context,
                                    "Please check your input again",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }) {
                        Text(
                            "Add Product", color = Color.White,
                            modifier = Modifier.padding(horizontal = 12.dp)
                        )
                    }
                }
            }

        }
    }
}