package com.foodwaste.foodwastevaluetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.foodwaste.foodwastevaluetracker.Fragments.HomeFragment;
import com.foodwaste.foodwastevaluetracker.Fragments.ProfileFragment;
import com.foodwaste.foodwastevaluetracker.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class UpdateProfileActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseDatabase db = FirebaseDatabase.getInstance("https://food-waste-value-tracker-default-rtdb.europe-west1.firebasedatabase.app/");
    private DatabaseReference ref = db.getReference().child("Users");
    private User user;

    private final String USER_NAME="NAME",PASSWORD="PASSWORD",EMAIL="EMAIL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        auth=FirebaseAuth.getInstance();
        findViewById(R.id.backUpdateprofile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateProfileActivity.this, MainActivity.class));
                finish();
            }
        });
        findViewById(R.id.emailUpdateBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { updateDialog(EMAIL);}
        });

        findViewById(R.id.nameUpdateBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { updateDialog(USER_NAME);}
        });


        findViewById(R.id.passwordUpdateBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { updateDialog(PASSWORD);}
        });

        getUserData();
    }


    private void getUserData(){


        ref.child(auth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {

                user = dataSnapshot.getValue(User.class);

            }
        });
    }


    private void updateDialog(String updateType){

        Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.dialog_update_profile);
        dialog.setCancelable(false);

        Button cancelBtn,updateBtn;
        EditText updateBox,oldEmailBox,oldPassBox;
        cancelBtn=dialog.findViewById(R.id.cancelBtn);
        updateBtn=dialog.findViewById(R.id.updateBtn);
        updateBox=dialog.findViewById(R.id.updateBox);
        oldEmailBox=dialog.findViewById(R.id.oldEmailBox);
        oldPassBox=dialog.findViewById(R.id.oldPasswordBox);

        switch (updateType){

            case USER_NAME:
                updateBox.setText(user.getUserName());
                updateBox.setHint("Enter New user name");
                oldEmailBox.setVisibility(View.GONE);
                oldPassBox.setVisibility(View.GONE);
                break;
            case PASSWORD:
                updateBox.setText("");
                updateBox.setHint("Enter new password");
                oldEmailBox.setVisibility(View.VISIBLE);
                oldPassBox.setVisibility(View.VISIBLE);
                break;
            case EMAIL:
                updateBox.setText(user.getEmail());
                updateBox.setHint("Enter New email");
                oldEmailBox.setVisibility(View.VISIBLE);
                oldPassBox.setVisibility(View.VISIBLE);
                break;
        }



        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { dialog.dismiss();

            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String updateValue=updateBox.getText().toString().trim();
                String oldEmail,oldPassword;

                switch (updateType){

                    case USER_NAME:

                        if(updateValue.isEmpty()){
                            Toast.makeText(UpdateProfileActivity.this,"Enter user name",Toast.LENGTH_LONG).show();
                        }
                        else
                        {

                            Map<String, Object> update = new HashMap<String,Object>();
                            update.put("userName",updateValue);
                            ref.child(auth.getCurrentUser().getUid()).updateChildren(update).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                    Toast.makeText(UpdateProfileActivity.this,"Name updated",Toast.LENGTH_LONG).show();
                                    dialog.dismiss();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull @NotNull Exception e) {
                                    Toast.makeText(UpdateProfileActivity.this,""+e.getMessage(),Toast.LENGTH_LONG).show();

                                }
                            });

                        }

                        break;
                    case PASSWORD:

                        oldEmail=oldEmailBox.getText().toString().trim();
                        oldPassword=oldPassBox.getText().toString().trim();

                        if(oldEmail.isEmpty()){
                            Toast.makeText(UpdateProfileActivity.this,"Enter old email",Toast.LENGTH_LONG).show();
                        }
                        else
                        if(oldPassword.isEmpty()){
                            Toast.makeText(UpdateProfileActivity.this,"Enter old password",Toast.LENGTH_LONG).show();
                        }
                        else
                        if(updateValue.isEmpty()){
                            Toast.makeText(UpdateProfileActivity.this,"Enter new password",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            auth.signInWithEmailAndPassword(oldEmail,oldPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {

                                    auth.getCurrentUser().updatePassword(updateValue).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                                            Toast.makeText(UpdateProfileActivity.this,"Password updated",Toast.LENGTH_LONG).show();
                                            dialog.dismiss();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull @NotNull Exception e) {
                                            Toast.makeText(UpdateProfileActivity.this,""+e.getMessage(),Toast.LENGTH_LONG).show();

                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull @NotNull Exception e) {
                                    Toast.makeText(UpdateProfileActivity.this,""+e.getMessage(),Toast.LENGTH_LONG).show();

                                }
                            });



                        }
                        break;
                    case EMAIL:

                        oldEmail=oldEmailBox.getText().toString().trim();
                        oldPassword=oldPassBox.getText().toString().trim();

                        if(oldEmail.isEmpty()){
                            Toast.makeText(UpdateProfileActivity.this,"Enter old email",Toast.LENGTH_LONG).show();
                        }
                        else
                        if(oldPassword.isEmpty()){
                            Toast.makeText(UpdateProfileActivity.this,"Enter old password",Toast.LENGTH_LONG).show();
                        }
                        else
                        if(updateValue.isEmpty()){
                            Toast.makeText(UpdateProfileActivity.this,"Enter new email",Toast.LENGTH_LONG).show();
                        }
                        else
                        {


                            auth.signInWithEmailAndPassword(oldEmail,oldPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {

                                    auth.getCurrentUser().updateEmail(updateValue).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull @NotNull Task<Void> task) {

                                            Map<String, Object> update = new HashMap<String,Object>();
                                            update.put("email",updateValue);
                                            ref.child(auth.getCurrentUser().getUid()).updateChildren(update).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {

                                                    Toast.makeText(UpdateProfileActivity.this,"Email updated",Toast.LENGTH_LONG).show();
                                                    dialog.dismiss();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull @NotNull Exception e) {
                                                    Toast.makeText(UpdateProfileActivity.this,""+e.getMessage(),Toast.LENGTH_LONG).show();

                                                }
                                            });
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull @NotNull Exception e) {
                                            Toast.makeText(UpdateProfileActivity.this,""+e.getMessage(),Toast.LENGTH_LONG).show();

                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull @NotNull Exception e) {
                                    Toast.makeText(UpdateProfileActivity.this,""+e.getMessage(),Toast.LENGTH_LONG).show();

                                }
                            });



                        }
                        break;
                }


            }
        });

        dialog.show();

    }

}