package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageButton mImageButton ;

    public static final String ACTIVITY_NAME ="Profile Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        EditText emails = findViewById(R.id.email);
        Intent fromMain = getIntent();
        emails.setText(fromMain.getStringExtra("E-mail"));
       // String ss = fromMain.getStringExtra("Email");
        mImageButton = findViewById(R.id.imageButton);
       // emails.setText(ss);

       mImageButton.setOnClickListener(click ->
        {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

            }
        });
        Button chats = findViewById(R.id.chat);
        chats.setOnClickListener(clik -> {
            Intent lab4 = new Intent(ProfileActivity.this, ChatRoomActivity.class);
            startActivity (lab4);
        });
        Button weather = findViewById(R.id.weather);
        weather.setOnClickListener(clik -> {
            Intent lab6 = new Intent(ProfileActivity.this, WeatherForecast.class);
            startActivity (lab6);
        });
        Log.e(ACTIVITY_NAME,"In function : OnCreates()") ;

    }







    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageButton.setImageBitmap(imageBitmap);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(ACTIVITY_NAME,"In function : OnResume()") ;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(ACTIVITY_NAME,"In function : OnStart()") ;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(ACTIVITY_NAME,"In function : OnDestroy()") ;
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(ACTIVITY_NAME,"In function : OnStop()") ;
    }
}
