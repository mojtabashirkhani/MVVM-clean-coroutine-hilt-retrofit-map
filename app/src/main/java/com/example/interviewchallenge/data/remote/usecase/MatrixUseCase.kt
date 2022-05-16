package com.example.interviewchallenge.data.remote.usecase

import com.example.interviewchallenge.data.remote.model.matrixModel.MatrixModel
import com.example.interviewchallenge.repo.MapRepository
import com.example.interviewchallenge.util.CloudErrorMapper
import com.example.interviewchallenge.util.UseCase
import javax.inject.Inject

class MatrixUseCase @Inject constructor(
    errorUtil: CloudErrorMapper,
    private val mapRepository: MapRepository
) : UseCase<MatrixModel, MatrixUseCase.MatrixParams>(errorUtil) {
    override suspend fun executeOnBackground(params: MatrixParams?): MatrixModel {
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