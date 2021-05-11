package com.foodwaste.foodwastevaluetracker.Fragments;

import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.foodwaste.foodwastevaluetracker.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddFragment extends Fragment {

    //Global Variables declaration
    private RecyclerView recyclerView;

    //Firebase initialzation
    private String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private FirebaseDatabase db = FirebaseDatabase.getInstance("https://food-waste-value-tracker-default-rtdb.europe-west1.firebasedatabase.app/");
    private DatabaseReference ref = db.getReference();

    public AddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton addBtn = (FloatingActionButton) view.findViewById(R.id.fabAdd);
        addBtn.setOnClickListener(v -> {
            addFootItem();
        });


    }

    private void addFootItem() {

        AlertDialog.Builder myDialog = new AlertDialog.Builder(getView().getContext());
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.addinput_layout,null);
        myDialog.setView(view);
        final AlertDialog dialog = myDialog.create();
        dialog.setCancelable(false);
        dialog.show();

        Toast.makeText(getContext(),"Yes",Toast.LENGTH_LONG).show();

    }


}