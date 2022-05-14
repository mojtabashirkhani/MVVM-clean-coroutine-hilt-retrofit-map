package com.example.interviewchallenge.repo

import com.example.interviewchallenge.data.remote.datasource.MapRemoteDataSource
import javax.inject.Inject

class MapRepository @Inject constructor(private val mapRemoteDataSource: MapRemoteDataSource) {
}