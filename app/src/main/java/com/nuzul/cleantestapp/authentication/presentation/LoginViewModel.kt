package com.nuzul.cleantestapp.authentication.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.nuzul.cleantestapp.authentication.domain.usecase.RequestLoginUsecase
import com.nuzul.cleantestapp.authentication.presentation.components.LoginState
import com.nuzul.cleantestapp.core.Resource
import com.nuzul.cleantestapp.core.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val requestLoginUseCase: RequestLoginUsecase
) : ViewModel() {

    private val _state = mutableStateOf(LoginState())
    val state: State<LoginState> = _state

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _sharedFlow = MutableSharedFlow<NavEvent>(extraBufferCapacity = 1)
    val sharedFlow = _sharedFlow.asSharedFlow()

    fun requestLogin(email: String, password: String) {
        requestLoginUseCase(email = email, password = password).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = LoginState(user = result.data)
                    _eventFlow.emit(
                        UIEvent.ShowSnackBar(
                            result.message ?: "Success"
                        )
                    )
                    _sharedFlow.emit(

                        NavEvent.Navigate(Screen.DashboardProductScreen.route)
                    )
                }

                is Resource.Error -> {
                    _state.value =
                        LoginState(error = result.message ?: "An unexpected error ocured")
                    _eventFlow.emit(
                        UIEvent.ShowSnackBar(
                            result.message ?: "Unknown Error"
                        )
                    )
                }

                is Resource.Loading -> {
                    _state.value = LoginState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    sealed class UIEvent {
        data class ShowSnackBar(val message: String): UIEvent()
    }

    sealed class NavEvent {
        data class Navigate(val route: String): NavEvent()
    }
}

