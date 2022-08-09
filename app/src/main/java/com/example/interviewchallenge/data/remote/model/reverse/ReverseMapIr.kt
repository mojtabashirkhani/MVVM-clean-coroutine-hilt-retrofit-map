package com.example.interviewchallenge.data.remote.model.reverse

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class ReverseMapIr(
    @Json(name = "address") val address: String?,
    @Json(name = "address_compact") val address_compact: String?,
    @Json(name = "city") val city: String?,
    @Json(name = "country") val country: String?,
    @Json(name = "county") val county: String?,
    @Json(name = "district") val district: String?,
    @Json(name = "geom") val geom: Geom?,
    @Json(name = "last") val last: String?,
    @Json(name = "name") val name: String?,
    @Json(name = "neighbourhood") val neighbourhood: String?,
    @Json(name = "plaque") val plaque: String?,
    @Json(name = "poi") val poi: String?,
    @Json(name = "postal_address") val postal_address: String?,
    @Json(name = "postal_code") val postal_code: String?,
    @Json(name = "primary") val primary: String?,
    @Json(name = "province") val province: String?,
    @Json(name = "region") val region: String?,
    @Json(name = "rural_district") val rural_district: String?,
    @Json(name = "village") val village: String?
) : Parcelable

