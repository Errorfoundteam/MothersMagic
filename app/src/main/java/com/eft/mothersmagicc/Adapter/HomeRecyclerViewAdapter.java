package com.eft.mothersmagicc.Adapter;

import static com.airbnb.lottie.model.layer.Layer.LayerType.IMAGE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.eft.mothersmagicc.MainActivity;
import com.eft.mothersmagicc.OpenActivity;
import com.eft.mothersmagicc.R;

import java.util.ArrayList;
import java.util.Objects;
import java.util.zip.Inflater;

import com.eft.mothersmagicc.model.getfoodlist;
import com.eft.mothersmagicc.model.savedata;

public class HomeRecyclerViewAdapter  extends RecyclerView.Adapter<HomeRecyclerViewAdapter.MyViewHolder> {
    @NonNull
    Context context;
    String ImageArray[];
ArrayList<getfoodlist> listt;
    public  HomeRecyclerViewAdapter(Context ct, ArrayList<getfoodlist> list)
    {
        context=ct;
        listt=list;
//        ImageArray=imagearray;
    }
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view= inflater.inflate(R.layout.item_view_holder,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Animation animatn= AnimationUtils.loadAnimation(context,R.anim.zoomin);
        holder.baselayout.startAnimation(animatn);
        holder.Foodname.setText(listt.get(position).getFoodname());
        holder.coockedby.setText(listt.get(position).getCookedBy());
        holder.foodprice.setText(listt.get(position).getFull_price());
        Glide.with(context)
                .load(listt.get(position).getImage())
                .placeholder(R.drawable.testitempicture)
                .error(R.drawable.testitempicture)
                .into(holder.itemImage);
        Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();
        holder.baselayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context, OpenActivity.class);
                i.putExtra("position",position);
                ActivityOptionsCompat optionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,holder.itemImage,
                        Objects.requireNonNull(ViewCompat.getTransitionName(holder.itemImage)));

//                i.putExtra("position",position);
                savedata.fooditemposition(position);
                context.startActivity(i,optionsCompat.toBundle());

            }
        });

    }

    @Override
    public int getItemCount() {
        return listt.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout baselayout;
        ImageView itemImage;
        TextView Foodname,coockedby,foodprice;
        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            itemImage=itemView.findViewById(R.id.itemimageid);
            baselayout=itemView.findViewById(R.id.itembaselayout);
       Foodname=itemView.findViewById(R.id.foodnameid);
       coockedby=itemView.findViewById(R.id.foodcoockedbyid);
       foodprice=itemView.findViewById(R.id.foodprice);
        }
    }
}
