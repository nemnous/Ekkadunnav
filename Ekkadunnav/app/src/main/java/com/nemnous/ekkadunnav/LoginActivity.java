package com.nemnous.ekkadunnav;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth auth;
    EditText email, passw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        email =  findViewById(R.id.emailid);
        passw =  findViewById(R.id.passwordid);
    }

    public void login(View v) {
        auth.signInWithEmailAndPassword(email.getText().toString(), passw.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "You're Welcome" , Toast.LENGTH_LONG).show();
                    Intent mapIntent = new Intent(LoginActivity.this, theNavBar.class);
                    startActivity(mapIntent);
                } else {
                    Toast.makeText(getApplicationContext(), "Fucked Up! Wrong Credentials" , Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}


