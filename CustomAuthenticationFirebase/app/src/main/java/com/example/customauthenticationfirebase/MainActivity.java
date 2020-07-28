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

public class MainActivity extends AppCompatActivity {

    private long backPressedTime;

    //Views
    EditText eEmail, ePassword;
    Button bLogin, bCreate;

    //Firebase
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        //views instantiate
        eEmail = findViewById(R.id.editText_username);
        ePassword = findViewById(R.id.editText_password);
        bLogin = findViewById(R.id.button_login);
        bCreate = findViewById(R.id.button_createAccount);

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });

        bCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUp();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.getInstance().signOut();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Toast.makeText(this, "Login To Continue", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(backPressedTime + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
            return;
        }else{
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();

    }

    public void Login(){
        final String email = eEmail.getText().toString().trim();
        final String password =ePassword.getText().toString().trim();

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
        }else{
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        mUser = mAuth.getCurrentUser();
                        Toast.makeText(MainActivity.this, "Logged in as " + mUser.getDisplayName(), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(MainActivity.this, UserMenu.class);
                        startActivity(intent);

                    }else{
                        Toast.makeText(MainActivity.this, "Problem Logging In", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    public void SignUp(){
        Intent intent = new Intent(MainActivity.this,CreateAccount.class);
        startActivity(intent);
    }
}