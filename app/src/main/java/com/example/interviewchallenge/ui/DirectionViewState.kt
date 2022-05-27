package com.example.interviewchallenge.ui

import org.neshan.servicessdk.direction.model.NeshanDirectionResult

data class DirectionViewState(val error: String? = null, val data: NeshanDirectionResult)
