package com.example.interviewchallenge.data.remote.model.reverse

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Geom(
    @Json(name = "coordinates") val coordinates: List<String>,
    @Json(name = "type") val type: String
): Parcelable