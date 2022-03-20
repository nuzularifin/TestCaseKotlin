package com.nuzul.cleantestapp.product.domain.usecase

import android.util.Log
import com.nuzul.cleantestapp.core.Resource
import com.nuzul.cleantestapp.product.domain.model.Product
import com.nuzul.cleantestapp.product.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AddProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    operator fun invoke(sku: String, name: String, qty: Int, price: Int, unit: String, status: Int): Flow<Resource<Product>> = flow {
        try {
            emit(Resource.Loading())
            val data = productRepository.addProduct(sku, name, qty, price, unit, status)
            val id = data["id"] ?: 0
            if (data["sku"] == null){
                throw IOException(data["message"].toString())
            } else {
                emit(
                    Resource.Success(
                        Product(
                            id.toString().toDouble().toInt(),
                            sku.toString(),
                            name.toString(),
                            Integer.parseInt(qty.toString()),
                            Integer.parseInt(price.toString()),
                            unit.toString(),
                            Integer.parseInt(status.toString())
                        )
                    )
                )
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