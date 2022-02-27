package com.eft.mothersmagicc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.eft.mothersmagicc.model.getfoodlist;
import com.eft.mothersmagicc.model.savedata;

import java.util.ArrayList;

public class Orderpage extends AppCompatActivity {
int x=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderpage);
        ImageButton plus=findViewById(R.id.imageButton);
        ImageButton minus=findViewById(R.id.imageButton2);

        TextView finalprice=findViewById(R.id.finalprice);
        TextView count=findViewById(R.id.textView5);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                x++;
                count.setText(x+"");
//                int m=(int) finalprice.getText();
            }
        });minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        if(x!=1) {
            x--;
            count.setText(x+"");
        }
            }
        });
    }
}