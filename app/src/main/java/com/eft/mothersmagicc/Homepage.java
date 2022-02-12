package com.eft.mothersmagicc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.eft.mothersmagicc.Adapter.HomeRecyclerViewAdapter;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class Homepage extends AppCompatActivity {
RecyclerView HomeRecyclerView;
ConstraintLayout SearchLayout,ProfilepageLayout;
LinearLayout HompageLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        final MeowBottomNavigation bottomNavigation=findViewById(R.id.bottomNavigation);
        HomeRecyclerView=findViewById(R.id.HomeRecycleId);
        HompageLayout=findViewById(R.id.hopagelayout);
        SearchLayout=findViewById(R.id.searchlayout);
        ProfilepageLayout=findViewById(R.id.profilepagelayout);

        Spinner location_spinner=findViewById(R.id.SpinnerAreaId);
        ArrayAdapter<CharSequence>adapter= ArrayAdapter.createFromResource(this, R.array.Location, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        location_spinner.setAdapter(adapter);
//        s7=getResources().getStringArray(R.array.name);
        HomeRecyclerViewAdapter myadapter= new HomeRecyclerViewAdapter(this);
        HomeRecyclerView.setAdapter(myadapter);
        HomeRecyclerView.setLayoutManager(new LinearLayoutManager(this));




//        homerecycl.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                if (dy > 0 && bottomNavigation.isShown()) {
//                    H_notification_bar.setVisibility(View.GONE);
//                    bottomNavigation.setVisibility(View.GONE);
//                } else if (dy < 0) {
//                    H_notification_bar.setVisibility(View.VISIBLE);
//                    bottomNavigation.setVisibility(View.VISIBLE);
//                }
//            }
//        });

        bottomNavigation.show(1,true);
        bottomNavigation.add(new MeowBottomNavigation.Model(0,R.drawable.ic_search));
        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_home));
        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_user));

        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch (model.getId()){
                    case 0:
                        SearchLayout.setVisibility(View.VISIBLE);
                        HompageLayout.setVisibility(View.GONE);
                        ProfilepageLayout.setVisibility(View.GONE);
                        Toast.makeText(Homepage.this, "one", Toast.LENGTH_SHORT).show();
//                        openlayout("search",1);
                        break;

                    case 1:
                        SearchLayout.setVisibility(View.GONE);
                        HompageLayout.setVisibility(View.VISIBLE);
                        ProfilepageLayout.setVisibility(View.GONE);
                        Toast.makeText(Homepage.this, "two", Toast.LENGTH_SHORT).show();
//                        openlayout("home", 2);
                        break;

                    case 2:
                        SearchLayout.setVisibility(View.GONE);
                        HompageLayout.setVisibility(View.GONE);
                        ProfilepageLayout.setVisibility(View.VISIBLE);
                        Toast.makeText(Homepage.this, "three", Toast.LENGTH_SHORT).show();
//                        openlayout("profile", 3);
                        break;
                    default:

                        SearchLayout.setVisibility(View.GONE);
                        HompageLayout.setVisibility(View.VISIBLE);
                        ProfilepageLayout.setVisibility(View.GONE);
//                        openlayout("home", 2);
                        break;
                }
                return null;
            }
        });



    }

}