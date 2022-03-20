package com.nuzul.cleantestapp.authentication.domain.usecase

import android.util.Log
import com.nuzul.cleantestapp.authentication.domain.model.User
import com.nuzul.cleantestapp.authentication.domain.repository.AuthRepository
import com.nuzul.cleantestapp.core.Resource
import com.nuzul.cleantestapp.core.utils.SharedPrefs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RequestRegisterUsecase @Inject constructor(
    private val authRepository: AuthRepository,
    private val sharedPrefs: SharedPrefs
) {

    operator fun invoke(email: String, password: String): Flow<Resource<User>> = flow {
        try {
            emit(Resource.Loading())
            val data = authRepository.requestRegister(email = email, password = password)
            sharedPrefs.saveToken(data["token"].toString());
            var token = sharedPrefs.getToken();
            Log.d("TOKEN", "invoke: $token")
            emit(Resource.Success(User(
                username = data["email"].toString(),
                password = data["password"].toString(),
                token = data["token"].toString()
            )))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage ?: "Couldnt reach server, Check your internet connection"))
        }
    }
}