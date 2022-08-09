package com.example.interviewchallenge.data.remote.model.reverse

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class ReverseNeshan(
    @Json(name = "city") val city: String?,
    @Json(name = "county") val county: String?,
    @Json(name = "district") val district: String?,
    @Json(name = "formatted_address") val formatted_address: String?,
    @Json(name = "in_odd_even_zone") val in_odd_even_zone: Boolean,
    @Json(name = "in_traffic_zone") val in_traffic_zone: Boolean,
    @Json(name = "municipality_zone") val municipality_zone: String?,
    @Json(name = "neighbourhood") val neighbourhood: String?,
    @Json(name = "place") val place: String?,
    @Json(name = "route_name") val route_name: String?,
    @Json(name = "route_type") val route_type: String?,
    @Json(name = "state") val state: String?,
    @Json(name = "status") val status: String?,
    @Json(name = "village") val village: String?
): Parcelable