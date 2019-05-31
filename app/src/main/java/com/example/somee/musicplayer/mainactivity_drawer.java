package com.example.somee.musicplayer;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.somee.musicplayer.Fragment.FragmentAboutme;
import com.example.somee.musicplayer.Fragment.FragmentRegister;
import com.example.somee.musicplayer.Fragment.Fragmentdownload;
import com.example.somee.musicplayer.Fragment.Fragmentlistview;
import com.example.somee.musicplayer.Fragment.Fragmentsearchlistview;
import com.example.somee.musicplayer.Fragment.Fragmentsendcommentuser;
import com.example.somee.musicplayer.Fragment.Homeactivity;
import com.example.somee.musicplayer.R;

public class mainactivity_drawer extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener  {

    private NavigationView navigationView;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton floatid;


    DrawerLayout drawerLayout;
    ActionBarDrawerToggle mytoggle;
    Toolbar mToolbar;


    Fragmentsearchlistview fragmentsearchlistview=new Fragmentsearchlistview();
    Fragmentlistview fragmentlistview=new Fragmentlistview();
    Fragmentdownload fragmentdownload=new Fragmentdownload();
    Homeactivity homeactivity= new Homeactivity();

    FragmentRegister fragmentregister=new FragmentRegister();
    FragmentAboutme fragmentAboutme =new FragmentAboutme();
    Fragmentsendcommentuser fragmentsendcommentuser =new Fragmentsendcommentuser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawerablelayout);
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
        drawerLayout=(DrawerLayout)findViewById(R.id.mydrawer);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        mytoggle =new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(mytoggle);
        mytoggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setUpNavigationView();
        navigationView.getBackground().setColorFilter(0x80000000, PorterDuff.Mode.OVERLAY);
//#FF9800

    }

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

        if (item != null && item.getItemId() == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                drawerLayout.closeDrawer(Gravity.RIGHT);
            }
            else {
                drawerLayout.openDrawer(Gravity.RIGHT);
            }
        }
        return super.onOptionsItemSelected(item);
    }
    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.draw_main:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeactivity).commit();
                        bottomNavigationView.setVisibility(View.VISIBLE);
                        floatid.show();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.draw_comment:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentsendcommentuser).commit();
                        bottomNavigationView.setVisibility(View.INVISIBLE);
                        floatid.hide();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.draw_registar:

                         getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentregister).commit();
                        bottomNavigationView.setVisibility(View.INVISIBLE);
                        floatid.hide();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.draw_aboutme:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentAboutme).commit();
                        bottomNavigationView.setVisibility(View.INVISIBLE);
                        floatid.hide();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.draw_exit:
                        finish();
                        System.exit(0);
                        break;

                    default:
                }


                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

//                loadHomeFragment();

                return true;
            }
        });}

}


