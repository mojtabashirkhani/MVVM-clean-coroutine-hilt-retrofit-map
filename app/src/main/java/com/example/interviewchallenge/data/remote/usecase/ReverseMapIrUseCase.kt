package com.example.interviewchallenge.data.remote.usecase

import com.example.interviewchallenge.data.remote.model.reverse.ReverseMapIr
import com.example.interviewchallenge.repo.ReverseMapIrRepository
import com.example.interviewchallenge.util.UseCase
import javax.inject.Inject

class ReverseMapIrUseCase @Inject constructor(private val reverseRepository: ReverseMapIrRepository): UseCase<ReverseMapIr, ReverseMapIrUseCase.ReverseParams>() {

    override suspend fun executeOnBackground(params: ReverseParams?): ReverseMapIr {
        return reverseRepository.getReverseMapIr(
            params?.mapApiKey ?: "",
            params?.lat ?: "",
            params?.lon ?: ""
        )
    }

    class ReverseParams(
        val mapApiKey: String = "",
        val lat: String = "",
        val lon: String = ""
    )


}