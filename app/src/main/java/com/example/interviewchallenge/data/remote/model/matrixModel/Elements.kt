package com.example.interviewchallenge.data.remote.model.matrixModel

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Elements (

  @Json(name = "status"   ) var status   : String?   = null,
  @Json(name = "duration" ) var duration : Duration? = Duration(),
  @Json(name = "distance" ) var distance : Distance? = Distance()

) : Parcelable