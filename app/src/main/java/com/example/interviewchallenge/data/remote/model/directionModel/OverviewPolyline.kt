package com.example.interviewchallenge.data.remote.model.directionModel

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class OverviewPolyline (

  @Json(name = "points" ) var points : String? = null

) : Parcelable