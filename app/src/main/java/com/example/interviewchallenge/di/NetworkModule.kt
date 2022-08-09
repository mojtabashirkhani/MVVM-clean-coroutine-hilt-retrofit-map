package com.example.interviewchallenge.di

import android.os.Environment
import com.example.interviewchallenge.core.Constants
import com.example.interviewchallenge.data.DefaultRequestInterceptor
import com.example.interviewchallenge.data.NeshanMapApi
import com.example.interviewchallenge.data.ReverseMapIrApi
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    @Named("cached")
    fun provideOkHttpClient(): OkHttpClient {
        val cache = Cache(Environment.getDownloadCacheDirectory(), 10 * 1024 * 1024)
        return OkHttpClient.Builder()
//            .addNetworkInterceptor(StethoInterceptor())
            .addInterceptor(DefaultRequestInterceptor())
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .cache(cache)
            .build()
    }

    @Singleton
    @Provides
    @Named("non_cached")
    fun provideNonCachedOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
//            .addNetworkInterceptor(StethoInterceptor())
            .addInterceptor(DefaultRequestInterceptor())
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .build()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }





    @Singleton
    @Provides
    fun provideRetrofit(moshi: Moshi, @Named("cached") client: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
    }

    @Provides
    @Singleton
    fun provideMapService(retrofit: Retrofit.Builder): NeshanMapApi {
        return retrofit.baseUrl(Constants.NetworkService.BASE_URL_MAP_NESHAN)
            .build()
            .create(NeshanMapApi::class.java)
    }

    @Provides
    @Singleton
    fun provideReverseMapIr(retrofit: Retrofit.Builder): ReverseMapIrApi {
        return retrofit.baseUrl(Constants.NetworkService.BASE_URL_REVERSE_MAP_IR)
            .build()
            .create(ReverseMapIrApi::class.java)
    }

}