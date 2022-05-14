package com.example.interviewchallenge.data.remote.model.matrixModel

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class MatrixModel (

  @Json(name = "status"                ) var status               : String?           = null,
  @Json(name = "rows"                  ) var rows                 : ArrayList<Rows>   = arrayListOf(),
  @Json(name = "origin_addresses"      ) var originAddresses      : ArrayList<String> = arrayListOf(),
  @Json(name = "destination_addresses" ) var destinationAddresses : ArrayList<String> = arrayListOf()

) : Parcelable