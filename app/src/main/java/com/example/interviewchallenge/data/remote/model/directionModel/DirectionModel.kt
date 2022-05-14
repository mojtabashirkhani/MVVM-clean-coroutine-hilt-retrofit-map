package com.example.interviewchallenge.data.remote.model.directionModel

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class DirectionModel (

  @Json(name = "routes" ) var routes : ArrayList<Routes> = arrayListOf()

) : Parcelable