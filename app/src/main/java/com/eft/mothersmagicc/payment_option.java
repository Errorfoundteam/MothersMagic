package com.eft.mothersmagicc;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.eft.mothersmagicc.model.savedata;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.Locale;

public class payment_option extends AppCompatActivity {
    private DatabaseReference mDatabase,nDatabase;
// ...
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_option);
        TextView cod=findViewById(R.id.cod);

        cod.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                uploadorder();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void uploadorder() {

    //foodname
    // quantity
    // amount
    // address

        String foodname=savedata.foodarray.get(savedata.itemArrayClickPosition).getFoodname();
        String foodquantity=savedata.foodQuantity+"";
        String amount=savedata.totalFoodPrice+"";
        String full_address=savedata.currect_address;
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH-mm-ss", Locale.getDefault()).format(new Date());
        String currentMonth = LocalDate.now().getMonth().toString();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Orders")
                .child(currentDate+" T "+currentTime);

        mDatabase.child("foodname").setValue(foodname);
        mDatabase.child("foodquantity").setValue(foodquantity);
        mDatabase.child("amount").setValue(amount);
        mDatabase.child("address_current").setValue(full_address);
        mDatabase.child("add_lat").setValue(savedata.latitude);
        mDatabase.child("add_lon").setValue(savedata.longitude);
        mDatabase.child("add_short").setValue(savedata.shortlocation);
        mDatabase.child("add_long").setValue(savedata.longlocation);
        mDatabase.child("date").setValue(currentDate);
        mDatabase.child("time").setValue(currentTime);
        mDatabase.child("delivery").setValue("no");
        mDatabase.child("phnumber").setValue(savedata.userPhoneNumber);

        //encryption

        String orderId = foodname+foodquantity+amount+currentDate+currentTime;
        String encryptedId = null;
        try
        {
            /* MessageDigest instance for MD5. */
            MessageDigest m = MessageDigest.getInstance("MD5");

            /* Add plain-text password bytes to digest using MD5 update() method. */
            m.update(orderId.getBytes());

            /* Convert the hash value into bytes */
            byte[] bytes = m.digest();

            /* The bytes array has bytes in decimal form. Converting it into hexadecimal format. */
            StringBuilder s = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            /* Complete hashed password in hexadecimal format */
            encryptedId = s.toString();

            mDatabase.child("orderid").setValue(encryptedId);
            mDatabase.child("adminmsg").setValue("no");
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }

        Toast.makeText(this, "Order Placed", Toast.LENGTH_LONG).show();
        nDatabase = FirebaseDatabase.getInstance().getReference().child("UserDetails/PhoneLogin")
                .child(savedata.userPhoneNumber)
        .child("orders").child(currentDate+" T "+currentTime);

        nDatabase.child("orderid").setValue(encryptedId);
    }
}