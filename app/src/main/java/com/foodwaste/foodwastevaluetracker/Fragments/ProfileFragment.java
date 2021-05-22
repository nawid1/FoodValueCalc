package com.foodwaste.foodwastevaluetracker.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.foodwaste.foodwastevaluetracker.Model.User;
import com.foodwaste.foodwastevaluetracker.R;
import com.foodwaste.foodwastevaluetracker.LoginActivity;
import com.foodwaste.foodwastevaluetracker.UpdateProfileActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileFragment extends Fragment {

    Context context;
    FirebaseAuth auth;
    private Button logOut;
    private FirebaseDatabase db = FirebaseDatabase.getInstance("https://food-waste-value-tracker-default-rtdb.europe-west1.firebasedatabase.app/");
    private DatabaseReference ref = db.getReference().child("Users");

    private View view;
    private TextView userNameTextV,userEmailTextV;
    private User user;
    private boolean mGetUserData=true;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        auth=FirebaseAuth.getInstance();

        logOut = view.findViewById(R.id.logoutBtn);
        logOut.setOnClickListener(v -> {

            auth.signOut();
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();


        });

        userEmailTextV=view.findViewById(R.id.userEmail);
        userNameTextV=view.findViewById(R.id.userName);


        view.findViewById(R.id.updateProfileBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), UpdateProfileActivity.class));
            }
        });

        getUserData();

        return view;
    }




    private void getUserData(){


        ref.child(auth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {

                user = dataSnapshot.getValue(User.class);

                userNameTextV.setText(user.getUserName());
                userEmailTextV.setText(user.getEmail());


            }
        });
    }


}