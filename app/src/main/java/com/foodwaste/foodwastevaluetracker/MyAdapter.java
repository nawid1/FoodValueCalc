package com.foodwaste.foodwastevaluetracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.foodwaste.foodwastevaluetracker.Model.FoodItem;

import org.jetbrains.annotations.NotNull;

public class MyAdapter extends FirebaseRecyclerAdapter<FoodItem,MyAdapter.myViewHolder> {

    public MyAdapter(@NonNull @NotNull FirebaseRecyclerOptions<FoodItem> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull MyAdapter.myViewHolder holder, int position, @NonNull @NotNull FoodItem foodItem) {
            holder.name.setText(foodItem.getName());
            holder.price.setText(foodItem.getPrice());
            holder.usage.setText(foodItem.getUsage());

    }

    @NonNull
    @NotNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row,parent,false);
       return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        TextView name,price,usage,date;
        public myViewHolder(View itemView){

            super(itemView);
            name = (TextView)itemView.findViewById(R.id.nameTxt);
            price = (TextView)itemView.findViewById(R.id.priceTxt);
            usage = (TextView)itemView.findViewById(R.id.usageTxt);


        }


    }


}


