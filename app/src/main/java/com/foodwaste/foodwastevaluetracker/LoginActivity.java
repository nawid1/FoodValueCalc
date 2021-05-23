package com.foodwaste.foodwastevaluetracker;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class LoginActivity extends AppCompatActivity {

   private EditText email;
   private EditText password;
   private Button register;
   private Button login;
   private FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        email = findViewById(R.id.emailLogin);
        password = findViewById(R.id.passwordLogin);
        register = findViewById(R.id.registerBtn);
        login = findViewById(R.id.loginBtn);
        auth = FirebaseAuth.getInstance();

        //Skrive data til firebase med nedstående kode
        //FirebaseDatabase.getInstance("https://food-waste-value-tracker-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Program").child("Python").setValue("SNAKE");

        login.setOnClickListener(v -> {

        String emailInput = email.getText().toString();
        String passwordInput = password.getText().toString();
        if (emailInput.isEmpty()||passwordInput.isEmpty()){

            Toast.makeText(LoginActivity.this,"Please Fill out Email and Password",Toast.LENGTH_SHORT).show();
        }else{

            loginUser(emailInput,passwordInput);
            }

        });

        register.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            finish();
        });


    }


    //onStart funktionen, der tjekker hvis brugeren allerede har registret, så bliver brugeren direkte startet i Main activity
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){

            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

    }




    //Login method. Den er udenfor main men bliver kaldt længere oppe.
    private void loginUser(String email,String password) {

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {

           if (task.isSuccessful()) {
               Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
               Intent intent = new Intent(LoginActivity.this,MainActivity.class);
               intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
               startActivity(intent);
               finish();
           }
        }).addOnFailureListener(e -> Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show());



    }


}
