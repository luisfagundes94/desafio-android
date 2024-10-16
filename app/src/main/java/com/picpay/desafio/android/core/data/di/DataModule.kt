package com.picpay.desafio.android.core.data.di

import com.picpay.desafio.android.core.data.service.PicPayService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val PIC_PAY_BASE_API_URL = "https://609a908e0f5a13001721b74e.mockapi.io/picpay/api/"

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun providePicPayService() = Retrofit.Builder()
        .baseUrl(PIC_PAY_BASE_API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PicPayService::class.java)

    @Provides
    fun provideOkHttpClient() = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor())
        .build()
}