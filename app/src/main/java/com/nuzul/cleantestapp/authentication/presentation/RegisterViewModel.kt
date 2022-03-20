package com.nuzul.cleantestapp.authentication.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nuzul.cleantestapp.authentication.domain.usecase.RequestRegisterUsecase
import com.nuzul.cleantestapp.authentication.presentation.components.LoginState
import com.nuzul.cleantestapp.authentication.presentation.components.RegisterState
import com.nuzul.cleantestapp.core.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    val requestRegisterUseCase: RequestRegisterUsecase
) : ViewModel() {

    private val _state = mutableStateOf(RegisterState())
    val state: State<RegisterState> = _state

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun requestRegister(email: String, password: String) {
        requestRegisterUseCase(email = email, password = password).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = RegisterState(user = result.data)
                    _eventFlow.emit(
                        UIEvent.ShowSnackBar(
                            result.message ?: "Success"
                        )
                    )
                }

                is Resource.Error -> {
                    _state.value =
                        RegisterState(error = result.message ?: "An unexpected error ocured")
                    _eventFlow.emit(
                        UIEvent.ShowSnackBar(
                            result.message ?: "Unknown Error"
                        )
                    )
                }

                is Resource.Loading -> {
                    _state.value = RegisterState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    sealed class UIEvent {
        data class ShowSnackBar(val message: String): UIEvent()
    }
}