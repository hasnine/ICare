package com.newfeds.icare.activities;


import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


import com.newfeds.icare.R;
import com.newfeds.icare.fragments.DoctorsFragment;
import com.newfeds.icare.fragments.HomeFragment;
import com.newfeds.icare.fragments.HospitalsFragment;
import com.newfeds.icare.fragments.MembersFragment;
import com.newfeds.icare.fragments.MyProfileFragment;
import com.newfeds.icare.fragments.SettingsFragment;
import com.newfeds.icare.helper.DBhelper;
import com.newfeds.icare.helper.L;
import com.newfeds.icare.model.Profile;

public class Dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ImageView imageViewNavHeader;
    TextView textViewNavHeaderName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);

        imageViewNavHeader = (ImageView) header.findViewById(R.id.imageViewNavHeader);
        textViewNavHeaderName = (TextView) header.findViewById(R.id.textViewNavHeaderName);
        try {
            DBhelper dBhelper = new DBhelper(this);

            Profile myprofile = dBhelper.getMyProfileMain();

            textViewNavHeaderName.setText(myprofile.NAME);

            L.log("photo:" + myprofile.PHOTO);
            L.log(myprofile.NAME);
            L.log(myprofile.AGE);
            L.log(myprofile.GENDER);
        }catch (Exception e){
            L.log(e.getMessage());
        }


        navigationView.getMenu().getItem(0).setChecked(true);
        Fragment fragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
        L.log("home should call");
    }

    @Override
    protected void onResume() {
        super.onResume();
        L.log("Resuming Dashboard");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        Fragment fragment = null;

        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            fragment = new HomeFragment();
        } else if (id == R.id.nav_members) {
            fragment = new MembersFragment();
        } else if(id == R.id.nav_doctors){
            fragment = new DoctorsFragment();
        } else if (id == R.id.nav_my_profile){
            fragment = new MyProfileFragment();
        } else if (id == R.id.nav_settings){
            fragment = new SettingsFragment();
        } else if(id == R.id.nav_hospitals){
            fragment = new HospitalsFragment();
        }

        if(fragment!=null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_container,fragment).commit();

        }else{
            L.log("Error creating fragment");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
