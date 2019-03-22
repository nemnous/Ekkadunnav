package com.nemnous.ekkadunnav;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InviteCodeActivity extends AppCompatActivity {
    String code;
    FirebaseAuth auth;
    FirebaseUser user;
    TextView text;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_code);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("invire", dataSnapshot.child("Users").child(user.getUid()).child("code").getValue(String.class));
                code = dataSnapshot.child("Users").child(user.getUid()).child("code").getValue(String.class);
                text = (TextView) findViewById(R.id.codeView);
//                Toast.makeText(getApplicationContext(), "Hola", Toast.LENGTH_SHORT).show();
                text.setText(dataSnapshot.child("Users").child(user.getUid()).child("code").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



//        code = reference.child("Users").child(user.getUid()).child("code").toString();
//        System.out.print(code);
//        Log.d("Invite", code);
//        text.setText(code);
    }

    public void invitebutton(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String mess = "Please use " + code + " to join the gang";
        intent.putExtra(Intent.EXTRA_TEXT, mess );
        startActivity(intent.createChooser(intent, "Share the code using:"));
    }

}
