package com.nemnous.ekkadunnav;

import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class joinGang extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference reference;
    String CurrentUserId;
    String CurrentUserCode;
    Pinview viewPin;
    String CurrentUserName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_gang);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        CurrentUserId = user.getUid();



        reference = FirebaseDatabase.getInstance().getReference().child("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                CurrentUserCode = dataSnapshot.child(CurrentUserId).child("code").getValue(String.class);
                CurrentUserName = dataSnapshot.child(CurrentUserId).child("name").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    public  void joinFunc(View view) {


        viewPin = (Pinview) findViewById(R.id.pinViewId);

        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        Query query = rootRef.child("Users").orderByChild("code").equalTo(viewPin.getValue());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(getApplicationContext(), "Join Successful" , Toast.LENGTH_SHORT).show();
                    for (DataSnapshot user : dataSnapshot.getChildren()) { //user is the one with actual code
                        for(DataSnapshot Circleusers: user.child("myCircle").getChildren()) { //Circle users - users of the owner
                            Log.d("Circle Tag", Circleusers.getKey());
//                            String t_lat = Circleusers.getValue().toString();
//                            Log.d("Circle Tag", t_lat);
                            rootRef.child("Users").child(CurrentUserId).child("myCircle").child(Circleusers.getKey()).setValue("NA");
                            rootRef.child("Users").child(Circleusers.getKey()).child("myCircle").child(CurrentUserId).setValue("NA");
                        }
                        // do something with the individual "issues"
                    }
                    Intent intent = new Intent(getApplicationContext(), theNavBar.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Please verify Code", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
