package com.eft.mothersmagicc

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.eft.mothersmagicc.R
import com.eft.mothersmagicc.model.savedata
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task

class newaddressbook : AppCompatActivity() {

    var REQUEST_CHECK_SETTINGS = 16958
    var check :Boolean = false
    var rqstcode = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newaddressbook)

        check=true
        checkPermission()
    }
    override fun onRestart() {
        super.onRestart()
        Toast.makeText(this, "Restart", Toast.LENGTH_SHORT).show()
//        location_edit.textSize= 12.0F
//        location_edit.setText(savedata.shortlocation).toString()

    }



    private fun googleGpsEnabler(){
        val locationRequest = LocationRequest.create()?.apply {
            interval = 500
            fastestInterval = 500
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
            val handler = Handler()
            handler.postDelayed({
                // do something after 1000ms
                checkGpsStatus()
                Log.d("TAG","here1")
            }, 3000)

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
                Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), rqstcode)

        }
        else if(check){
            googleGpsEnabler()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK){
            //checkGpsStatus()
            startActivity(Intent(this,MapsActivity::class.java))
        }else{
            // googleGpsEnabler()  // it will ask for loaction again and again if user denies
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, RuntimePermission: Array<String> ,  grantResults: IntArray){
        super.onRequestPermissionsResult(requestCode, RuntimePermission, grantResults)
        when(requestCode){
            rqstcode -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    checkGpsStatus()
                } else {
                    Toast.makeText(this, "Please allow location access", Toast.LENGTH_SHORT).show()
                }
                return
            }
            else -> {
                // Ignore all other requests.
            }}}


}