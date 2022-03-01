package com.eft.mothersmagicc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.eft.mothersmagicc.model.getfoodlist;
import com.eft.mothersmagicc.model.savedata;

import java.util.ArrayList;

public class OpenActivity extends AppCompatActivity {
    ArrayList<getfoodlist> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);
//        String p = getIntent().getStringExtra("position");
//        int position=Integer.parseInt(p);
//        Toast.makeText(this, p, Toast.LENGTH_SHORT).show();
        ImageView imageView=findViewById(R.id.imageView5);
        TextView name=findViewById(R.id.name1);
        name.setText(savedata.foodarray.get(savedata.itemArrayClickPosition).getFoodname());
        Glide.with(this)
                .load(savedata.foodarray.get(savedata.itemArrayClickPosition).getImage())
                .placeholder(R.drawable.testitempicture)
                .error(R.drawable.testitempicture)
                .into(imageView);

        TextView ordernow=findViewById(R.id.ordernow);
        ordernow.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),Orderpage.class)));




        list= savedata.foodarray;
        int position= getIntent().getExtras().getInt("position");

        TextView price=findViewById(R.id.price);
        TextView coockedby=findViewById(R.id.cookedby);
        TextView ingrediants=findViewById(R.id.ingrediants);
        TextView ratings=findViewById(R.id.textView12);

        name.setText(list.get(position).getFoodname());
        price.setText("₹ "+list.get(position).getFull_price());

        //uncommented code
        coockedby.setText(list.get(position).getCookedBy());
        ingrediants.setText(list.get(position).getIngrediants());

        ratings.setText(list.get(position).getRatings());


    }
}