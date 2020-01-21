package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main_linear);

        EditText theEdit = findViewById(R.id.edit);
        CheckBox cb = findViewById(R.id.checkBox);
        Button btn = findViewById(R.id.button2);
        Switch swtch = findViewById(R.id.switchbtn);
        btn.setOnClickListener( cd -> {
            Toast.makeText(MainActivity.this, getResources().getString(R.string.toast_message) , Toast.LENGTH_LONG).show();
        });
        swtch.setOnCheckedChangeListener( (compoundButton , b) -> {

               String message;
                if(b==true) {
                    //  Toast.makeText(MainActivity.this, "Checkbox is " + b, Toast.LENGTH_LONG).show();
                    Snackbar.make(theEdit, "The switch is now ON  " , Snackbar.LENGTH_LONG)
                     .setAction("Undo", click-> compoundButton.setChecked( !b ))
                            .show();

                }else
                    Snackbar.make(theEdit, "The switch is now Off  " , Snackbar.LENGTH_LONG)
                            .setAction("Undo", click-> compoundButton.setChecked( !b ))
                            .show();


            });
        cb.setOnCheckedChangeListener( (compoundButton , b) -> {

            String message;
            if(b==true) {
                //  Toast.makeText(MainActivity.this, "Checkbox is " + b, Toast.LENGTH_LONG).show();
                Snackbar.make(theEdit, "The switch is now ON  " , Snackbar.LENGTH_LONG)
                        .setAction("Undo", click-> compoundButton.setChecked( !b ))
                        .show();

            }else
                Snackbar.make(theEdit, "The switch is now Off  " , Snackbar.LENGTH_LONG)
                        .setAction("Undo", click-> compoundButton.setChecked( !b ))
                        .show();


        });

    }
}