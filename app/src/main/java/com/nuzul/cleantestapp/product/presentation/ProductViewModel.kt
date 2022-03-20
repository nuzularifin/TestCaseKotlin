package com.nuzul.cleantestapp.product.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nuzul.cleantestapp.authentication.presentation.LoginViewModel
import com.nuzul.cleantestapp.authentication.presentation.components.LoginState
import com.nuzul.cleantestapp.core.Resource
import com.nuzul.cleantestapp.core.Screen
import com.nuzul.cleantestapp.product.domain.model.Product
import com.nuzul.cleantestapp.product.domain.usecase.*
import com.nuzul.cleantestapp.product.presentation.components.ProductState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    val getProductListUseCase: GetProductListUseCase,
    val searchProductListUseCase: SearchProductListUseCase,
    val addProductUseCase: AddProductUseCase,
    val deleteProductUseCase: DeleteProductUseCase,
    val updateProductUseCase: UpdateProductUseCase
) : ViewModel(){

    private val _state = mutableStateOf(ProductState())
    val state: State<ProductState> = _state

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private val _sharedFlow = MutableSharedFlow<NavEvent>(extraBufferCapacity = 1)
    val sharedFlow = _sharedFlow.asSharedFlow()

    private var searchJob: Job? = null

    init {
        getProductList()
    }

    private fun getProductList() {
        getProductListUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = ProductState(listProduct = result.data ?: emptyList())
                }

                is Resource.Error -> {
                    _state.value =
                        ProductState(error = result.message ?: "An unexpected error ocured")
                    _eventFlow.emit(
                        UIEvent.ShowSnackBar(
                            result.message ?: "Unknown Error"
                        )
                    )
                }

                is Resource.Loading -> {
                    _state.value = ProductState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun addProduct(sku: String, name: String, qty: Int, price: Int, unit: String){
        addProductUseCase(sku, name, qty, price, unit, 1).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = ProductState(listProduct = emptyList())
                    _eventFlow.emit(
                        UIEvent.ShowToast(
                            result.message ?: "Success"
                        )
                    )
                    _sharedFlow.emit(
                        NavEvent.Navigate(Screen.DashboardProductScreen.route)
                    )
                }

                is Resource.Error -> {
                    _state.value =
                        ProductState(error = result.message ?: "An unexpected error ocured")
                    _eventFlow.emit(
                        UIEvent.ShowToast(
                            result.message ?: "Unknown Error"
                        )
                    )
                }

                is Resource.Loading -> {
                    _state.value = ProductState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun updateProduct(sku: String, name: String, qty: Int, price: Int, unit: String){
        updateProductUseCase(sku, name, qty, price, unit, 1).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _eventFlow.emit(
                        UIEvent.ShowToast(
                            result.message ?: "Success"
                        )
                    )
                    _sharedFlow.emit(
                        NavEvent.Navigate(Screen.DashboardProductScreen.route)
                    )
                }

                is Resource.Error -> {
                    _state.value =
                        ProductState(error = result.message ?: "An unexpected error ocured")
                    _eventFlow.emit(
                        UIEvent.ShowToast(
                            result.message ?: "Unknown Error"
                        )
                    )
                }

                is Resource.Loading -> {
                    _state.value = ProductState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun deleteProduct(sku: String){
        deleteProductUseCase(sku).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = ProductState(listProduct = result.data ?: emptyList())
                    _eventFlow.emit(
                        UIEvent.ShowToast(
                            result.message ?: "Success"
                        )
                    )
                }

                is Resource.Error -> {
                    _state.value =
                        ProductState(error = result.message ?: "An unexpected error ocured")
                    _eventFlow.emit(
                        UIEvent.ShowToast(
                            result.message ?: "Unknown Error"
                        )
                    )
                }

                is Resource.Loading -> {
                    _state.value = ProductState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onSearch(query: String) {
        _searchQuery.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(2000L)
            if (query == ""){
                getProductList()
            } else {
                searchProductListUseCase(query)
                    .onEach { result ->
                        when (result) {
                            is Resource.Loading -> {
                                _state.value = ProductState(isLoading = true)
                            }
                            is Resource.Error -> {
                                _state.value = state.value.copy(
                                    listProduct = result.data ?: emptyList(),
                                    isLoading = false
                                )
                                _eventFlow.emit(
                                    UIEvent.ShowToast(
                                        result.message ?: "Unknown Error"
                                    )
                                )
                            }
                            is Resource.Success -> {
                                _state.value = state.value.copy(
                                    listProduct = result.data ?: emptyList(),
                                    isLoading = false
                                )
                            }
                        }
                    }.launchIn(this)
            }
        }
    }

    sealed class UIEvent {
        data class ShowSnackBar(val message: String): UIEvent()
        data class ShowToast(val message: String): UIEvent()
    }

    sealed class NavEvent {
        data class Navigate(val route: String): NavEvent()
    }
}