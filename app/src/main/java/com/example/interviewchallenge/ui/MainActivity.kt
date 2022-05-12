package com.example.interviewchallenge.ui

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.carto.styles.AnimationStyle
import com.carto.styles.AnimationStyleBuilder
import com.carto.styles.AnimationType
import com.carto.styles.MarkerStyleBuilder
import com.example.interviewchallenge.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.view.*
import org.neshan.common.model.LatLng
import org.neshan.mapsdk.MapView
import org.neshan.mapsdk.internal.utils.BitmapUtils
import org.neshan.mapsdk.model.Marker


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mapView: MapView
    private lateinit var animSt: AnimationStyle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mapView = findViewById(R.id.map)

    }

    override fun onStart() {
        super.onStart()
        setupMarker()
    }

    private fun setupMarker() {
        mapView.setOnMapLongClickListener { latLng -> mapView.addMarker(createMarker(latLng)) }
        // when on marker clicked, change marker style to blue
        // when on marker clicked, change marker style to blue
        mapView.setOnMarkerClickListener { marker1 -> mapView.removeMarker(marker1) }
    }

    private fun changeMarkerToBlue(marker1: Marker?) {
        TODO("Not yet implemented")
    }

    private fun createMarker(latLng: LatLng?): Marker {
        val animStBl = AnimationStyleBuilder()
        animStBl.fadeAnimationType = AnimationType.ANIMATION_TYPE_SMOOTHSTEP
        animStBl.sizeAnimationType = AnimationType.ANIMATION_TYPE_SPRING
        animStBl.phaseInDuration = 0.5f
        animStBl.phaseOutDuration = 0.5f
        animSt = animStBl.buildStyle()

        // Creating marker style. We should use an object of type MarkerStyleCreator, set all features on it
        // and then call buildStyle method on it. This method returns an object of type MarkerStyle

        // Creating marker style. We should use an object of type MarkerStyleCreator, set all features on it
        // and then call buildStyle method on it. This method returns an object of type MarkerStyle
        val markStCr = MarkerStyleBuilder()
        markStCr.size = 30f
        markStCr.bitmap = BitmapUtils.createBitmapFromAndroidBitmap(
            BitmapFactory.decodeResource(
                resources, R.drawable.ic_marker
            )
        )
        // AnimationStyle object - that was created before - is used here
        // AnimationStyle object - that was created before - is used here
        markStCr.animationStyle = animSt
        val markSt = markStCr.buildStyle()

        // Creating marker

        // Creating marker
        return Marker(latLng, markSt)
    }
}