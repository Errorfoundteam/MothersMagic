package com.eft.mothersmagicc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.eft.mothersmagicc.model.savedata;

public class OpenActivity extends AppCompatActivity {

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

    }
}