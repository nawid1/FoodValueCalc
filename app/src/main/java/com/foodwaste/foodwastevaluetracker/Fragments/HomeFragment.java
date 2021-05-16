package com.foodwaste.foodwastevaluetracker.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foodwaste.foodwastevaluetracker.Model.FoodItem;
import com.foodwaste.foodwastevaluetracker.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    //FireBase Initialization
    private FirebaseDatabase db = FirebaseDatabase.getInstance("https://food-waste-value-tracker-default-rtdb.europe-west1.firebasedatabase.app/");
    private String uid = FirebaseAuth.getInstance().getUid();
    private DatabaseReference ref = db.getReference().child("Fooditem").child(uid);

    private TextView totalvaluelossTxt;
    private TextView totalvaluemonthTxt;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        totalvaluelossTxt = view.findViewById(R.id.totalValuelossTxt);
        totalvaluemonthTxt = view.findViewById(R.id.totalValuelossMonthTxt);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                int totalValueloss=0;
                int monthlyValueloss=0;


                for (DataSnapshot snap:snapshot.getChildren()){

                    FoodItem foodItem = snap.getValue(FoodItem.class);
                    totalValueloss += foodItem.getValueloss();
                    String TotalValueLoss = "Total Valueloss: " + totalValueloss;
                    totalvaluelossTxt.setText(TotalValueLoss);


                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });




    return view;
    }

}