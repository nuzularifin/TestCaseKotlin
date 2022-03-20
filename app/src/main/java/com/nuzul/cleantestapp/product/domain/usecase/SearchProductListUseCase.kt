package com.nuzul.cleantestapp.product.domain.usecase

import com.google.gson.Gson
import com.nuzul.cleantestapp.core.Resource
import com.nuzul.cleantestapp.product.domain.model.Product
import com.nuzul.cleantestapp.product.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class SearchProductListUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {

    operator fun invoke(query: String): Flow<Resource<List<Product>>> = flow {
        try {
            emit(Resource.Loading())
            val data = productRepository.searchProductList(query = query)
            val list: ArrayList<Product> = ArrayList();
            val product: Product
            if (data["sku"] == null){
                throw IOException(data["message"].toString())
            } else {
                product = Product(
                    data["id"].toString().toDouble().toInt(),
                    data["sku"].toString(),
                    data["product_name"].toString(),
                    data["qty"].toString().toDouble().toInt(),
                    data["price"].toString().toDouble().toInt(),
                    data["unit"].toString(),
                    data["status"].toString().toDouble().toInt()
                )
                list.add(product)
            }
            emit(Resource.Success(list))
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