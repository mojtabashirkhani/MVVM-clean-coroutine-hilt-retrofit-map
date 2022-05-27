package com.example.interviewchallenge.util

import org.neshan.common.model.LatLng

object ExtensionFunctions {
    fun latLngToString(LatLng: LatLng): String {
        return LatLng.latitude.toString() + "," + LatLng.longitude
    }

    fun splitArrayListOfMatrixLocations(latLngArrayList: ArrayList<LatLng>): String {

        var location = ""
        val latLngIterator: Iterator<*> = latLngArrayList.iterator()
        while (latLngIterator.hasNext()) {
            val latLng: LatLng = latLngIterator.next() as LatLng
            location =
                location + (if (location.isNotEmpty()) "|" else "") + latLngToString(latLng)
        }

        return location

    }
}