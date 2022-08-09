package com.example.interviewchallenge.repo

import com.example.interviewchallenge.data.NeshanMapApi
import javax.inject.Inject

class MapRepository @Inject constructor(private val mapApi: NeshanMapApi) {

    suspend fun getDirections(
        apiKey: String, origin: String, destination: String, waypoints: String,
        avoidTerrificZone: Boolean, avoidOddEvenZone: Boolean, alternative: Boolean
    ) = mapApi.getNeshanDirection(
        apiKey,
        origin,
        destination,
        waypoints,
        avoidTerrificZone,
        avoidOddEvenZone,
        alternative
    )

    suspend fun getDirections(
        apiKey: String, origin: String, destination: String,
        avoidTerrificZone: Boolean, avoidOddEvenZone: Boolean, alternative: Boolean
    ) = mapApi.getNeshanDirection(
        apiKey,
        origin,
        destination,
        avoidTerrificZone,
        avoidOddEvenZone,
        alternative
    )

    suspend fun getMatrix(
        mapApiKey: String, origin: String, destination: String
    ) = mapApi.getNeshanDistanceMatrix(mapApiKey, origin, destination)

}