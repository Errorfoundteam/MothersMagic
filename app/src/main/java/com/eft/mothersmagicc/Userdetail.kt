package com.eft.mothersmagicc

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.google.firebase.database.FirebaseDatabase
import  com.eft.mothersmagicc.model.savedata

class Userdetail : AppCompatActivity() {
    lateinit var mapimg :ImageView
    var REQUEST_CHECK_SETTINGS = 16958
    var rqstcode = 1
    var check=false
    lateinit var location_edit:EditText
    lateinit var child:String
    lateinit var usernode:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userdetail)
        val phrase = intent.getStringExtra("intent").toString()
        val info = intent.getStringExtra("value").toString()
        val submit_details=findViewById<TextView>(R.id.submit_details)
        val name_edit=findViewById<EditText>(R.id.name_edittext)
         location_edit=findViewById<EditText>(R.id.location_edittext)
        val peditText = findViewById<EditText>(R.id.phoneno_edit_text)
        val geditText = findViewById<EditText>(R.id.email_edit_text)

        if (phrase == "P"){

            location_edit.setText(savedata.shortlocation).toString()

            submit_details.setText("Submit")
            Toast.makeText(this, info, Toast.LENGTH_SHORT).show()
            peditText.setText(info).toString()
            peditText.isEnabled=false
        }else{

            submit_details.setText("Verify Number")
            Toast.makeText(this, info, Toast.LENGTH_SHORT).show()
//            geditText.setText(info).toString()

        }
        val googleAccount = GoogleSignIn.getLastSignedInAccount(this)
        if(googleAccount != null ) {
            geditText.setText(googleAccount.email).toString()
            name_edit.setText(googleAccount.displayName).toString()
            geditText.isEnabled=false
child="EmailLogin"
  usernode=googleAccount.email.substringBefore(".")
        }else{
            child="PhoneLogin"
            usernode=info
        }
        mapimg = findViewById(R.id.map2)
        mapimg.setOnClickListener {
check=true
            checkPermission()
        }

        submit_details.setOnClickListener {
            if (name_edit.text.toString().isEmpty()){
                name_edit.setError("invalid value")
            }else if (location_edit.text.toString().isEmpty()){
                location_edit.setError("invalid value")

            }else if (peditText.text.toString().isEmpty()){
                peditText.setError("invalid value")

            }else if (geditText.text.toString().isEmpty()){
                name_edit.setError("invalid value")

            }else if(phrase=="G"){
                if (peditText.text.toString().length == 10) {
                startActivity(Intent(this,otpActivity::class.java)
                        .putExtra("Phnumber", peditText.text.toString()))}
                else{ peditText.setError("invalid value")
               }
            } else{


saveUserDetailsToFirebase(name_edit.text.toString(),
        geditText.text.toString(),
        peditText.text.toString(),
        location_edit.text.toString())
            }
        }
        checkPermission()
    }

    override fun onRestart() {
        super.onRestart()
        Toast.makeText(this, "Restart", Toast.LENGTH_SHORT).show()
location_edit.textSize= 12.0F
        location_edit.setText(savedata.shortlocation).toString()

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


    private fun saveUserDetailsToFirebase(
            fullName: String,
            emailID: String,
            phoneNumber: String,
            address: String
    ){
        try {

            val ref = FirebaseDatabase.getInstance().getReference("/UserDetails/$child/$usernode")

            ref.child("name").setValue(fullName)
            ref.child("email").setValue(emailID)
            ref.child("phno").setValue(phoneNumber)
            ref.child("shortadd").setValue(savedata.shortlocation)
            ref.child("latitude").setValue(savedata.latitude)
            ref.child("longitude").setValue(savedata.longitude)
            ref.child("longadd").setValue(savedata.longlocation)
            startActivity(Intent(this,Homepage::class.java))
            finish()

        }catch (e:Exception){
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_SHORT).show()

        }

    }

}