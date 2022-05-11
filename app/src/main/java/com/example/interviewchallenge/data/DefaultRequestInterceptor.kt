package com.example.interviewchallenge.data

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class DefaultRequestInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url
            .newBuilder()
//            .addQueryParameter()
            .build()
        val request = chain.request().newBuilder().url(url).build()
        return chain.proceed(request)
    }
}