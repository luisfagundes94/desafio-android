package com.picpay.desafio.android.core.data.di

import android.content.Context
import androidx.room.Room
import com.picpay.desafio.android.core.data.database.PicPayDatabase
import com.picpay.desafio.android.core.data.service.PicPayService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val PIC_PAY_BASE_API_URL = "https://609a908e0f5a13001721b74e.mockapi.io/picpay/api/"
private const val PIC_PAY_DATABASE_NAME = "picpay_database"

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        PicPayDatabase::class.java,
        PIC_PAY_DATABASE_NAME
    ).build()

    @Provides
    fun provideUserDao(database: PicPayDatabase) = database.userDao()

    @Provides
    fun provideOkHttpClient() = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        )
        .build()

    @Provides
    fun providePicPayService(okHttpClient: OkHttpClient) = Retrofit.Builder()
        .baseUrl(PIC_PAY_BASE_API_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PicPayService::class.java)
}
