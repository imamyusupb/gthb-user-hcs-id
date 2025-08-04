package com.hcs.core.remote

import com.hcs.core.utils.DataConfig
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


object Network {
    fun retrofitClient(url: String = DataConfig.BASE_URL): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient())
            .build()
    }

    private fun okHttpClient() : OkHttpClient {
        val certificatePinner = CertificatePinner.Builder()
            .add(
                DataConfig.HOST_NAME,
                DataConfig.CERTIFICATE_PIN_1,
                DataConfig.CERTIFICATE_PIN_2,
                DataConfig.CERTIFICATE_PIN_3,
            )
            .build()

        return OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .addInterceptor(createLoggingInterceptor())
            .pingInterval(30, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES)
            .certificatePinner(certificatePinner)
            .build()
    }

    private fun createLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}