package com.nuzul.cleantestapp.core.di

import android.app.Application
import android.content.Context
import com.nuzul.cleantestapp.authentication.data.remote.AuthAPI
import com.nuzul.cleantestapp.authentication.data.repository.AuthRepositoryImpl
import com.nuzul.cleantestapp.authentication.domain.repository.AuthRepository
import com.nuzul.cleantestapp.core.Constants
import com.nuzul.cleantestapp.core.utils.SharedPrefs
import com.nuzul.cleantestapp.product.data.remote.ProductAPI
import com.nuzul.cleantestapp.product.data.repository.ProductRepositoryImpl
import com.nuzul.cleantestapp.product.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext

    @Provides
    @Singleton // Make sure for just 1 single instance
    fun provideAuthApi() : AuthAPI{
        val httpClient = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            httpClient.addInterceptor(logging)

        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
            .create(AuthAPI::class.java)
    }

    @Provides
    @Singleton // Make sure for just 1 single instance
    fun provideProductApi() : ProductAPI{
        val httpClient = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        httpClient.addInterceptor(logging)

        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
            .create(ProductAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(api: AuthAPI): AuthRepository {
        return AuthRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideProductRepository(api: ProductAPI, context: Context): ProductRepository {
        return ProductRepositoryImpl(api, context)
    }

    @Provides
    @Singleton
    fun provideSharedPrefs(@ApplicationContext context: Context) : SharedPrefs {
        return SharedPrefs(context = context)
    }
}