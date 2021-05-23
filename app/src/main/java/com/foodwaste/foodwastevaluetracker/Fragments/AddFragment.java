package com.foodwaste.foodwastevaluetracker.Fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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
import android.widget.CompoundButton;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;
import org.joda.time.Months;
import org.joda.time.MutableDateTime;
import org.joda.time.Weeks;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;
import java.util.zip.Inflater;


public class AddFragment extends Fragment {
    //Global Variables declaration
    private RecyclerView recyclerView;
    private String postKey="";
    private String fooditemName;
    private int price=0;
    private int usage=0;

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
        //Recyclerview initialization to java class
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
                        .setQuery(ref, FoodItem.class).setLifecycleOwner(this)
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
                holder.mview.setOnClickListener(v -> {
                     postKey = getRef(position).getKey();
                     fooditemName = model.getName();
                     price = model.getPrice();
                     usage = model.getUsage();

                     updateFooditem();

                });


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


    @RequiresApi(api = Build.VERSION_CODES.O)
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
            }
            if (TextUtils.isEmpty(price)) {
                fprice.setError("Type in Food Price");
            }
            if (TextUtils.isEmpty(usage)) {
                fusage.setError("Usede Percentage is Required");
            } else {
                //Date to firebase
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                Calendar cal = Calendar.getInstance();
                String date = dateFormat.format(cal.getTime());
                MutableDateTime epoch = new MutableDateTime();
                epoch.setDate(0);
                DateTime now = new DateTime();
                LocalDate today = LocalDate.now();
                int month = today.getMonthValue();
                Weeks weeks = Weeks.weeksBetween(epoch,now);

                float value = (Integer.parseInt(price) * Integer.parseInt(usage) / 100);
                float valueloss = (Integer.parseInt(price) - value);

                FoodItem foodItem = new FoodItem(name, Integer.parseInt(price),
                        Integer.parseInt(usage), date, month, category, value, valueloss, weeks.getWeeks());

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateFooditem() {
        //New alertdialog to inflate the updating cardview of fooditem
        AlertDialog.Builder dialog =  new AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.update_layout,null);
        dialog.setView(view);
        final AlertDialog alertDialog = dialog.create();

        //Initializing update layout textview & edittext to Java class
        final TextView ftitle = view.findViewById(R.id.fnameTitleTxt);
        final EditText updateFname =view.findViewById(R.id.updateFname);
        final EditText updateFprice =view.findViewById(R.id.updateFprice);
        final EditText updateFusage =view.findViewById(R.id.updateFusage);
        RadioGroup radioGroup = view.findViewById(R.id.updateCategoryBtn);

        //Initializing button update & delete
        Button update = view.findViewById(R.id.updateBtn);
        Button delete = view.findViewById(R.id.deleteBtn);

        //Update title Text
        ftitle.setText(fooditemName);
        updateFname.setText(fooditemName);
        updateFprice.setText(String.valueOf(price));
        updateFusage.setText(String.valueOf(usage));


        //Set onclick listener to the buttons of update layout
        update.setOnClickListener(v -> {

            RadioButton selectedRadiobutton = view.findViewById(radioGroup.getCheckedRadioButtonId());
            //Getiing the user typed input to java and adding it to Food item Class
            String name =updateFname.getText().toString();
            int price = Integer.parseInt(updateFprice.getText().toString());
            int usage =Integer.parseInt(updateFusage.getText().toString());
            String category = selectedRadiobutton.getText().toString();
            //Date to firebase
            @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Calendar cal = Calendar.getInstance();
            String date = dateFormat.format(cal.getTime());
            MutableDateTime epoch = new MutableDateTime();
            epoch.setDate(0);
            DateTime now = new DateTime();
            LocalDate today = LocalDate.now();
            int month = today.getMonthValue();
            Weeks weeks = Weeks.weeksBetween(epoch,now);

            float value = price * usage / 100;
            float valueloss = price - value;

            FoodItem foodItem = new FoodItem(name,price,usage,date,month,category,value,valueloss,weeks.getWeeks());
            ref.child(postKey).setValue(foodItem).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                Toast.makeText(view.getContext(),"Item Updated",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(view.getContext(),""+ (task.getException()).toString(),Toast.LENGTH_SHORT).show();
                }

                alertDialog.dismiss();
                 });



        });


        delete.setOnClickListener(v -> {
            ref.child(postKey).removeValue().addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Toast.makeText(view.getContext(),"Item Deleted",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(view.getContext(),""+ (task.getException()).toString(),Toast.LENGTH_SHORT).show();
                }

                alertDialog.dismiss();
            });


        });

        alertDialog.show();


    }

}

