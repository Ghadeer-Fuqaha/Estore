package com.example.exam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.widget.Toolbar;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.drm.DrmStore;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.example.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivity extends AppCompatActivity{

    private DrawerLayout drawer;
    FragmentTransaction mFragmentTransaction = getSupportFragmentManager().beginTransaction();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //if the user is not logged in
        //starting the login activity
        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, login.class));
        }



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.user_name_nav);
        TextView navEmail = (TextView) headerView.findViewById(R.id.Email_nav);
        TextView navPhone = (TextView) headerView.findViewById(R.id.Phone_nav);

        //getting the current user
        User user = SharedPrefManager.getInstance(this).getUser();


        navUsername.setText(user.getName());
        navEmail.setText(user.getEmail());
        navPhone.setText(user.getPhone());
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){

                    case R.id.nav_fav:
                        // Begin the transaction
                        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
                        // Replace the contents of the container with the new fragment
                        mFragmentTransaction.replace(R.id.your_placeholder, new FavoriteFragment());
                        mFragmentTransaction.addToBackStack(null);
                        // Complete the changes added above
                        mFragmentTransaction.commit();
                        break;

                    case R.id.nav_cart:
                        // Begin the transaction
                        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
                        // Replace the contents of the container with the new fragment
                        mFragmentTransaction.replace(R.id.your_placeholder, new CartFragment());
                        mFragmentTransaction.addToBackStack(null);
                        // Complete the changes added above
                        mFragmentTransaction.commit();
                        break;

                    case R.id.nav_logout:
                        finish();
                        SharedPrefManager.getInstance(getApplicationContext()).logout();
                        break;

                }

                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //loading the default fragment
       loadFragment(new HomeFragment(), savedInstanceState);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId()) {
                    case R.id.home_navigation:

                        // Begin the transaction
                        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
                        // Replace the contents of the container with the new fragment
                        mFragmentTransaction.replace(R.id.your_placeholder, new HomeFragment());
                        mFragmentTransaction.addToBackStack(null);
                        // Complete the changes added above
                        mFragmentTransaction.commit();

                        break;
                    case R.id.smartphone_navigation:

                        // Begin the transaction
                        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
                        // Replace the contents of the container with the new fragment
                        mFragmentTransaction.replace(R.id.your_placeholder, new SmartphoneFragment());
                        mFragmentTransaction.addToBackStack(null);
                        // Complete the changes added above
                        mFragmentTransaction.commit();

                        break;

                    case R.id.laptop_navigation:

                        // Begin the transaction
                        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
                        // Replace the contents of the container with the new fragment
                        mFragmentTransaction.replace(R.id.your_placeholder, new LaptopFragment());
                        mFragmentTransaction.addToBackStack(null);
                        // Complete the changes added above
                        mFragmentTransaction.commit();

                        break;

                    case R.id.computer_navigation:

                        // Begin the transaction
                        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
                        // Replace the contents of the container with the new fragment
                        mFragmentTransaction.replace(R.id.your_placeholder, new ComputerFragment());
                        mFragmentTransaction.addToBackStack(null);
                        // Complete the changes added above
                        mFragmentTransaction.commit();

                        break;
                }

                return true;
            }
        });


    }//End of onCreate method


    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{

            super.onBackPressed();
        }
        moveTaskToBack(true);
    }

    private boolean loadFragment(Fragment fragment, Bundle savedInstanceState) {
        //switching fragment
        if (fragment != null && savedInstanceState == null) {

            // Replace the contents of the container with the new fragment
            mFragmentTransaction.replace(R.id.your_placeholder, new HomeFragment());
            // Complete the changes added above
            mFragmentTransaction.commit();
            return true;
        }
        return false;
    }

}//End of Class

