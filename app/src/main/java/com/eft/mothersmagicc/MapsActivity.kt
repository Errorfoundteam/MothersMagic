package com.eft.mothersmagicc

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*
import com.eft.mothersmagicc.model.savedata

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var gpsStatus : Boolean = false
    private var requestCODE = 101
    private lateinit var fusedLocationProviderClient : FusedLocationProviderClient
    private var lat : String = ""
    private  var lng : String = ""
    private  var srtAdd : String = ""
    private  var lngAdd : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        isLocationIsActive()

        /*
        val autotext_View = findViewById<AutoCompleteTextView>(R.id.autoTextView)
        val languages = resources.getStringArray(R.array.Address)
        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_single_choice, languages)
        autotext_View.setAdapter(adapter)
        autoTextView.setAdapter(adapter)
        autoTextView.threshold = 1
        */

        findViewById<TextView>(R.id.submit_location).setOnClickListener {
            if (lat.isNotEmpty() &&lng.isNotEmpty() &&srtAdd.isNotEmpty() &&lngAdd.isNotEmpty()){
savedata(lat,lng,srtAdd,lngAdd)
                onBackPressed()
            }else{
                Toast.makeText(this,"Gps not working pls reopen this page again",Toast.LENGTH_SHORT).show()
            }

        }


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (gpsStatus) {
            Toast.makeText(this,"Location is enabled",Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this,"Location is disabled",Toast.LENGTH_SHORT).show()
        }
//        addressPopupId
        //val top_animation_2 : Animation = AnimationUtils.loadAnimation(this, R.anim.top_animation_2)
        //mainPopupBox.animation = top_animation_2

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    private fun isLocationIsActive(){
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        gpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.isMyLocationEnabled = true

        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this, ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(ACCESS_FINE_LOCATION), requestCODE
            )
            return
        }

        fusedLocationProviderClient.lastLocation.addOnCompleteListener {task->
            mMap = googleMap
            mMap.isMyLocationEnabled = true

            val location: Location? = task.result
            if(location == null){
                newLocationData()
            }else{
                googleMap.apply {
                    // just a random location our map will point to when its launched
                    val myLocation = LatLng(location.latitude,location.longitude)
                    addMarker(MarkerOptions().apply {
                        position(myLocation)
                        icon(BitmapDescriptorFactory.fromResource(R.drawable.location_pin))
                        title("My Location")
                        draggable(false)
                    })
                    Toast.makeText(this@MapsActivity,
                            getAddress(location.latitude,location.longitude),Toast.LENGTH_SHORT
                    ).show()
                    lat = location.latitude.toString()
                    lng = location.longitude.toString()
                    animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 18f))
                }


                mMap.setOnCameraIdleListener {

                    findViewById<ImageView>(R.id.imgLocationPinUp).visibility = View.VISIBLE

                    val markerOptions = MarkerOptions().position(mMap.cameraPosition.target)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.location_pin))
                    //mMap.addMarker(markerOptions)
                    Toast.makeText(
                            this@MapsActivity,
                            "${mMap.cameraPosition.target.latitude} and ${mMap.cameraPosition.target.longitude}",
                            Toast.LENGTH_SHORT
                    ).show()
                    lat = mMap.cameraPosition.target.latitude.toString()
                    lng = mMap.cameraPosition.target.longitude.toString()
                    Toast.makeText(this@MapsActivity,
                            getAddress(mMap.cameraPosition.target.latitude,mMap.cameraPosition.target.longitude), Toast.LENGTH_SHORT
                    ).show()

                }
                mMap.setOnCameraMoveListener {
                    mMap.clear()
                    findViewById<ImageView>(R.id.imgLocationPinUp).visibility = View.VISIBLE
                }
                //dragAndGetLocation(googleMap)

            }
        }
    }

    private fun getAddress(lat: Double,long: Double):String{
        var cityName:String = ""
        var countryName = ""
        val geoCoder = Geocoder(this, Locale.getDefault())
        val adress = geoCoder.getFromLocation(lat,long,3)


        if (findViewById<EditText>(R.id.popupET2).text.toString() !=""){
            try {
                val abc = geoCoder.getFromLocationName(findViewById<EditText>(R.id.popupET2).text.toString(),5)
                findViewById<EditText>(R.id.popupET3).setText(abc[0].toString())
            }
            catch (e :IndexOutOfBoundsException){

            }}
        cityName = adress[0].getAddressLine(0)
        val j = " " + adress[0].subLocality
        val x = adress[0].getAddressLine(0).toString().split(",")
        val y=" "+ x[0]+", "+x[1]+ ", "+ x[2]
        val b = " " + adress[0].adminArea
        findViewById<EditText>(R.id.popupET).setText(y)
        findViewById<EditText>(R.id.popupET2).setText(j)
        findViewById<EditText>(R.id.popupET3).setText(b)
        countryName = adress[0].countryName
        Log.d("Debug:", "Your City: $cityName ; your Country $countryName")
        lngAdd = adress[0].getAddressLine(0)
        srtAdd = y
        return cityName
    }

    private fun newLocationData(){
        val locationRequest =  LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 100
        locationRequest.fastestInterval = 100
        locationRequest.numUpdates = 1
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                        this,
                        ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(ACCESS_FINE_LOCATION), requestCODE
            )
            return
        }
        fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,locationCallback, Looper.myLooper()
        )
    }

    private val locationCallback = object : LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult) {
            val lastLocation: Location = locationResult.lastLocation
            Toast.makeText(this@MapsActivity,"Current location ${lastLocation.latitude}and${lastLocation.longitude}",Toast.LENGTH_LONG).show()
            onMapReady(mMap)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}