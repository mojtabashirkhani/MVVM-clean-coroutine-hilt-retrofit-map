package com.example.interviewchallenge.data.remote.model.directionModel

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Legs (

    @Json(name = "summary"  ) var summary  : String?          = null,
    @Json(name = "distance" ) var distance : Distance?        = Distance(),
    @Json(name = "duration" ) var duration : Duration?        = Duration(),
    @Json(name = "steps"    ) var steps    : ArrayList<Steps> = arrayListOf()

) : Parcelable