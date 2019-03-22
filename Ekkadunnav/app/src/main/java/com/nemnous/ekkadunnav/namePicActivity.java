package com.nemnous.ekkadunnav;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

public class namePicActivity extends AppCompatActivity {
    EditText Name;
    CircleImageView circularImage;
    String Email;
    String Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_pic);
        Name = (EditText) findViewById(R.id.nameid);
        circularImage = (CircleImageView) findViewById(R.id.picid);
        Intent intent = getIntent();
        this.Email = intent.getStringExtra("Email");
        this.Password = intent.getStringExtra("Password");
    }

    public void profilepic(View view) {
        Toast.makeText(getApplicationContext(), "Working on it bae" , Toast.LENGTH_LONG).show();
    }
    public void nextButton(View view) {
        String name = Name.getText().toString();
        Toast.makeText(getApplicationContext(), Email, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, GenerateCodeActivity.class);
        intent.putExtra("Name", name);
        intent.putExtra("Email", this.Email);
        intent.putExtra("Password", this.Password);
        startActivity(intent);
    }

}
