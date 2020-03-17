package com.example.androidlabs;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class Toolbar extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation);
        //This gets the toolbar from the layout:
        androidx.appcompat.widget.Toolbar tBar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);

        //This loads the toolbar, which calls onCreateOptionsMenu below:
        setSupportActionBar(tBar);


        //For NavigationDrawer:
        DrawerLayout drawer = findViewById(R.id.dd);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, tBar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      String text ="";
      switch(item.getItemId()){
          case R.id.sport:
              text = "You clicked on the first Item";
              break;
          case R.id.tiger:
              text = "You clicked on the second Item";
              break;
          case R.id.item1:
              text = "You clicked on the third item";
              break;
          default:
              text = "You clicked on th overflow menu";
      }
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
      return true;
    }
    public boolean onNavigationItemSelected( MenuItem item) {

        switch(item.getItemId()){
            case R.id.chatv:
                Intent it = new Intent(this,ChatRoomActivity.class);
                startActivity(it);
                break;
            case R.id.Weatherd:
                Intent its = new Intent(this,WeatherForecast.class);
                startActivity(its);
                break;
            case R.id.backs:
                Intent ss = new Intent(this,ProfileActivity.class);
                setResult(500,ss);
               finish();
                break;


        }



        return false;
    }
}
