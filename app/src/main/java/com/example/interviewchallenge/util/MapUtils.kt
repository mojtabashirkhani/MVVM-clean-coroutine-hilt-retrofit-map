package com.example.interviewchallenge.util

import android.content.Context
import android.graphics.BitmapFactory
import com.carto.graphics.Color
import com.carto.styles.*
import com.example.interviewchallenge.R
import org.neshan.common.model.LatLng
import org.neshan.mapsdk.MapView
import org.neshan.mapsdk.internal.utils.BitmapUtils
import org.neshan.mapsdk.model.Marker

object MapUtils {

     fun createMarker(latLng: LatLng?, originRemovedFlag: Boolean, context: Context, mapView: MapView): Marker {


        val animStBl = AnimationStyleBuilder()
        animStBl.fadeAnimationType = AnimationType.ANIMATION_TYPE_SMOOTHSTEP
        animStBl.sizeAnimationType = AnimationType.ANIMATION_TYPE_SPRING
        animStBl.phaseInDuration = 0.5f
        animStBl.phaseOutDuration = 0.5f
        val animSt: AnimationStyle= animStBl.buildStyle()

        // Creating marker style. We should use an object of type MarkerStyleCreator, set all features on it
        // and then call buildStyle method on it. This method returns an object of type MarkerStyle

        // Creating marker style. We should use an object of type MarkerStyleCreator, set all features on it
        // and then call buildStyle method on it. This method returns an object of type MarkerStyle
        val markStCr = MarkerStyleBuilder()
        markStCr.size = 30f

        if (originRemovedFlag) {
            markStCr.bitmap = BitmapUtils.createBitmapFromAndroidBitmap(
                BitmapFactory.decodeResource(
                    context.resources, R.drawable.ic_origin
                )
            )
        } else if (!originRemovedFlag) {
            markStCr.bitmap = BitmapUtils.createBitmapFromAndroidBitmap(
                BitmapFactory.decodeResource(
                    context.resources, R.drawable.ic_marker
                )
            )
        }


        // AnimationStyle object - that was created before - is used here
        // AnimationStyle object - that was created before - is used here
        markStCr.animationStyle = animSt
        val markSt = markStCr.buildStyle()

        // Creating marker
        // Creating marker
        val marker = Marker(latLng, markSt)

        // Adding marker to markerLayer, or showing marker on map!

        // Adding marker to markerLayer, or showing marker on map!
        mapView.addMarker(marker)
        return marker
    }

     fun mapSetPosition(overview: Boolean, mapView: MapView, markers: ArrayList<Marker>) {
        val centerFirstMarkerX = markers[0].latLng.latitude
        val centerFirstMarkerY = markers[0].latLng.longitude
        if (overview) {
            val centerFocalPositionX = (centerFirstMarkerX + markers[1].latLng.latitude) / 2
            val centerFocalPositionY = (centerFirstMarkerY + markers[1].latLng.longitude) / 2
            mapView.moveCamera(LatLng(centerFocalPositionX, centerFocalPositionY), 0.5f)
            mapView.setZoom(14f, 0.5f)
        } else {
            mapView.moveCamera(LatLng(centerFirstMarkerX, centerFirstMarkerY), 0.5f)
            mapView.setZoom(18f, 0.5f)
        }
    }

     fun initMap(mapView: MapView) {

        // Setting map focal position to a fixed position and setting camera zoom
        mapView.moveCamera(LatLng(35.767234, 51.330743), 0f)
        mapView.setZoom(14.0F, 0.0F)
    }

     fun getLineStyle(): LineStyle? {
        val lineStCr = LineStyleBuilder()
        lineStCr.color = Color(2.toShort(), 119.toShort(), 189.toShort(), 190.toShort())
        lineStCr.width = 10f
        lineStCr.stretchFactor = 0f
        return lineStCr.buildStyle()
    }


}