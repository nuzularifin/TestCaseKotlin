package com.nuzul.cleantestapp.authentication.presentation.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.ThumbUp
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nuzul.cleantestapp.authentication.presentation.LoginViewModel
import com.nuzul.cleantestapp.authentication.presentation.RegisterViewModel
import com.nuzul.cleantestapp.core.Screen
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val state = viewModel.state.value
    var context = LocalContext.current
    var email by remember {
        mutableStateOf("")
    }
    var password by remember { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is RegisterViewModel.UIEvent.ShowSnackBar
                -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = "Register") },
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
        },
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
        ) {
            Text(
                text = "Register new Account", style = MaterialTheme.typography.h2,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = email,
                placeholder = { Text(text = "user@email.com") },
                label = { Text(text = "Email") },
                maxLines = 1,
                onValueChange = {
                    email = it
                })
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = password,
                placeholder = { Text(text = "******") },
                maxLines = 1,
                label = { Text(text = "Password") },
                onValueChange = {
                    password = it
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Rounded.ThumbUp
                    else Icons.Rounded.Lock

                    // Please provide localized description for accessibility services
                    val description = if (passwordVisible) "Hide password" else "Show password"

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, description)
                    }
                }
            )
            Spacer(modifier = Modifier.height(12.dp))
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                Button(onClick = {
                    if (email.isNotBlank() && password.isNotBlank()) {
                        viewModel.requestRegister(email = email, password = password)
                    } else {
                        Toast.makeText(
                            context,
                            "Please enter email and password",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }) {
                    Text(
                        "Register", color = Color.White,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }
            }
        }
    }
}