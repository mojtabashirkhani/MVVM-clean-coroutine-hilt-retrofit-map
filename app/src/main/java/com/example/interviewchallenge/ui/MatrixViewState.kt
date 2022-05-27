package com.example.interviewchallenge.ui

import org.neshan.servicessdk.distancematrix.model.NeshanDistanceMatrixResult

data class MatrixViewState(val error: String? = null, val data: NeshanDistanceMatrixResult)