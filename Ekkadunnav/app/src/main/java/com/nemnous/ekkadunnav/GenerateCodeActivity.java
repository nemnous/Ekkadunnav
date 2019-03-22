package com.nemnous.ekkadunnav;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Random;

public class GenerateCodeActivity extends AppCompatActivity {

    ProgressDialog dialog;
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference reference;
    String Name, Email, Password;
    TextView codeStr;
    String randomNumber;
    HashMap<String, HashMap<String, String>> myCircle = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_code);
        dialog = new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("Users");
        Intent intent = getIntent();
        this.Email = intent.getStringExtra("Email");
        this.Password = intent.getStringExtra("Password");
        this.Name = intent.getStringExtra("Name");
        codeStr = (TextView) findViewById(R.id.theCodeId);
        Random r = new Random();
        randomNumber = String.format("%04d", r.nextInt(1001));
        codeStr.setText(randomNumber);

    }
//
    public void finishButton(View view) {
        dialog.setMessage("Please Wait!");
        dialog.show();
        user = auth.getCurrentUser();
        String userId = user.getUid();

        HashMap<String, String> innerHash = new HashMap<>();
        innerHash.put("Lat", "NA");
        innerHash.put("Lon", "NA");
        myCircle.put(userId, innerHash);



//        Toast.makeText(getApplicationContext(), user.getUid(), Toast.LENGTH_LONG).show();
        CreateUser createUser = new CreateUser(userId, this.Name, this.Email, this.Password,randomNumber,"NA","NA","NA","NA",  this.myCircle);
//        reference.child(userId).setValue(createUser).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        dialog.dismiss();
//                        if(task.isSuccessful()) {
//                            Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
//                        } else {
//                            Toast.makeText(getApplicationContext(), "Failed to add details", Toast.LENGTH_LONG).show();
//                        }
//
//                    }
//                });
        reference.child(userId).setValue(createUser, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                dialog.dismiss();
                if (databaseError != null) {
                    Toast.makeText(getApplicationContext(), "Failed to add details", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
                    Intent mapIntent = new Intent(GenerateCodeActivity.this, theNavBar.class);
                    startActivity(mapIntent);
                }
            }
        });

//        Intent intent = new Intent(this, mapActivity.class);
//        startActivity(intent);
    }
}
