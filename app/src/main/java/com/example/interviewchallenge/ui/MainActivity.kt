package com.example.interviewchallenge.ui

import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.carto.graphics.Color
import com.carto.styles.AnimationStyle
import com.carto.styles.LineStyle
import com.carto.styles.LineStyleBuilder
import com.example.interviewchallenge.R
import com.example.interviewchallenge.data.remote.usecase.DirectionUseCase
import com.example.interviewchallenge.data.remote.usecase.MatrixUseCase
import com.example.interviewchallenge.util.ExtensionFunctions.latLngToString
import com.example.interviewchallenge.util.ExtensionFunctions.splitArrayListOfMatrixLocations
import com.example.interviewchallenge.util.MapUtils.createMarker
import com.example.interviewchallenge.util.MapUtils.getLineStyle
import com.example.interviewchallenge.util.MapUtils.initMap
import com.example.interviewchallenge.util.MapUtils.mapSetPosition
import dagger.hilt.android.AndroidEntryPoint
import org.neshan.common.model.LatLng
import org.neshan.common.utils.PolylineEncoding
import org.neshan.mapsdk.MapView
import org.neshan.mapsdk.model.Marker
import org.neshan.mapsdk.model.Polyline
import org.neshan.servicessdk.direction.NeshanDirection
import org.neshan.servicessdk.direction.model.NeshanDirectionResult
import org.neshan.servicessdk.direction.model.Route
import org.neshan.servicessdk.distancematrix.NeshanDistanceMatrix
import org.neshan.servicessdk.distancematrix.model.NeshanDistanceMatrixResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private var originRemoved = false

    private lateinit var mapView: MapView

    // define two toggle button and connecting together for two type of routing
    private lateinit var overviewToggleButton: ToggleButton
    private lateinit var stepByStepToggleButton: ToggleButton

    // we save decoded Response of routing encoded string because we don't want request every time we clicked toggle buttons
    private var routeOverviewPolylinePoints: ArrayList<LatLng>? = null
    private var decodedStepByStepPath: ArrayList<LatLng>? = null

    // value for difference mapSetZoom
    private var overview = false

    private val markers: ArrayList<Marker> = ArrayList()

    private var onMapPolyline: Polyline? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onStart() {
        super.onStart()
        initViews()
        // Initializing mapView element
        initMap(mapView)

        setupMarker()


    }

    private fun setupMarker() {


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
                        overviewToggleButton.isChecked = true;
                        neshanMatrixApi()
                    };
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
    }


    private fun initViews() {
        mapView = findViewById(R.id.map)

        val changeChecker =
            CompoundButton.OnCheckedChangeListener { toggleButton, isChecked -> // if any toggle button checked:
                if (isChecked) {
                    // if overview toggle button checked other toggle button is uncheck
                    if (toggleButton === overviewToggleButton) {
                        stepByStepToggleButton.isChecked = false
                        overview = true
                    }
                    if (toggleButton === stepByStepToggleButton) {
                        overviewToggleButton.isChecked = false
                        overview = false
                    }
                }
                if (!isChecked && onMapPolyline != null) {
                    mapView.removePolyline(onMapPolyline)
                }
            }

        // each toggle button has a checkChangeListener for uncheck other toggle button
        overviewToggleButton = findViewById(R.id.overviewToggleButton);
        overviewToggleButton.setOnCheckedChangeListener(changeChecker);

        stepByStepToggleButton = findViewById(R.id.stepByStepToggleButton);
        stepByStepToggleButton.setOnCheckedChangeListener(changeChecker);
    }


    private fun neshanMatrixApi() {
        val distances = arrayListOf<Int>()
        val origins = arrayListOf<LatLng>(markers[0].latLng)
        val destinations = arrayListOf<LatLng>()
        for (i in 1..5) {
            destinations.add(markers[i].latLng)
        }

        viewModel.getMatrixParams(
            MatrixUseCase.MatrixParams("service.VNlPhrWb3wYRzEYmstQh3GrAXyhyaN55AqUSRR3V",
            splitArrayListOfMatrixLocations(origins), splitArrayListOfMatrixLocations(destinations)))

        viewModel.matrix.observe(this, Observer { response ->
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

        viewModel.getDirectionParams( DirectionUseCase.DirectionParams("service.VNlPhrWb3wYRzEYmstQh3GrAXyhyaN55AqUSRR3V",
            latLngToString(origin),
            latLngToString(destination)))

        viewModel.direction.observe(this, Observer {
        if (it != null && it
                            .routes != null && it.routes.isNotEmpty()
                    ) {
            val route: Route = it.routes[0]
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
            mapSetPosition(overview, mapView, markers)
            }
        })

    }

    // In this method we create a LineStyleCreator, set its features and call buildStyle() method
    // on it and return the LineStyle object (the same routine as crating a marker style)

    fun findRoute(view: View) {
        when {
            markers.size < 2 -> {
                Toast.makeText(this, "برای مسیریابی باید دو نقطه انتخاب شود", Toast.LENGTH_SHORT)
                    .show()
                overviewToggleButton.isChecked = false
                stepByStepToggleButton.isChecked = false
            }
            overviewToggleButton.isChecked -> {
                try {
                    mapView.removePolyline(onMapPolyline)
                    onMapPolyline = Polyline(routeOverviewPolylinePoints, getLineStyle())
                    //draw polyline between route points
                    mapView.addPolyline(onMapPolyline)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            stepByStepToggleButton.isChecked -> {
                try {
                    mapView.removePolyline(onMapPolyline)
                    onMapPolyline = Polyline(decodedStepByStepPath, getLineStyle())
                    //draw polyline between route points
                    mapView.addPolyline(onMapPolyline)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }


}