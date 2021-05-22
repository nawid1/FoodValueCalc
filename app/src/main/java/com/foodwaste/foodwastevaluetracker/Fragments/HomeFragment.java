package com.foodwaste.foodwastevaluetracker.Fragments;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.foodwaste.foodwastevaluetracker.Model.FoodItem;
import com.foodwaste.foodwastevaluetracker.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;
import org.joda.time.Months;
import org.joda.time.MutableDateTime;
import org.joda.time.Weeks;
import org.w3c.dom.Text;

import java.time.Month;
import java.util.ArrayList;
import java.util.Map;


public class HomeFragment extends Fragment {

    //FireBase Initialization
    private FirebaseDatabase db = FirebaseDatabase.getInstance("https://food-waste-value-tracker-default-rtdb.europe-west1.firebasedatabase.app/");
    private String uid = FirebaseAuth.getInstance().getUid();
    private DatabaseReference ref = db.getReference().child("Fooditem").child(uid);

    private TextView totalvalueloss;
    private TextView monthlyValueloss;
    private TextView weeklyValueloss;
    private ProgressBar mProgressBarTotal, mProgressBarW, mProgressBarM;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        totalvalueloss = view.findViewById(R.id.total);
        monthlyValueloss = view.findViewById(R.id.monthlyTotal);
        weeklyValueloss = view.findViewById(R.id.weeklyTotal);
        mProgressBarTotal=view.findViewById(R.id.pbTotal);
        mProgressBarW =view.findViewById(R.id.pbW);
        mProgressBarM =view.findViewById(R.id.pbM);


        setTotalvalueloss();
        setMonthlyValueloss();
        setWeeklyValueloss();

        return view;
    }



    private void setTotalvalueloss() {
        //Query to get Total
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                //Retrieving & Initializing total valueloss
                int totalValueloss =0;
                for (DataSnapshot snap:snapshot.getChildren()) {
                    Map<String, Object> map = (Map<String, Object>) snap.getValue();
                    Object totalVl = map.get("valueloss");
                    int ptotal = Integer.parseInt(String.valueOf(totalVl));
                    totalValueloss += ptotal;
                    totalvalueloss.setText(""+totalValueloss);

                }
                mProgressBarTotal.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
    private void setMonthlyValueloss(){

        //Retrieve & Initializing current month's valueloss
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();
        Months months = Months.monthsBetween(epoch, now);
        Query query = ref.orderByChild("month").equalTo(months.getMonths());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                int totalMonthlyValueloss=0;

                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    Map<String, Object> map = (Map<String, Object>) snapshot1.getValue();
                    Object totalVl = map.get("valueloss");
                    int ptotal = Integer.parseInt(String.valueOf(totalVl));
                    totalMonthlyValueloss += ptotal;
                    monthlyValueloss.setText(""+totalMonthlyValueloss);
                }
                mProgressBarM.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }
    private void setWeeklyValueloss() {
        //Retrieve & Initializing current month's valueloss
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();
        Weeks weeks = Weeks.weeksBetween(epoch, now);


        Query query = ref.orderByChild("week").equalTo(weeks.getWeeks());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                int totalWeeklyValueloss = 0;
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    Map<String, Object> map = (Map<String, Object>) snapshot1.getValue();
                    Object totalVl = map.get("valueloss");
                    int ptotal = Integer.parseInt(String.valueOf(totalVl));
                    totalWeeklyValueloss += ptotal;

                    weeklyValueloss.setText(""+totalWeeklyValueloss);
                }
                mProgressBarW.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });



    }


}