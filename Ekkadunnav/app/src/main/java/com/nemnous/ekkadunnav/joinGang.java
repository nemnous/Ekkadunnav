package com.nemnous.ekkadunnav;

import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class joinGang extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference reference;
    String CurrentUserId;
    String CurrentUserCode;


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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    public  void joinFunc(View view) {

        Toast.makeText(getApplicationContext(), CurrentUserCode, Toast.LENGTH_SHORT).show();

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        Query query = rootRef.child("Users").orderByChild("code").equalTo("0145");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot user : dataSnapshot.getChildren()) {
                        // do something with the individual "issues"

                        Log.d("sometag", user.child("name").getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
