package com.example.interviewchallenge.ui

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.viewModelScope
import com.carto.graphics.Color
import com.carto.styles.*
import com.example.interviewchallenge.R
import com.example.interviewchallenge.data.remote.usecase.DirectionUseCase
import dagger.hilt.android.AndroidEntryPoint
import org.neshan.common.model.LatLng
import org.neshan.common.utils.PolylineEncoding
import org.neshan.mapsdk.MapView
import org.neshan.mapsdk.internal.utils.BitmapUtils
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
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private var originRemoved = false

    private lateinit var mapView: MapView
    private lateinit var animSt: AnimationStyle
    private lateinit var marker: Marker

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

        with(viewModel) {

          /*  this.setDirectionParams(
                DirectionUseCase.DirectionParams(
                    "service.VNlPhrWb3wYRzEYmstQh3GrAXyhyaN55AqUSRR3V",
                    "35.767234,51.330743",
                    "36.767234, 52.330743"
                )
            )*/


             this.direction.observe(this@MainActivity, Observer {
                 it.toString()
             })

           /*  this.matrix.observe(this@MainActivity, Observer {
                 it.toString()
             })*/

        }

    }

    override fun onStart() {
        super.onStart()
       /* initViews()
        // Initializing mapView element
        initMap()

        setupMarker()*/


    }

    private fun setupMarker() {


        mapView.setOnMapLongClickListener { latLng ->


            if (markers.size < 6) {
                when {
                    markers.size == 0 -> {
                        originRemoved = true
                        markers.add(0, createMarker(latLng, originRemoved))
                        originRemoved = false
                    }
                    originRemoved -> {
                        markers.add(0, createMarker(latLng, originRemoved))
                        originRemoved = false
                    }
                    else -> {
                        markers.add(createMarker(latLng, originRemoved))
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

    // Initializing map
    private fun initMap() {

        // Setting map focal position to a fixed position and setting camera zoom
        mapView.moveCamera(LatLng(35.767234, 51.330743), 0f)
        mapView.setZoom(14.0F, 0.0F)
    }

    private fun neshanMatrixApi() {
        val distances = arrayListOf<Int>()
        val origins = arrayListOf<LatLng>(markers[0].latLng)
        val destinations = arrayListOf<LatLng>()
        for (i in 1..5) {
            destinations.add(markers[i].latLng)
        }
        NeshanDistanceMatrix.Builder(
            "service.VNlPhrWb3wYRzEYmstQh3GrAXyhyaN55AqUSRR3V",
            origins, destinations
        ).build().call(object : Callback<NeshanDistanceMatrixResult?> {
            override fun onResponse(
                call: Call<NeshanDistanceMatrixResult?>,
                response: Response<NeshanDistanceMatrixResult?>
            ) {
                if (response.body() != null && response.body()!!
                        .rows != null && response.body()!!.rows.isNotEmpty()
                ) {
                    val responseBody = response.body()
                    for (i in 0..4) {
                        distances.add(responseBody!!.rows[0].elements[i].duration.value)
                    }
                    val shortestDistanceIndexValue =
                        distances.sortedWith(compareBy { it }).first().let { distances.indexOf(it) }
                    val shortestDestinationPath =
                        responseBody!!.destinationAddresses[shortestDistanceIndexValue]
                    neshanRoutingApi(
                        origins[0],
                        LatLng(
                            shortestDestinationPath.split(",")[0].toDouble(),
                            shortestDestinationPath.split(",")[1].toDouble()
                        )
                    )
                } else {
//                    Toast.makeText(this@MainActivity, "مسیری یافت نشد", Toast.LENGTH_LONG).show()
                }

            }

            override fun onFailure(call: Call<NeshanDistanceMatrixResult?>, t: Throwable) {
//                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
            }

        })

    }


    private fun neshanRoutingApi(origin: LatLng, destination: LatLng) {
        NeshanDirection.Builder(
            "service.VNlPhrWb3wYRzEYmstQh3GrAXyhyaN55AqUSRR3V",
            origin,
            destination
        )
            .build().call(object : Callback<NeshanDirectionResult?> {
                override fun onResponse(
                    call: Call<NeshanDirectionResult?>?,
                    response: Response<NeshanDirectionResult?>?
                ) {

                    // two type of routing
                    if (response?.body() != null && response.body()!!
                            .routes != null && response.body()!!.routes.isNotEmpty()
                    ) {
                        val route: Route = response.body()!!.routes[0]
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
                        mapSetPosition(overview)
                    } else {
                        Toast.makeText(this@MainActivity, "مسیری یافت نشد", Toast.LENGTH_LONG)
                            .show()
                    }
                }

                override fun onFailure(call: Call<NeshanDirectionResult?>?, t: Throwable?) {
//                    Toast.makeText(this@MainActivity, t?.message, Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun createMarker(latLng: LatLng?, originRemovedFlag: Boolean): Marker {


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

        if (originRemovedFlag) {
            markStCr.bitmap = BitmapUtils.createBitmapFromAndroidBitmap(
                BitmapFactory.decodeResource(
                    resources, R.drawable.ic_origin
                )
            )
        } else if (!originRemovedFlag) {
            markStCr.bitmap = BitmapUtils.createBitmapFromAndroidBitmap(
                BitmapFactory.decodeResource(
                    resources, R.drawable.ic_marker
                )
            )
        }


        // AnimationStyle object - that was created before - is used here
        // AnimationStyle object - that was created before - is used here
        markStCr.animationStyle = animSt
        val markSt = markStCr.buildStyle()

        // Creating marker
        // Creating marker
        marker = Marker(latLng, markSt)

        // Adding marker to markerLayer, or showing marker on map!

        // Adding marker to markerLayer, or showing marker on map!
        mapView.addMarker(marker)
        return marker
    }


    private fun mapSetPosition(overview: Boolean) {
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

    // In this method we create a LineStyleCreator, set its features and call buildStyle() method
    // on it and return the LineStyle object (the same routine as crating a marker style)
    private fun getLineStyle(): LineStyle? {
        val lineStCr = LineStyleBuilder()
        lineStCr.color = Color(2.toShort(), 119.toShort(), 189.toShort(), 190.toShort())
        lineStCr.width = 10f
        lineStCr.stretchFactor = 0f
        return lineStCr.buildStyle()
    }

    fun findRoute(view: View?) {
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