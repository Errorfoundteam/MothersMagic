package com.eft.mothersmagicc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class Homepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        final MeowBottomNavigation bottomNavigation=findViewById(R.id.bottomNavigation);

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
                        Toast.makeText(Homepage.this, "one", Toast.LENGTH_SHORT).show();
//                        openlayout("search",1);
                        break;

                    case 1:
                        Toast.makeText(Homepage.this, "two", Toast.LENGTH_SHORT).show();
//                        openlayout("home", 2);
                        break;

                    case 2:
                        Toast.makeText(Homepage.this, "three", Toast.LENGTH_SHORT).show();
//                        openlayout("profile", 3);
                        break;
                    default:
//                        openlayout("home", 2);
                        break;
                }
                return null;
            }
        });



    }
}