package com.example.interviewchallenge.data.remote.model.directionModel

import com.example.interviewchallenge.data.remote.model.directionModel.Legs
import com.example.interviewchallenge.data.remote.model.directionModel.OverviewPolyline
import com.google.gson.annotations.SerializedName


data class Routes (

    @SerializedName("legs"              ) var legs             : ArrayList<Legs>   = arrayListOf(),
    @SerializedName("overview_polyline" ) var overviewPolyline : OverviewPolyline? = OverviewPolyline()

)