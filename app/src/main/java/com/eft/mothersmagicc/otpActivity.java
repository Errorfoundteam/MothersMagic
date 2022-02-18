package com.eft.mothersmagicc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class otpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private String sentcode;
    private EditText recievedOtpTv;
    TextView LoginBtn,resend;
    String code=null;
    TextView tvCountdown;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillisecond=60000;
    private String phn0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        recievedOtpTv=findViewById(R.id.RecievedOtpId);
        LoginBtn=findViewById(R.id.AuthenticateBtnId);
        resend=findViewById(R.id.tv_resend);
        tvCountdown=findViewById(R.id.tv_countdown);
        String phnumb = getIntent().getStringExtra("Phnumber");
        phn0 = phnumb;
        String phnumber="+91"+phnumb;
        mAuth = FirebaseAuth.getInstance();
        requestOtp(phnumber);
resend.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        resend.setVisibility(View.GONE);
        countDownTimer.onFinish();
        requestOtp(phnumber);
        Toast.makeText(otpActivity.this, "Resending Code....", Toast.LENGTH_SHORT).show();
    }
});
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code = recievedOtpTv.getText().toString();

                if (code.length() != 6) {
                    recievedOtpTv.setError("Incorrecet Otp");
                    recievedOtpTv.requestFocus();
                    return;
                }else {

//                    progressbar.setVisibility(View.VISIBLE);
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(sentcode, code);
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });
    }



    //Phone verification
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(otpActivity.this,"1", Toast.LENGTH_LONG).show();

                        if(task.isSuccessful()){
                            //successfully signed in as code of user and sent code matched;
//                            autoverify.cancel();
                            PhoneAuthSuccessfully();
                        }
                        else{
//                            autoverify.cancel();
//                            progressbar.setVisibility(View.GONE);
                            try {
                                if (task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                                    recievedOtpTv.setError("Wrong Otp");
                                }
                            }catch (Exception e){
                                Toast.makeText(otpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }


                        }
                    }
                })
                .addOnCanceledListener(this, new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        Toast.makeText(otpActivity.this, "2 CAncelled", Toast.LENGTH_LONG).show();

                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(otpActivity.this,"3 Failure", Toast.LENGTH_LONG).show();

                    }
                });
    }

    private void PhoneAuthSuccessfully() {
        Toast.makeText(this, "Congratulations", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getBaseContext(), Userdetail.class);
        intent.putExtra("intent","P");
        intent.putExtra("value",phn0);
        startActivity(intent);

//        progressbar.setVisibility(View.GONE);
//        String user="Y";


    }

    private void requestOtp(String phnumber) {
//        progressbar.setVisibility(View.VISIBLE);


        PhoneAuthProvider.getInstance().verifyPhoneNumber(phnumber,120L, TimeUnit.SECONDS,this,mCallbacks);
    }

    private void startTimer() {
    countDownTimer=new CountDownTimer(timeLeftInMillisecond,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            timeLeftInMillisecond=millisUntilFinished;
        int sec=(int) timeLeftInMillisecond/1000;
        if(sec<10){    tvCountdown.setText("Auto Verifying OTP in (00:0"+sec+")");
        }
        else{tvCountdown.setText("Auto Verifying OTP in (00:"+sec+")");
        }

        }

        @Override
        public void onFinish() {
            resend.setVisibility(View.VISIBLE);
        }
    }.start();
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();

            if (code != null) {
//                login.setVisibility(View.GONE);
                recievedOtpTv.setText(code);
                long TIME_OUT=2500;

//                progressbar.setVisibility(View.VISIBLE);
                (new Handler()).postDelayed((Runnable)(new Runnable() {
                    public final void run() {
                        PhoneAuthCredential credential= PhoneAuthProvider.getCredential(sentcode,code);
                        signInWithPhoneAuthCredential(credential);
                    }
                }), TIME_OUT);
            }
        }

        @Override
        public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
            super.onCodeAutoRetrievalTimeOut(s);
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
//            autoverify.cancel();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            sentcode=s;
            startTimer();
            Toast.makeText(otpActivity.this, "Code Sent", Toast.LENGTH_SHORT).show();
//
//            progressbar.setVisibility(View.GONE);
//            autoverify=Toast.makeText(loginjava.this, "Wait we will try to auto-verify the code", Toast.LENGTH_LONG);
//            autoverify.show();
        }
    };

    //phone verification end here
}