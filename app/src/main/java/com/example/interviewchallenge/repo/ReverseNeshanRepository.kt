package com.example.interviewchallenge.repo

import com.example.interviewchallenge.data.NeshanMapApi
import javax.inject.Inject

class ReverseNeshanRepository @Inject constructor(private val neshanMapApi: NeshanMapApi) {

    suspend fun getNeshanReverse(
        mapApiKey: String, lat: String, lng: String
    ) = neshanMapApi.getNeshanReverseGeocoding(mapApiKey, lat, lng)


}