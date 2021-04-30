package com.foodwaste.foodwastevaluetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
            if (emailInput.isEmpty()||passwordInput.isEmpty()){
                Toast.makeText(StartActivity.this,"Please Fill out Email and Password",Toast.LENGTH_SHORT).show();
            }else{
                loginUser(emailInput,passwordInput);
                }

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

    //Login method. Den er udenfor main men bliver kaldt længere oppe.
    private void loginUser(String email,String password) {

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult>task) {

               if (task.isSuccessful()) {
                   Toast.makeText(StartActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                   Intent intent = new Intent(StartActivity.this,MainActivity.class);
                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   startActivity(intent);
                   finish();
               }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(StartActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
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
            finish();
        }

    }

}
