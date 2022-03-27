package com.eft.mothersmagicc;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.eft.mothersmagicc.Adapter.HomeRecyclerViewAdapter;
import com.eft.mothersmagicc.Adapter.ViewPagerAdapter;
import com.eft.mothersmagicc.model.getfoodlist;
import com.eft.mothersmagicc.model.savedata;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
public class Homepage extends AppCompatActivity {
    private ShimmerFrameLayout shimmerFrameLayout;

    CarouselView carouselView;
    ConstraintLayout ProfilepageLayout;
    int[] sampleImages = {R.drawable.testadd, R.drawable.testadd, R.drawable.testadd, R.drawable.testadd, R.drawable.testadd};
    private ViewPagerAdapter homeslideAdapter;
    ScrollView HompageLayout;
    int currentpage=-1;
    private GoogleApiClient mGoogleApiClient;
    ArrayList<getfoodlist> list;
    RecyclerView recyclerView;
    HomeRecyclerViewAdapter myadapter;
    @Override
    protected void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
         mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        super.onStart();
    }
    @Override
    protected void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        shimmerFrameLayout.stopShimmer();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        final MeowBottomNavigation bottomNavigation=findViewById(R.id.bottomNavigation);
        HompageLayout=findViewById(R.id.hopagelayout);
        ProfilepageLayout=findViewById(R.id.profilepagelayout);
        shimmerFrameLayout=findViewById(R.id.Shimmer);
        recyclerView=findViewById(R.id.Home_itemrecycler_view);
//        = new HomeRecyclerViewAdapter(this);
//        recyclerView.setAdapter(myadapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false));




//Spiner
        Spinner location_spinner=findViewById(R.id.SpinnerAreaId);
        ArrayAdapter<CharSequence>adapter= ArrayAdapter.createFromResource(this, R.array.Location, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        location_spinner.setAdapter(adapter);

//Carouse
        carouselView = findViewById(R.id.AddCarouselHomepage);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);

        // Profile page
        TextView signout=findViewById(R.id.logout);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loggingout();
            }
        });


        //bottom navigation bar
        bottomNavigation.show(0,true);
        bottomNavigation.add(new MeowBottomNavigation.Model(0,R.drawable.ic_home));
        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_user));

        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch (model.getId()){
                    case 0:
                        HompageLayout.setVisibility(View.VISIBLE);
                        ProfilepageLayout.setVisibility(View.GONE);

                        Toast.makeText(Homepage.this, "two", Toast.LENGTH_SHORT).show();
                        break;

                    case 1:

                        HompageLayout.setVisibility(View.GONE);
                        ProfilepageLayout.setVisibility(View.VISIBLE);

                        Toast.makeText(Homepage.this, "three", Toast.LENGTH_SHORT).show();
                        break;
                    default:

                        HompageLayout.setVisibility(View.VISIBLE);
                        ProfilepageLayout.setVisibility(View.GONE);
                        break;
                }
                return null;
            }
        });

// firebase data

        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference("FoodItems/Jharkhand/Ranchi");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list=new ArrayList<getfoodlist>();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    getfoodlist updf=dataSnapshot1.getValue(getfoodlist.class);
                    list.add(updf);
                }

                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                myadapter=new HomeRecyclerViewAdapter(Homepage.this,list);
                recyclerView.setAdapter(myadapter);
                savedata.fooditemdetail(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Homepage.this, "Unable to show List, Check your Internet conn and Try again later", Toast.LENGTH_LONG).show();
            }
        });
    getUserDataFromSql();
    }

    private void loggingout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Homepage.this);
        builder.setMessage("Log out will remove your data and you need to login again for further orders.");
        builder.setTitle("Are you Sure?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //do things

                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                deletetable();
                                FirebaseAuth.getInstance().signOut();
                            }
                        });

            }
        });

        builder.setNegativeButton("NO",null);

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void getUserDataFromSql() {

        SQLiteDatabase conn = openOrCreateDatabase("db", MODE_PRIVATE, null);
        conn.execSQL("create table if not exists phlogin(user varchar,phnumber varchar,email varchar,shortadd varchar,longadd varchar,long varchar,lati varchar);");
        TextView name=findViewById(R.id.username);
        TextView ph=findViewById(R.id.userphno_);
        Cursor c = conn.rawQuery("select * from phlogin", null);
        if (c.moveToFirst()) {
            name.setText(c.getString(0));
            ph.setText(c.getString(1));
            savedata.setUserPh(c.getString(1));
            Toast.makeText(this, name+"  "+ph, Toast.LENGTH_LONG).show();
        }


    }
//carouse Image
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };
    private void deletetable() {
        SQLiteDatabase conn = openOrCreateDatabase("db", MODE_PRIVATE, null);
        conn.execSQL("DROP TABLE IF EXISTS phlogin");
//        startActivity(new Intent(getApplicationContext(),splash_screen.class));
        Toast.makeText(getApplicationContext(),"Logged Out",Toast.LENGTH_SHORT).show();
        Intent i=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
    }

}