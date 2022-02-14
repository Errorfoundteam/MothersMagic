package com.eft.mothersmagicc.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.eft.mothersmagicc.MainActivity;
import com.eft.mothersmagicc.OpenActivity;
import com.eft.mothersmagicc.R;

import java.util.zip.Inflater;

public class HomeRecyclerViewAdapter  extends RecyclerView.Adapter<HomeRecyclerViewAdapter.MyViewHolder> {
    @NonNull
    Context context;
    String ImageArray[];

    public  HomeRecyclerViewAdapter(Context ct)
    {
        context=ct;
//        ImageArray=imagearray;
    }
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view= inflater.inflate(R.layout.item_view_holder,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Animation animatn= AnimationUtils.loadAnimation(context,R.anim.zoomin);
        holder.baselayout.startAnimation(animatn);
        holder.baselayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context, OpenActivity.class);

                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout baselayout;
        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            baselayout=itemView.findViewById(R.id.itembaselayout);
        }
    }
}
