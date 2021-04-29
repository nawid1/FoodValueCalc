package com.foodwaste.foodwastevaluetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddActivity extends AppCompatActivity {


    private EditText foodName,foodPrice, foodPercent;
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
            @Override
            public void onClick(View v) {

                String foodNameTxt = foodName.getText().toString();
                String foodPriceTxt = foodPrice.getText().toString();
                String foodPercentTxt = foodPercent.getText().toString();

                if (foodNameTxt.isEmpty()||foodPriceTxt.isEmpty()||foodPercentTxt.isEmpty()){

                    Toast.makeText(AddActivity.this,"Please fill out empty fields",Toast.LENGTH_SHORT).show();
                }else{

                    FirebaseDatabase.getInstance("https://food-waste-value-tracker-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Program").push().child(foodNameTxt).setValue(foodPriceTxt);
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
