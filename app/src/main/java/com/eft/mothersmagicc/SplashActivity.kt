package com.eft.mothersmagicc

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot as DataSnapshot1

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val logo=findViewById<ImageView>(R.id.logoo)
        val topamin : Animation = AnimationUtils.loadAnimation(this,R.anim.top_animation)
        logo.animation = topamin

        //default database
        //database

        val phAccount = FirebaseAuth.getInstance()
        val googleAccount = GoogleSignIn.getLastSignedInAccount(this)


        val handler = Handler()
        handler.postDelayed(
                {
                    val intent = Intent(this,MainActivity::class.java)
                    when {
                        phAccount.currentUser != null -> {
                            startActivity(Intent(this, Homepage::class.java))

                        }
                        googleAccount != null -> {
                            startActivity(Intent(this, Homepage::class.java))
                        }
                        else -> {
                        val options = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            ActivityOptions.makeSceneTransitionAnimation(this,
                            logo,"logoAnimationToLogin")
                        } else {
                            TODO("VERSION.SDK_INT < LOLLIPOP")
                        }
                            startActivity(intent)
                        }
                    }
                    finish()
                },
                3000
        )
    }
}
