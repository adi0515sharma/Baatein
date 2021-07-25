package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

public class signup extends AppCompatActivity {
    public FirebaseAuth mAuth;
    public FirebaseDatabase database;
    public DatabaseReference databaseReference;
    public EditText email, password, phone, name;
    public Button submit;
    public String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email_id);
        password = findViewById(R.id.password);
        submit = findViewById(R.id.submit);
        phone = findViewById(R.id.phone_no);
        name = findViewById(R.id.name);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();


        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (task.isSuccessful()){
                    token = task.getResult().toString();
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(signup.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            Log.d("success", "createUserWithEmail:success");

                                            FirebaseUser user = mAuth.getCurrentUser();
                                            User user1 = new User(email.getText().toString(), password.getText().toString(), token, phone.getText().toString(), name.getText().toString());
                                            database.getReference().child("Users").child(phone.getText().toString()).setValue(user1);
                                            Toast.makeText(signup.this, "Sign Up Successfull",Toast.LENGTH_LONG);
                                            Intent i = new Intent(signup.this,GettingStarted.class);
                                            i.putExtra("id",user.getUid());
                                            // go back to home page
                                        }
                                        else {
                                            // If sign in fails, display a message to the user.
                                            Log.w("failed", "createUserWithEmail:failure", task.getException());
                                            Toast.makeText(signup.this, "Authentication failed.",
                                                    Toast.LENGTH_SHORT).show();
                                            // show error
                                        }
                                    }
                                }

                        );
            }

        });
    }
}