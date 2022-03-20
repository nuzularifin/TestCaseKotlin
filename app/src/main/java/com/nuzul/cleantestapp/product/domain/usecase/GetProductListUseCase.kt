package com.nuzul.cleantestapp.product.domain.usecase

import android.util.Log
import com.nuzul.cleantestapp.authentication.domain.model.User
import com.nuzul.cleantestapp.core.Resource
import com.nuzul.cleantestapp.product.domain.model.Product
import com.nuzul.cleantestapp.product.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetProductListUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    operator fun invoke(): Flow<Resource<List<Product>>> = flow {
        try {
            emit(Resource.Loading())
            val data = productRepository.getProductList()
            Log.d("PRODUCT LIST", "Product List Invoke: ${data.size}")
            emit(Resource.Success(data))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An expected error occurred"))
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    e.localizedMessage ?: "Couldn't reach server, Check your internet connection"
                )
            )
        }
    }
}