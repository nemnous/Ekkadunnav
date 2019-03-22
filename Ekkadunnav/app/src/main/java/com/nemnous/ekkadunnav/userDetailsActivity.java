package com.nemnous.ekkadunnav;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class userDetailsActivity extends AppCompatActivity {


    String Email;
    String Password;
    EditText name;
    Uri resultUri;
    ImageView circleImageView;
    private static final String TAG = "MyApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        circleImageView = findViewById(R.id.circularID);
        name = (EditText) findViewById(R.id.nameId);
        setContentView(R.layout.activity_user_details);
        Intent intent = getIntent();
        if (intent != null) {
            this.Email = intent.getStringExtra("Email");
            this.Password = intent.getStringExtra("Password");
        }
    }

    public void selectImage(View v) {
        Toast.makeText(getApplicationContext(), "On construction!" , Toast.LENGTH_LONG).show();
//        if(true)
//            return;
//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        intent.setType("image/*");
////        startActivityForResult(intent, 12);
//        Log.d(TAG, "hell");
//        startActivityForResult(intent, 100);
//        circleImageView.setImageURI(resultUri);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        Log.d(TAG, "yooooo");
//        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
//            Uri selectedImage = data.getData();
////           circleImageView.setImageURI(selectedImage);
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
//                circleImageView.setImageBitmap(bitmap);
//            }
//            catch (IOException e)
//            {
//                e.printStackTrace();
//            }
////            CropImage.activity()
////                    .setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1, 1)
////                    .start(this);
//
//        }
//
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (resultCode == RESULT_OK) {
//                try {
//                    resultUri = result.getUri();
//                    circleImageView.setImageURI(result.getUri());
////                    Log.d(TAG, );
////                    circleImageView.setImageURI(result.getUri());
//                } catch (Exception e) {
//                    Log.e(TAG, "Received an exception", e);
//                }
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Exception error = result.getError();
//            }
//        }
//    }

    public void nextButton(View view) {
        Intent intent = new Intent(this, GenerateCodeActivity.class);
        Toast.makeText(getApplicationContext(), "NextButton Clicked" , Toast.LENGTH_LONG).show();
        intent.putExtra("Email", this.Email);
        intent.putExtra("Password", this.Password);
        intent.putExtra("Name", name.getText().toString());

        startActivity(intent);
    }








}



