package com.example.somee.musicplayer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.somee.musicplayer.Fragment.Fragmentdownload;
import com.example.somee.musicplayer.Fragment.Fragmentlistview;
import com.example.somee.musicplayer.Fragment.Fragmentsearchlistview;
import com.example.somee.musicplayer.Fragment.Homeactivity;

public class fragmentActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    FloatingActionButton floatid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainfragmant);
//        if(getSupportActionBar() != null){
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        floatid = (FloatingActionButton)findViewById(R.id.floatid);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);
        floatid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"this is test",Toast.LENGTH_SHORT).show();
            }
        });




    }

    Fragmentsearchlistview fragmentsearchlistview=new Fragmentsearchlistview();
    Fragmentlistview fragmentlistview=new Fragmentlistview();
    Fragmentdownload fragmentdownload=new Fragmentdownload();
    Homeactivity homeactivity= new Homeactivity();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, homeactivity).commit();
                return true;

            case R.id.search:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentsearchlistview).commit();
                return true;

            case R.id.download:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentdownload).commit();
                return true;




        }

        return false;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item != null && item.getItemId() == android.R.id.home) {
//            if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
//                drawerLayout.closeDrawer(Gravity.RIGHT);
//            }
//            else {
//                drawerLayout.openDrawer(Gravity.RIGHT);
//            }
//        }
        return super.onOptionsItemSelected(item);

    }
}
