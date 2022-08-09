package com.example.interviewchallenge.data.remote.usecase

import com.example.interviewchallenge.data.remote.model.reverse.ReverseMapIr
import com.example.interviewchallenge.data.remote.model.reverse.ReverseNeshan
import com.example.interviewchallenge.repo.ReverseMapIrRepository
import com.example.interviewchallenge.repo.ReverseNeshanRepository
import com.example.interviewchallenge.util.UseCase
import javax.inject.Inject

class ReverseNeshanUseCase @Inject constructor(private val reverseNeshanRepository: ReverseNeshanRepository): UseCase<ReverseNeshan, ReverseNeshanUseCase.ReverseParams>() {

    override suspend fun executeOnBackground(params: ReverseParams?): ReverseNeshan {
        return reverseNeshanRepository.getNeshanReverse(
            params?.mapApiKey ?: "",
            params?.lat ?: "",
            params?.lng ?: ""
        )
    }

    class ReverseParams(
        val mapApiKey: String = "",
        val lat: String = "",
        val lng: String = ""
    )

}