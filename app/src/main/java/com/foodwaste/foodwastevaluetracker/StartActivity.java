package com.foodwaste.foodwastevaluetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;


public class StartActivity extends AppCompatActivity {

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

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            String emailInput = email.getText().toString();
            String passwordInput = password.getText().toString();

            loginUser(emailInput,passwordInput);

            startActivity(new Intent(StartActivity.this, MainActivity.class));
            finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, RegisterActivity.class));
                finish();
            }
        });


    }

    //onStart funktionen, der tjekker hvis brugeren allerede har registret, så bliver brugeren direkte startet i Main activity
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){
            startActivity(new Intent(StartActivity.this, MainActivity.class));
        }

    }

    //Login funktionen. Den er udenfor main men bliver kaldt længere oppe.
    private void loginUser(String email,String password) {

        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                Toast.makeText(StartActivity.this, "Login Successful",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(StartActivity.this, MainActivity.class));
                finish();
            }
        });



    }

}
