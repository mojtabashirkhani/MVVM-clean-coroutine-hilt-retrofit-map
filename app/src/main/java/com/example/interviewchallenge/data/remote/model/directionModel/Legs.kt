package com.example.interviewchallenge.data.remote.model.directionModel

import com.google.gson.annotations.SerializedName


data class Legs (

    @SerializedName("summary"  ) var summary  : String?          = null,
    @SerializedName("distance" ) var distance : Distance?        = Distance(),
    @SerializedName("duration" ) var duration : Duration?        = Duration(),
    @SerializedName("steps"    ) var steps    : ArrayList<Steps> = arrayListOf()

)