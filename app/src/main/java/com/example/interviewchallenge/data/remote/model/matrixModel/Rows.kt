package com.example.interviewchallenge.data.remote.model.matrixModel

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Rows (

  @Json(name = "elements" ) var elements : ArrayList<Elements> = arrayListOf()

) : Parcelable