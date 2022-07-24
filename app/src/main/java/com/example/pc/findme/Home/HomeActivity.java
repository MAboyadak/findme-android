package com.example.pc.findme.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.pc.findme.LoginActivity;
import com.example.pc.findme.Post.PostActivity;
import com.example.pc.findme.R;
import com.example.pc.findme.SharedPrefManager;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if(!SharedPrefManager.getInstance(this).isLoggedIn())
        {
            finish();
            Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
            startActivity(intent);
        }



        /** Get The Bottom Nav to response to each item click **/
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);         /** Set Home icon Selected by default **/
        bottomNavigationView.setOnNavigationItemSelectedListener(navClickListner);          /** Set Nav item Selected Listner  **/
        /** Set HomeFragment Selected by default **/
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();

    }



    private BottomNavigationView.OnNavigationItemSelectedListener navClickListner =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_notification:
                            selectedFragment = new NotificationsFragment();
                            break;
//                        case R.id.nav_profile:
//                            selectedFragment = null;
//                            startActivity(new Intent(HomeActivity.this,ProfileActivity.class));
//                            break;
                        case R.id.nav_post:
                            selectedFragment = null;
                            startActivity(new Intent(HomeActivity.this,PostActivity.class));
                            break;
                    }
                    if(selectedFragment == null){
                        return false;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                    return true;
                }
            };
}
