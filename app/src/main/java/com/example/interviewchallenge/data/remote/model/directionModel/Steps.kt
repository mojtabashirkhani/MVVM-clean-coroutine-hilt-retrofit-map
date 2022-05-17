package com.example.interviewchallenge.data.remote.model.directionModel

import com.example.interviewchallenge.data.remote.model.directionModel.Distance
import com.example.interviewchallenge.data.remote.model.directionModel.Duration
import com.google.gson.annotations.SerializedName


data class Steps (

    @SerializedName("name"           ) var name          : String?           = null,
    @SerializedName("instruction"    ) var instruction   : String?           = null,
    @SerializedName("distance"       ) var distance      : Distance?         = Distance(),
    @SerializedName("duration"       ) var duration      : Duration?         = Duration(),
    @SerializedName("polyline"       ) var polyline      : String?           = null,
    @SerializedName("maneuver"       ) var maneuver      : String?           = null,
    @SerializedName("start_location" ) var startLocation : ArrayList<Double> = arrayListOf()

)