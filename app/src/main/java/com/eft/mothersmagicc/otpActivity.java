package com.eft.mothersmagicc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class otpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        String sessionId = getIntent().getStringExtra("Phnumber");
    }
}