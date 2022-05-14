package com.example.interviewchallenge.data.remote.model.directionModel

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Routes (

    @Json(name = "legs"              ) var legs             : ArrayList<Legs>   = arrayListOf(),
    @Json(name = "overview_polyline" ) var overviewPolyline : OverviewPolyline? = OverviewPolyline()

) : Parcelable