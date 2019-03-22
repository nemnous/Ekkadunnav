package com.nemnous.ekkadunnav;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.karan.churi.PermissionManager.PermissionManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;
    PermissionManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        if(user == null) {
            setContentView(R.layout.activity_main);
        } else {
            Intent mapIntent = new Intent(this, theNavBar.class);
            startActivity(mapIntent);
            finish();
        }

        manager = new PermissionManager() {
        };
        manager.checkAndRequestPermissions(this);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        manager.checkResult(requestCode, permissions, grantResults);
        ArrayList<String> denied_Permissions = manager.getStatus().get(0).denied;

        if(denied_Permissions.isEmpty()) {
            Toast.makeText(getApplicationContext(), "All good to go", Toast.LENGTH_SHORT).show();

        }

    }

    public  void SignUpPage(View view) {
        Intent intent = new Intent(this, Signup.class);
        startActivity(intent);
    }
    public  void loginPage(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


}
