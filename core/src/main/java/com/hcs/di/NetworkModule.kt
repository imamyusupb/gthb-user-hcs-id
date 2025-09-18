package com.hcs.di

import android.content.Context
import android.util.Log
import com.chuckerteam.chucker.api.BodyDecoder
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.hcs.core.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import okio.ByteString
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }



    @Provides
    @Singleton
    fun provideAuthInterceptor(): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .addHeader("Authorization", "token ${BuildConfig.GITHUB_TOKEN}")
                .addHeader("Accept", "application/vnd.github.v3+json")
                .build()
            chain.proceed(request)
        }
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {

        // Log untuk debug
        Log.d("NetworkModule", "Creating OkHttpClient in ${if (BuildConfig.DEBUG) "DEBUG" else "RELEASE"} mode")

        val builder = OkHttpClient().newBuilder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)

        // Auth interceptor
        builder.addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .addHeader("Authorization", "token ${BuildConfig.GITHUB_TOKEN}")
                .addHeader("Accept", "application/vnd.github.v3+json")
                .build()
            chain.proceed(request)
        }

        if (BuildConfig.DEBUG) {
            val chuckerInterceptor = ChuckerInterceptor.Builder(context).apply {
                maxContentLength(Long.MAX_VALUE)
                addBodyDecoder(bodyDecoder(context))
            }.build()
            builder.apply {
                addInterceptor(httpLoggingInterceptor)
                addInterceptor(chuckerInterceptor)
            }
            Log.d("NetworkModule", "Chucker interceptor added")
        }

        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }
}

private fun bodyDecoder(context: Context) = object : BodyDecoder {
    override fun decodeRequest(request: Request, body: ByteString): String {
        val res = body.utf8()
        return hideLongString(context, res)
    }

    override fun decodeResponse(response: Response, body: ByteString): String {
        val res = body.utf8()
        return hideLongString(context, res)
    }

    private fun hideLongString(context: Context, str: String): String {
        return try {
            val obj = JSONObject(str.ifEmpty { "{}" })
            val newJSON = JSONObject()
            obj.keys().forEach { key ->
                val v = obj[key]
                var newValue: Any? = null
                (v as? String)?.also { content ->
                    if (content.length <= 10_000) {
                        newValue = content
                    }
                }
                newJSON.put(key, newValue ?: v)
            }
            return newJSON.toString()
        } catch (_: Exception) {
            str
        }
    }
}
