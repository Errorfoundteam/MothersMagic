package com.eft.mothersmagicc;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.eft.mothersmagicc.Adapter.HomeRecyclerViewAdapter;
import com.eft.mothersmagicc.Adapter.ViewPagerAdapter;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
public class Homepage extends AppCompatActivity {

    CarouselView carouselView;
    ConstraintLayout ProfilepageLayout;
    int[] sampleImages = {R.drawable.testadd, R.drawable.testadd, R.drawable.testadd, R.drawable.testadd, R.drawable.testadd};
    private ViewPagerAdapter homeslideAdapter;
    ScrollView HompageLayout;
    int currentpage=-1;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        final MeowBottomNavigation bottomNavigation=findViewById(R.id.bottomNavigation);
        HompageLayout=findViewById(R.id.hopagelayout);
        ProfilepageLayout=findViewById(R.id.profilepagelayout);

        recyclerView=findViewById(R.id.Home_itemrecycler_view);
        HomeRecyclerViewAdapter myadapter= new HomeRecyclerViewAdapter(this);
        recyclerView.setAdapter(myadapter);
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



    }
//carouse Image
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };


}