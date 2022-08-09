package com.example.interviewchallenge.ui

import com.example.interviewchallenge.util.EventObserver
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.interviewchallenge.R
import com.example.interviewchallenge.core.Constants
import com.example.interviewchallenge.data.remote.usecase.ReverseMapIrUseCase
import com.example.interviewchallenge.data.remote.usecase.ReverseNeshanUseCase
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

 /*   private var originRemoved = false

    private lateinit var mapView: MapView

    // we save decoded Response of routing encoded string because we don't want request every time we clicked toggle buttons
    private var routeOverviewPolylinePoints: ArrayList<LatLng>? = null
    private var decodedStepByStepPath: ArrayList<LatLng>? = null

    // value for difference mapSetZoom
    private var overview = false

    private val markers: ArrayList<Marker> = ArrayList()

    private var onMapPolyline: Polyline? = null*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.getMapIrReverseParams(ReverseMapIrUseCase.ReverseParams(
            Constants.NetworkService.API_KEY_MAP_IR, "35.7390942460414", "51.33668674793789"
        ))

        viewModel.getNeshanRverseParams(ReverseNeshanUseCase.ReverseParams(
            Constants.NetworkService.API_KEY_MAP_NESHAN, "35.7390942460414", "51.33668674793789"
        ))

    }

    override fun onStart() {
        super.onStart()
        viewModel.reverseMapIr.observe(this, EventObserver {
            Log.d("MainActivity", it.city.toString())
        })

        viewModel.reverseNeshan.observe(this, EventObserver{
            Log.d("MainActivity", it.city.toString())
        })
    }


   /* private fun setupMarker() {


        mapView.setOnMapLongClickListener { latLng ->


            if (markers.size < 6) {
                when {
                    markers.size == 0 -> {
                        originRemoved = true
                        markers.add(0, createMarker(latLng, originRemoved, this, mapView))
                        originRemoved = false
                    }
                    originRemoved -> {
                        markers.add(0, createMarker(latLng, originRemoved, this, mapView))
                        originRemoved = false
                    }
                    else -> {
                        markers.add(createMarker(latLng, originRemoved, this, mapView))
                    }
                }
                if (markers.size == 6) {
                    markers[0].title = "origin"
                    for (i in 1..5) {
                        markers[i].title = "destination"
                    }
                    runOnUiThread {
                        neshanMatrixApi()
                    }
                }
            } else {
//            Toast.makeText(this@MainActivity, "مسیریابی بین دو نقطه انجام میشود!", Toast.LENGTH_SHORT).show()
            }
        }

        // when on marker clicked, change marker style to blue
        // when on marker clicked, change marker style to blue
        mapView.setOnMarkerClickListener { marker ->
            if (marker.title.equals("origin")) {
                originRemoved = true
            }
            mapView.removeMarker(marker)
            markers.remove(marker)
        }
    }*/


 /*   private fun initViews() {
        mapView = findViewById(R.id.map)
    }*/


   /* private fun neshanMatrixApi() {

        if (onMapPolyline != null)
        mapView.removePolyline(onMapPolyline)

        val distances = arrayListOf<Int>()
        val origins = arrayListOf<LatLng>(markers[0].latLng)
        val destinations = arrayListOf<LatLng>()
        for (i in 1..5) {
            destinations.add(markers[i].latLng)
        }

        viewModel.getMatrixParams(
            MatrixUseCase.MatrixParams(API_KEY_MAP,
            splitArrayListOfMatrixLocations(origins), splitArrayListOfMatrixLocations(destinations)))

        viewModel.matrix.observe(this, EventObserver { response ->
            for (i in 0..4) {
                distances.add(response.rows[0].elements[i].duration.value)
            }
            val shortestDistanceIndexValue =
                distances.sortedWith(compareBy { it }).first().let { distances.indexOf(it) }
            val shortestDestinationPath =
                response.destinationAddresses[shortestDistanceIndexValue]
            neshanRoutingApi(
                origins[0],
                LatLng(
                    shortestDestinationPath.split(",")[0].toDouble(),
                    shortestDestinationPath.split(",")[1].toDouble()
                )
            )
        })

    }


    private fun neshanRoutingApi(origin: LatLng, destination: LatLng) {

        viewModel.getDirectionParams( DirectionUseCase.DirectionParams(API_KEY_MAP,
            latLngToString(origin),
            latLngToString(destination)))

        viewModel.direction.observe(this, EventObserver { response ->
        if (!(response.routes == null || response.routes.isEmpty())
                    ) {
            val route: Route = response.routes[0]
            routeOverviewPolylinePoints = ArrayList(
                PolylineEncoding.decode(
                    route.overviewPolyline.encodedPolyline
                )
            )
            decodedStepByStepPath = ArrayList()

            // decoding each segment of steps and putting to an array
            for (step in route.legs[0].directionSteps) {
                decodedStepByStepPath!!.addAll(PolylineEncoding.decode(step.encodedPolyline))
            }
            onMapPolyline = Polyline(routeOverviewPolylinePoints, getLineStyle())
            //draw polyline between route points
            mapView.addPolyline(onMapPolyline)
            // focusing camera on first point of drawn line
//            mapSetPosition(overview, mapView, markers)
            }
        })

    }*/
}