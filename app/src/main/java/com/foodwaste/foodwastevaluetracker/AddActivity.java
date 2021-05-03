package com.foodwaste.foodwastevaluetracker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;



public class AddActivity extends AppCompatActivity {


    private EditText foodName;
    private EditText foodPrice;
    private EditText foodPercent;
    private Button addFoodItem;
    private ListView listView;
    String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);


        listView = findViewById(R.id.listview);
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
                    foodItemData.put("Food Item Name", foodNameTxt);
                    foodItemData.put("Food Item Price", foodPriceTxt);
                    foodItemData.put("Food Item Percent", foodPercentTxt);
                    foodItemData.put("Food Item Date", foodDateAdd.toString());
                    FirebaseDatabase.getInstance("https://food-waste-value-tracker-default-rtdb.europe-west1.firebasedatabase.app/")
                            .getReference("Users")
                            .child("User Typed food Item").setValue(foodItemData)
                            .addOnSuccessListener(new OnSuccessListener<Void>(){
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        foodName.setText("");
                                        foodPrice.setText("");
                                        foodPercent.setText("");
                                    }
                                } ).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(AddActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });

                     }
                }




        });


        final ArrayList<String> foodList = new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.foodlist,foodList);
        listView.setAdapter(adapter);
        DatabaseReference dbRef = FirebaseDatabase.getInstance("https://food-waste-value-tracker-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("Users").child(userid);

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                foodList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    foodList.add(snapshot.getValue().toString());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
