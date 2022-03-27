package com.eft.mothersmagicc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eft.mothersmagicc.R;
import com.eft.mothersmagicc.model.getfoodlist;
import com.eft.mothersmagicc.model.savedata;

import java.util.ArrayList;

public class AddressRecyclerViewAdapter  extends RecyclerView.Adapter<AddressRecyclerViewAdapter.MyViewHolder> {


    Context context;
    ArrayList<String> addresslist;
    ArrayList<String> abbList;
    public  AddressRecyclerViewAdapter(Context ct,ArrayList<String> listabb, ArrayList<String> listadd)
    {
        context=ct;
        addresslist=listadd;
        abbList=listabb;
    }
    @NonNull
    @Override
    public AddressRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.addressitem_viewholder,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull AddressRecyclerViewAdapter.MyViewHolder holder, int position) {

        holder.add.setText(addresslist.get(position));
        holder.abb.setText(abbList.get(position));
        savedata.setCurrect_address(addresslist.get(position));
        Toast.makeText(context, "Selected    -    "+addresslist.get(position), Toast.LENGTH_LONG).show();
    }

    @Override
    public int getItemCount() {
        return addresslist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView add,abb;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            add=itemView.findViewById(R.id.addresstext);
            abb=itemView.findViewById(R.id.addabb);


        }

    }
}
