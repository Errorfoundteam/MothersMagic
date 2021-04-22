package com.eft.mothersmagicc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth

class splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
//        val topamin : Animation = AnimationUtils.loadAnimation(this, top_animation)
//        logo.animation = topamin

        val phAccount = FirebaseAuth.getInstance()
        val googleAccount = GoogleSignIn.getLastSignedInAccount(this)


        val handler = Handler()
        handler.postDelayed(
                {
//                    val intent = Intent(this,LoginActivity::class.java)
                    when {
                        phAccount.currentUser != null -> {
//                            startActivity(Intent(this, BottomNavigationBar::class.java))

                        }
                        googleAccount != null -> {
//                            startActivity(Intent(this, MapActivity::class.java))
                        }
                        else -> {
//                        val options = ActivityOptions.makeSceneTransitionAnimation(this,
//                        logo,"logoAnimationToLogin")
                            startActivity(intent)
                        }
                    }
                    finish()
                },
                5000
        )
    }
}