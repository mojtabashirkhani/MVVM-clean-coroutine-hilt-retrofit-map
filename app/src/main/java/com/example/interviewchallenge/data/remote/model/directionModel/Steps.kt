package com.example.interviewchallenge.data.remote.model.directionModel

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Steps (

    @Json(name = "name"           ) var name          : String?           = null,
    @Json(name = "instruction"    ) var instruction   : String?           = null,
    @Json(name = "distance"       ) var distance      : Distance?         = Distance(),
    @Json(name = "duration"       ) var duration      : Duration?         = Duration(),
    @Json(name = "polyline"       ) var polyline      : String?           = null,
    @Json(name = "maneuver"       ) var maneuver      : String?           = null,
    @Json(name = "start_location" ) var startLocation : ArrayList<Double> = arrayListOf()

) : Parcelable