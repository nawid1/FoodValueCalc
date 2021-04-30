package com.foodwaste.foodwastevaluetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private EditText username;
    private EditText email;
    private EditText password;
    private Button register;
    ProgressDialog pd;

    private FirebaseAuth auth;
    private DatabaseReference myDbRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);

        auth = FirebaseAuth.getInstance();
        myDbRef = FirebaseDatabase.getInstance("https://food-waste-value-tracker-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        pd = new ProgressDialog(this);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameTxt = email.getText().toString();
                String emailTxt = email.getText().toString();
                String passwordTxt = password.getText().toString();


                if (TextUtils.isEmpty(emailTxt) || TextUtils.isEmpty(passwordTxt)||TextUtils.isEmpty(usernameTxt)) {

                    Toast.makeText(RegisterActivity.this, "Empty Credentials Or Match Password", Toast.LENGTH_SHORT).show();

                } else if (passwordTxt.length() < 6 ){

                    Toast.makeText(RegisterActivity.this, "Password has to be at least 6 character long", Toast.LENGTH_SHORT).show();
                }else{
                    registerUser(usernameTxt,emailTxt,passwordTxt);

                }

            }
        });

    }

        private void registerUser(final String username,final String email, final String password) {

            pd.setMessage("Please Wait. Signing Up");
            pd.show();
            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {

                    HashMap<String, Object>  userData = new HashMap<>();
                    userData.put("Username",username);
                    userData.put("Email",email);
                    userData.put("Password",password);
                    userData.put("UserID",auth.getCurrentUser().getUid());

                    myDbRef.child("Users").child(auth.getCurrentUser().getUid()).child("User Info").setValue(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                pd.dismiss();
                                Toast.makeText(RegisterActivity.this,"You have now Signed up. Start Tracking!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();
                    Toast.makeText(RegisterActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });


        }
}