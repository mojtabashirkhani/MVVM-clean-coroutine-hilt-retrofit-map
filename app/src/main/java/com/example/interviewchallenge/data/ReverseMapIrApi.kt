package com.example.interviewchallenge.data

import com.example.interviewchallenge.data.remote.model.reverse.ReverseMapIr
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ReverseMapIrApi {

    @GET("/reverse/no")
    suspend fun reverseMapIr(@Header("x-api-key") mapApiKey: String,
                             @Query("lat") lon: String,
                             @Query("lon") lat: String): ReverseMapIr
}