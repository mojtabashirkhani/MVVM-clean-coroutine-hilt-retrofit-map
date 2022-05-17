package com.example.interviewchallenge.data.remote.model.directionModel

import com.google.gson.annotations.SerializedName


data class Distance (

  @SerializedName("value" ) var value : Int?    = null,
  @SerializedName("text"  ) var text  : String? = null

)