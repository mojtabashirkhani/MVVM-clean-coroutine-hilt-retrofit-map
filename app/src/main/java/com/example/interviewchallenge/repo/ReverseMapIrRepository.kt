package com.example.interviewchallenge.repo

import com.example.interviewchallenge.data.ReverseMapIrApi
import javax.inject.Inject

class ReverseMapIrRepository @Inject constructor(private val reverseApi: ReverseMapIrApi) {

    suspend fun getReverseMapIr(
        mapApiKey: String, lat: String, lon: String
    ) = reverseApi.reverseMapIr(mapApiKey, lat, lon)

}