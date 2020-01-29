package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    SharedPreferences share = null;
    EditText type ;
    @Override
    protected void onPause() {
        super.onPause();
        saveSharedPrefs(type.getText().toString());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       // setContentView(R.layout.activity_main);

        share = getSharedPreferences("file",MODE_PRIVATE);
        String saved = share.getString("Reserve name", "");
         type = findViewById(R.id.edit);
        type.setText(saved);


        Button login = findViewById(R.id.login);
        //login.setOnClickListener(click -> saveSharedPrefs(type.getText().toString()));
    login.setOnClickListener(click ->{
        Intent goToProfile = new Intent(MainActivity.this, ProfileActivity.class);

       // saveSharedPrefs(type.getText().toString());

      goToProfile.putExtra("E-mail",type.getText().toString());
          startActivity ( goToProfile );

    });

    }


    private void saveSharedPrefs(String save)
    {
        SharedPreferences.Editor editor = share.edit();
        editor.putString("Reserve name", save);
        editor.commit();
    }
    }
