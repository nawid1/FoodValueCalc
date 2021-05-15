package com.foodwaste.foodwastevaluetracker.Fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.foodwaste.foodwastevaluetracker.Model.FoodItem;
import com.foodwaste.foodwastevaluetracker.R;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;
import org.joda.time.Months;
import org.joda.time.MutableDateTime;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class AddFragment extends Fragment {
    //Global Variables declaration
    private TextView totalValueLoss;
    private RecyclerView recyclerView;
    //FireBase Initialization
    private FirebaseDatabase db = FirebaseDatabase.getInstance("https://food-waste-value-tracker-default-rtdb.europe-west1.firebasedatabase.app/");
    private String uid = FirebaseAuth.getInstance().getUid();
    private DatabaseReference ref = db.getReference().child("Fooditem").child(uid);
    String id = ref.push().getKey();


    public AddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.fragment_add, container, false);
        //Recyclerview and Adapter initialization
        recyclerView = view.findViewById(R.id.RecyclerView);

        //Floating action button in AddFragment
        FloatingActionButton addBtn = view.findViewById(R.id.fabAdd);
        addBtn.setOnClickListener(v -> {
            addFootItem();

        });

        return view;
    }



    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<FoodItem> options =
                new FirebaseRecyclerOptions.Builder<FoodItem>()
                        .setQuery(ref, FoodItem.class)
                        .build();
        LinearLayoutManager linearLayout = new LinearLayoutManager(getContext());
        linearLayout.setReverseLayout(true);
        linearLayout.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayout);

        FirebaseRecyclerAdapter<FoodItem,myViewholder> adapter = new FirebaseRecyclerAdapter<FoodItem, myViewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull @NotNull myViewholder holder, int position, @NonNull @NotNull FoodItem model) {

                holder.setItemName(""+model.getName());
                holder.setItemPrice("Price: "+model.getPrice()+" Kr.");
                holder.setItemUsage("Usage: "+model.getUsage()+" %");
                holder.setItemCategory("Category: "+model.getCategory());
                holder.setItemValue("Value: "+model.getValue()+" Kr.");
                holder.setItemValueloss("Valueloss: "+model.getValueloss()+" Kr.");
                holder.setItemDate("Date: "+model.getDate());

                switch (model.getCategory()){

                    case "Other":
                        holder.imageView.setImageResource(R.drawable.other);
                        holder.cardView.setCardBackgroundColor(Color.parseColor("#EAEDED"));
                        break;
                    case "Dairy":
                        holder.imageView.setImageResource(R.drawable.dairy);
                        holder.cardView.setCardBackgroundColor(Color.parseColor("#FFA500"));
                        break;
                    case "Bread":
                        holder.imageView.setImageResource(R.drawable.bread);
                        holder.cardView.setCardBackgroundColor(Color.parseColor("#FFD700"));
                        break;
                    case "Fruit & Vege":
                        holder.imageView.setImageResource(R.drawable.fruit_veg);
                        holder.cardView.setCardBackgroundColor(Color.parseColor("#3CB371"));
                        break;
                    case "Meat":
                        holder.imageView.setImageResource(R.drawable.meat);
                        holder.cardView.setCardBackgroundColor(Color.parseColor("#CD5C5C"));
                        break;

                }


            }

            @NonNull
            @NotNull
            @Override
            public myViewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow1,parent,false);
                return new myViewholder(view);
            }
        };


        recyclerView.setAdapter(adapter);
        adapter.startListening();
        adapter.notifyDataSetChanged();


    }



    public class myViewholder extends RecyclerView.ViewHolder {

        View mview;
        ImageView imageView;
        CardView cardView;

        public myViewholder(@NonNull @NotNull View itemView) {
            super(itemView);

            mview = itemView;
            imageView=mview.findViewById(R.id.imageView);
            cardView=mview.findViewById(R.id.cardview);

        }

        public void setItemName(String name) {
            TextView fname = mview.findViewById(R.id.nameTxt1);
            fname.setText(name);
        }

        public void setItemPrice(String itemPrice) {
            TextView fprice = mview.findViewById(R.id.priceTxt1);
            fprice.setText(itemPrice);
        }

        public void setItemUsage(String itemUsage) {
            TextView fusage = mview.findViewById(R.id.usageTxt1);
            fusage.setText(itemUsage);
        }

        public void setItemDate(String itemDate) {
            TextView fdate = mview.findViewById(R.id.dateTxt1);
            fdate.setText(itemDate);
        }

        public void setItemCategory(String itemCategory) {
            TextView fcategory = mview.findViewById(R.id.categoryTxt1);
            fcategory.setText(itemCategory);
        }

        public void setItemValue(String itemValue) {
            TextView fvalue = mview.findViewById(R.id.valueTxt1);
            fvalue.setText(itemValue);
        }

        public void setItemValueloss(String itemValueloss) {
            TextView fvalueloss = mview.findViewById(R.id.valuelossTxt1);
            fvalueloss.setText(itemValueloss);
        }

    }

    private void addFootItem() {
        AlertDialog.Builder myDialog = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.addinput_layout, null);

        myDialog.setView(view);
        final AlertDialog dialog = myDialog.create();
        dialog.setCancelable(false);
        //Pop up XML addinput connecting to Java class
        final RadioGroup radioGroup = view.findViewById(R.id.radioBtns);
        final EditText fname = view.findViewById(R.id.fnameInput);
        final EditText fprice = view.findViewById(R.id.fpriceInput);
        final EditText fusage = view.findViewById(R.id.fusageInput);
        final Button cancel = view.findViewById(R.id.cancelBtn);
        final Button save = view.findViewById(R.id.saveBtn);

        //If Save button Pressed
        save.setOnClickListener(v -> {

            RadioButton selectedRadiobutton = view.findViewById(radioGroup.getCheckedRadioButtonId());
            String category = selectedRadiobutton.getText().toString();
            String name = fname.getText().toString();
            String price = fprice.getText().toString();
            String usage = fusage.getText().toString();

            // Check if required inputfields are typed. And push to firebase
            if (TextUtils.isEmpty(name)) {
                fname.setError("Type in Food Name");
            } else if (TextUtils.isEmpty(price)) {
                fprice.setError("Type in Food Price");
            } else if (TextUtils.isEmpty(usage)) {
                fusage.setError("Usede Percentage is Required");
            } else {
                //Date to firebase
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                Calendar cal = Calendar.getInstance();
                String date = dateFormat.format(cal.getTime());
                MutableDateTime epoch = new MutableDateTime();
                epoch.setDate(0);
                DateTime now = new DateTime();
                Months months = Months.monthsBetween(epoch, now);

                float value = (Integer.parseInt(price) * Integer.parseInt(usage) / 100);
                float valueloss = (Integer.parseInt(price) - value);

                FoodItem foodItem = new FoodItem(name, Integer.parseInt(price),
                        Integer.parseInt(usage), date, months.getMonths(), category, value, valueloss);

                ref.child(id).setValue(foodItem).addOnCompleteListener(task -> {

                    Toast.makeText(view.getContext(),"Food Item Added",Toast.LENGTH_SHORT).show();
                    fname.setText("");
                    fprice.setText("");
                    fusage.setText("");
                    dialog.dismiss();
                }).addOnFailureListener(e ->
                        Toast.makeText(view.getContext(),"" + e, Toast.LENGTH_SHORT).show());
            }
        });
        //If Cancel button pressed
        cancel.setOnClickListener(v -> {
            dialog.cancel();
            dialog.dismiss();



        });

        dialog.show();
    }

}

