package com.eft.mothersmagicc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eft.mothersmagicc.Adapter.AddressRecyclerViewAdapter;
import com.eft.mothersmagicc.model.savedata;

import java.util.ArrayList;

public class addressbook extends AppCompatActivity {
RecyclerView addrecyclerview;
AddressRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addressbook);
        EditText editText=findViewById(R.id.location_edittext);
        Button addbutton=findViewById(R.id.addaddress);
        addrecyclerview=findViewById(R.id.addrecyclerview);

//getAddress();
        addbutton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String new_add=editText.getText().toString();
        String abb="HOME";
        addAddress(new_add,abb);
        System.out.println();
    }
});
    }

    // Local database code



    private void getAddress() {

        SQLiteDatabase conn = openOrCreateDatabase("db", MODE_PRIVATE, null);

        conn.execSQL("create table if not exists address(sno varchar,abb varchar,addd varchar);");

        Cursor c = conn.rawQuery("select * from address ", null);

        ArrayList<String> listadd=new ArrayList<String>();
        ArrayList<String> listabb=new ArrayList<String>();
        int x=c.getCount()-1;

        Toast.makeText(this, x, Toast.LENGTH_SHORT).show();
for(int i=0;i<x;i++) {
//    if (c.getPosition()) {
//        listabb.add(c.getString(0));
//        listadd.add(c.getString(1));
//        Toast.makeText(this, c.getString(1), Toast.LENGTH_SHORT).show();
//    }

}
        addrecyclerview.setLayoutManager(new LinearLayoutManager(this));


//        //adding list
        adapter=new AddressRecyclerViewAdapter(getApplicationContext(),listabb,listadd);
        addrecyclerview.setAdapter(adapter);

    }

    // Local database code

    private void addAddress( String new_add,String abb) {
        SQLiteDatabase conn=openOrCreateDatabase("db",MODE_PRIVATE,null);
        conn.execSQL("create table if not exists address(abb varchar,addd varchar);");

        conn.execSQL("insert into address values("+"'"+abb+"'"+","+"'"+new_add+"'"+");");
        Toast.makeText(this, "addedd", Toast.LENGTH_SHORT).show();
//        getAddress();
    }
}