package com.example.interviewchallenge.data.remote.usecase

import com.example.interviewchallenge.data.remote.model.matrixModel.MatrixModel
import com.example.interviewchallenge.repo.MapRepository
import com.example.interviewchallenge.util.UseCase
import org.neshan.servicessdk.distancematrix.model.NeshanDistanceMatrixResult
import javax.inject.Inject

class MatrixUseCase @Inject constructor(
    private val mapRepository: MapRepository
) : UseCase<NeshanDistanceMatrixResult, MatrixUseCase.MatrixParams>() {
    override suspend fun executeOnBackground(params: MatrixParams?): NeshanDistanceMatrixResult {
        return mapRepository.getMatrix(
            params?.mapApiKey ?: "",
            params?.origin ?: "",
            params?.destination ?: ""
        )
    }

    class MatrixParams(
        val mapApiKey: String = "",
        val origin: String = "",
        val destination: String = ""
    )
}