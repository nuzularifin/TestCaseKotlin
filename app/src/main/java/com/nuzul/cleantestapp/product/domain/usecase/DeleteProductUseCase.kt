package com.nuzul.cleantestapp.product.domain.usecase

import com.nuzul.cleantestapp.core.Resource
import com.nuzul.cleantestapp.product.domain.model.Product
import com.nuzul.cleantestapp.product.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DeleteProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {

    operator fun invoke(sku: String): Flow<Resource<List<Product>>> = flow {
        try {
            emit(Resource.Loading())
            val data = productRepository.deleteProduct(sku)
            val id = data["id"] ?: 0
            if (data["sku"] == null){
                throw IOException(data["message"].toString())
            } else {
                val data = productRepository.getProductList()
                emit(Resource.Success(data))
            }
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