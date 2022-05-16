package com.example.interviewchallenge.data.remote.model

data class ErrorModel(val message: String?,
                      val code: Int?,
                      @Transient var errorStatus: ErrorStatus
) {
    constructor(errorStatus: ErrorStatus) : this(null, null, errorStatus)
}
