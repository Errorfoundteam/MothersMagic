package com.eft.mothersmagicc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.eft.mothersmagicc.model.savedata;

public class Orderpage extends AppCompatActivity {
int x=1;
    EditText address;
    TextView foodprice;
    int intfoodprice;
int basefoodprice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderpage);
        Button mapbutton=findViewById(R.id.button2);
        ImageButton plus=findViewById(R.id.imageButton);
        ImageButton minus=findViewById(R.id.imageButton2);
        address=findViewById(R.id.textView55);
        TextView finalprice=findViewById(R.id.finalprice);
        TextView food_quantity =findViewById(R.id.textView5);
        TextView foodname=findViewById(R.id.textView10); // food name
        foodprice=findViewById(R.id.foodprice);
        TextView ordernowbtn=findViewById(R.id.ordernowbtn);
        ordernowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),payment_option.class));
savedata.setCurrect_address(address.getText().toString());
            }
        });

        intfoodprice=300;// get price from the firebase or somewhere else of the food price;

//        food price
        foodprice.setText("₹ "+savedata.foodarray.get(savedata.itemArrayClickPosition).getFull_price());
        intfoodprice=Integer.parseInt(savedata.foodarray.get(savedata.itemArrayClickPosition).getFull_price());
        basefoodprice=intfoodprice;
        foodname.setText(savedata.foodarray.get(savedata.itemArrayClickPosition).getFoodname());
        //food price end
        // Local database code
        SQLiteDatabase conn = openOrCreateDatabase("db", MODE_PRIVATE, null);

        conn.execSQL("create table if not exists address(sno varchar,abb varchar,addd varchar);");
        Cursor cc = conn.rawQuery("select * from address ", null);
        if(cc.moveToFirst()){
            savedata.setCurrect_address(cc.getString(2));
            address.setText(cc.getString(2));
        }



        // Local database code end
mapbutton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(getApplicationContext(),newaddressbook.class));

    }
});

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                x++;
                food_quantity.setText(x+"");
//                int m=(int) finalprice.getText();
            foodprice(x);
            }
        });minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        if(x>1) {
            x--;
            food_quantity.setText(x+"");
            foodprice(x);
        }
            }
        });
    }

    private void foodprice(int x) {
        intfoodprice=basefoodprice*x;
        foodprice.setText(intfoodprice+"");

        savedata.setTotalPriceNQuantity(x,intfoodprice);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        address.setText(savedata.shortlocation);

    }

    @Override
    protected void onResume() {
        super.onResume();

        address.setText(savedata.shortlocation);

    }

    @Override
    protected void onStart() {
        super.onStart();

        address.setText(savedata.shortlocation);

    }
}