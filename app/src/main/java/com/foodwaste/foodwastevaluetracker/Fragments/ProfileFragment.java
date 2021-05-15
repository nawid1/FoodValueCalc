package com.foodwaste.foodwastevaluetracker.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.foodwaste.foodwastevaluetracker.R;
import com.foodwaste.foodwastevaluetracker.StartActivity;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {

    Button logOut;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        logOut = view.findViewById(R.id.logoutBtn);
        logOut.setOnClickListener(v -> {

            FirebaseAuth.getInstance().signOut();
        });



        return view;
    }

}