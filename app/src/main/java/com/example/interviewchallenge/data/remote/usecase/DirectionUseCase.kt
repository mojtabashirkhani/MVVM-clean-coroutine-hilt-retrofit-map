package com.example.interviewchallenge.data.remote.usecase

import com.example.interviewchallenge.data.remote.model.directionModel.DirectionModel
import com.example.interviewchallenge.repo.MapRepository
import com.example.interviewchallenge.util.CloudErrorMapper
import com.example.interviewchallenge.util.UseCase
import javax.inject.Inject

class DirectionUseCase @Inject constructor(
    errorUtil: CloudErrorMapper,
    private val mapRepository: MapRepository
) : UseCase<DirectionModel, DirectionUseCase.DirectionParams>(errorUtil) {
    override suspend fun executeOnBackground(params: DirectionParams?): DirectionModel {
        return mapRepository.getDirections(
            params?.apiKey ?: "",
            params?.origin ?: "",
            params?.destination ?: "",
            params?.waypoints ?: "",
            params?.avoidTerrificZone ?: false,
            params?.avoidOddEvenZone ?: false,
            params?.alternative ?: false
        )
    }

    class DirectionParams(
        val apiKey: String = "",
        val origin: String = "",
        val destination: String = "",
        val waypoints: String = "",
        val avoidTerrificZone: Boolean = false,
        val avoidOddEvenZone: Boolean = false,
        val alternative: Boolean = false
    )
}