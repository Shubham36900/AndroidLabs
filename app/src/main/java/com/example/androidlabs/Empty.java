package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Empty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
        //TextView id =findViewById(R.id.id1);
        Bundle dataToPass = getIntent().getExtras();
        DetailsFragment dFragment = new DetailsFragment(); //add a DetailFragment
        dFragment.setArguments( dataToPass );//pass it a bundle for information
        dFragment.setTablet(false);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentLocation, dFragment) //Add the fragment in FrameLayout
                .commit(); //actually load the fragment.
    }
}
