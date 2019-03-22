package com.nemnous.ekkadunnav;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.ProviderQueryResult;

public class Signup extends AppCompatActivity {
    EditText emailadd, passw;
    FirebaseAuth auth;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        emailadd = (EditText) findViewById(R.id.emailFieldId);
        passw = (EditText) findViewById(R.id.passId);
        auth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);
    }

    public void signupButton(View v) {
        //part5
        final Intent intent = new Intent(this, namePicActivity.class);
        dialog.setMessage("Verifying Mail-Id");
        dialog.show();
        auth.createUserWithEmailAndPassword(emailadd.getText().toString(), passw.getText().toString())
                .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                if (!task.isSuccessful())
                                {
                                    try
                                    {
                                        throw task.getException();
                                    }
                                    // if user enters wrong email.
                                    catch (FirebaseAuthWeakPasswordException weakPassword)
                                    {
//                                        Log.d(TAG, "onComplete: weak_password");
                                        Toast.makeText(getApplicationContext(), "WeakPassword" , Toast.LENGTH_LONG).show();
                                        dialog.dismiss();
                                        // TODO: take your actions!
                                    }
                                    // if user enters wrong password.
                                    catch (FirebaseAuthInvalidCredentialsException malformedEmail)
                                    {
//                                        Log.d(TAG, "onComplete: malformed_email");
                                        Toast.makeText(getApplicationContext(), "Invalid Credentials" , Toast.LENGTH_LONG).show();
                                        dialog.dismiss();
                                        // TODO: Take your action
                                    }
                                    catch (FirebaseAuthUserCollisionException existEmail)
                                    {
//                                        Log.d(TAG, "onComplete: exist_email");
                                        Toast.makeText(getApplicationContext(), "Email Id already exists" , Toast.LENGTH_LONG).show();
                                        dialog.dismiss();

                                        // TODO: Take your action
                                    }
                                    catch (Exception e)
                                    {
//                                        Log.d(TAG, "onComplete: " + e.getMessage());
                                        Toast.makeText(getApplicationContext(), "Something is Wrong" , Toast.LENGTH_LONG).show();
                                        dialog.dismiss();
                                    }
                                } else {
                                    dialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Signed Up" , Toast.LENGTH_LONG).show();
                                    intent.putExtra("Email", emailadd.getText().toString());
                                    intent.putExtra("Password", passw.getText().toString());
                                    startActivity(intent);
                                }
                            }
                        }
                );
    }

}
