package com.example.interviewchallenge.data.remote.usecase

import com.example.interviewchallenge.data.remote.model.directionModel.DirectionModel
import com.example.interviewchallenge.repo.MapRepository
import com.example.interviewchallenge.util.UseCase
import org.neshan.servicessdk.direction.model.NeshanDirectionResult
import javax.inject.Inject

class DirectionUseCase @Inject constructor(
    private val mapRepository: MapRepository
) : UseCase<NeshanDirectionResult, DirectionUseCase.DirectionParams>() {
    override suspend fun executeOnBackground(params: DirectionParams?): NeshanDirectionResult {
        return mapRepository.getDirections(
            params?.apiKey ?: "",
            params?.origin ?: "",
            params?.destination ?: "",
            params?.avoidTerrificZone ?: false,
            params?.avoidOddEvenZone ?: false,
            params?.alternative ?: false
        )
    }

    class DirectionParams(
        val apiKey: String = "",
        val origin: String = "",
        val destination: String = "",
        val avoidTerrificZone: Boolean = false,
        val avoidOddEvenZone: Boolean = false,
        val alternative: Boolean = false
    )
}