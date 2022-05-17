package com.example.interviewchallenge.data.remote.model.directionModel

import com.google.gson.annotations.SerializedName


data class DirectionModel (

  @SerializedName("routes" ) var routes : ArrayList<Routes> = arrayListOf()

)