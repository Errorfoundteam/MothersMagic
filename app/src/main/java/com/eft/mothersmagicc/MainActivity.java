package com.eft.mothersmagicc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eft.mothersmagicc.Adapter.ViewPagerAdapter;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import kotlin.jvm.internal.Intrinsics;

public class MainActivity extends AppCompatActivity {
    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 123;
    private FirebaseAuth mAuth;
EditText phnumber;
Button ButtonPhoneLogin;
    private ViewPager mViewPager ;
    private ViewPagerAdapter slideAdapter;
    int currentpage=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //hello
        mViewPager=findViewById(R.id.viewPagerLoginActivity);
        SpringDotsIndicator mImageView = findViewById(R.id.imageViewForThreeDot);  //this is your imageView

Handler handler=new Handler();

Runnable Update=new Runnable(){

    @Override
    public void run() {
        mViewPager.setCurrentItem(++currentpage,true);
        if (currentpage == 3 - 1) {
            currentpage = -1;
            // ++currentPage will make currentPage = 0
        }
    }
};
Timer timer=new Timer();
timer.schedule(new TimerTask() {
    @Override
    public void run() {
        handler.post(Update);
    }
},2000,2000);
slideAdapter= new ViewPagerAdapter(this);
mViewPager.setAdapter(slideAdapter);
mImageView.setViewPager(mViewPager);



                phnumber=findViewById(R.id.phnumber);
        mAuth = FirebaseAuth.getInstance();


        Button ButtonPhoneLogin=findViewById(R.id.ButtonPhoneLogin);
        ButtonPhoneLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phnumber.getText().toString().length() == 10){
                    Intent intent = new Intent(getBaseContext(), otpActivity.class);
                    intent.putExtra("Phnumber", phnumber.getText().toString());
                    startActivity(intent);
                }
                else{
                    phnumber.setError("Enter Valid Number");
                }
            }
        });


        Button gbutton=findViewById(R.id.Gmailbutton);
        createRequest();
        gbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    private void createRequest() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


//        progressbar.setVisibility(View.VISIBLE);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = (GoogleSignInAccount) task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                // ...
//                progressbar.setVisibility(View.GONE);
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            } catch (Throwable throwable) {
                Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
//                progressbar.setVisibility(View.GONE);
                throwable.printStackTrace();
                Toast.makeText(getApplicationContext(), throwable.getMessage(),Toast.LENGTH_SHORT).show();

            }
        } else {
//            progressbar.setVisibility(View.GONE);}
        }}
        private void firebaseAuthWithGoogle (GoogleSignInAccount acct){
            Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();

            AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUser(user);
//                                text.setText("Sign In");
//                                Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
//                                startActivity(intent);


                            } else {
                                Toast.makeText(MainActivity.this, "Sorry auth failed.", Toast.LENGTH_SHORT).show();


                            }


                            // ...
                        }
                    });
        }
        private void updateUser (FirebaseUser user){
            Toast.makeText(this, user.getEmail().toString(), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "HeLLo  "+user.getDisplayName().toString(), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getBaseContext(), Userdetail.class);

            startActivity(intent);
        }

    }
