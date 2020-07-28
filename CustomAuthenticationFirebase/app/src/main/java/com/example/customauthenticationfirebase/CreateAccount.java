package com.example.customauthenticationfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class CreateAccount extends AppCompatActivity {

    //Views
    EditText eUsername, eEmail, ePassword;
    Button bSignUp;

    //Firebase
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        //Views instantiate
        eUsername = findViewById(R.id.editText_name);
        eEmail = findViewById(R.id.editText_username);
        ePassword = findViewById(R.id.editText_password);

        bSignUp = findViewById(R.id.button_signUp);

        //Instantiate Firebase
        mAuth = FirebaseAuth.getInstance();

        bSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccount();
            }
        });

    }

    public void CreateAccount(){
        final String email = eEmail.getText().toString().trim();
        final String password = ePassword.getText().toString().trim();

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Please provide the needed information", Toast.LENGTH_SHORT).show();
        }else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        userProfile();
                        Toast.makeText(CreateAccount.this, "Account Creation Successful", Toast.LENGTH_SHORT).show();

                        //Go back to login page
                        Intent intent = new Intent(CreateAccount.this,MainActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }

    }

    public void userProfile(){
        String username = eUsername.getText().toString().trim();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                    .setDisplayName(username).build();

            user.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(CreateAccount.this, "Account Creation Success", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}