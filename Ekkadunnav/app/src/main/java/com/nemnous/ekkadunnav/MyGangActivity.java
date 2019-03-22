package com.nemnous.ekkadunnav;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyGangActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference reference;
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_gang);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference();
        txt = findViewById(R.id.myGangId);
        reference.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String str = "";
                if(dataSnapshot.exists()) {
                    Log.d("thisGang", dataSnapshot.child(user.getUid()).child("myCircle").toString());
                    for(DataSnapshot data : dataSnapshot.child(user.getUid()).child("myCircle").getChildren()) {
                        Log.d("Usernames", data.getKey());
                        for(DataSnapshot username : dataSnapshot.getChildren()) {
                            Log.d("theInneruser", username.getKey());
                            if(data.getKey().equals(username.getKey())) {
                                Log.d("nametag", username.child("name").getValue().toString());
                                str += "\n" + username.child("name").getValue().toString() + "\n";
                            }
                        }
                    }
                    txt.setText(str);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
