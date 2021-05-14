package com.eft.mothersmagicc

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.ImageView
import android.location.LocationManager
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.tasks.Task


class Userdetail : AppCompatActivity() {
    lateinit var mapimg :ImageView
    var REQUEST_CHECK_SETTINGS = 16958
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userdetail)

        mapimg = findViewById(R.id.map2)
        mapimg.setOnClickListener {
            checkPermission()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK){
            checkGpsStatus()
        }else{
            // googleGpsEnabler()  // it will ask for loaction again and again if user denies
        }
    }
    private fun googleGpsEnabler(){
        val locationRequest = LocationRequest.create()?.apply {
            interval = 100
            fastestInterval = 100
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        val builder = locationRequest?.let {
            LocationSettingsRequest.Builder()
                    .addLocationRequest(it)
        }

        val client: SettingsClient = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder?.build())

        task.addOnSuccessListener { locationSettingsResponse ->
            // All location settings are satisfied. The client can initialize
            // location requests here.
            checkGpsStatus()
            Log.d("TAG","here1")
            //startActivity(Intent(this,MapsActivity::class.java))
        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException){
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    exception.startResolutionForResult(this,REQUEST_CHECK_SETTINGS)
                    Log.d("TAG","here")
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
    }

    private fun checkGpsStatus() {
        val locationManager = applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val gpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (gpsStatus) {
            startActivity(Intent(this,MapsActivity::class.java))
        } else {

            checkPermission()
        }
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) !==
                PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
                checkPermission()
            } else {
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
                checkPermission()
            }
        }
        else{
            googleGpsEnabler()
        }
    }
}