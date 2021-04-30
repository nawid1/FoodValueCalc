package com.foodwaste.foodwastevaluetracker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.util.HashMap;



public class AddActivity extends AppCompatActivity {


    private EditText foodName;
    private EditText foodPrice;
    private EditText foodPercent;
    private Button addFoodItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);


        foodName =findViewById(R.id.foodNameAdd);
        foodPrice =findViewById(R.id.foodPriceAdd);
        foodPercent =findViewById(R.id.foodPercentAdd);


        addFoodItem = findViewById(R.id.addFoodItem);

        addFoodItem.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {


                String foodNameTxt = foodName.getText().toString();
                String foodPriceTxt = foodPrice.getText().toString();
                String foodPercentTxt = foodPercent.getText().toString();
                LocalDate foodDateAdd = java.time.LocalDate.now();

                if (foodNameTxt.isEmpty()||foodPriceTxt.isEmpty()||foodPercentTxt.isEmpty()){

                    Toast.makeText(AddActivity.this,"Please Fill Out Empty Field",Toast.LENGTH_SHORT).show();
                }else {

                    HashMap<String, Object> foodItemData = new HashMap<>();
                    foodItemData.put("Food Item Price", foodPriceTxt);
                    foodItemData.put("Food Item Percent", foodPercentTxt);
                    foodItemData.put("Food Item Date", foodDateAdd.toString());
                    FirebaseDatabase.getInstance("https://food-waste-value-tracker-default-rtdb.europe-west1.firebasedatabase.app/")
                            .getReference().child("UID").updateChildren(foodItemData).addOnSuccessListener(new OnSuccessListener<Void>(){
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        foodName.setText("");
                                        foodPrice.setText("");
                                        foodPercent.setText("");
                                    }
                                } );

                     }
                }



        });



    }


}




        /* Sætte data gennem Hashmap:
        HashMap<String, Object> UserData = new HashMap<>();
        UserData.put("Name", "Navid");
        UserData.put("Email", "test@test.com");

        FirebaseDatabase.getInstance("https://food-waste-value-tracker-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Program").updateChildren(UserData);
*/
