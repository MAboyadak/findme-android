package com.example.pc.findme.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.pc.findme.Home.HomeActivity;
import com.example.pc.findme.Home.NotificationsFragment;
import com.example.pc.findme.Post.PostActivity;
import com.example.pc.findme.R;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
//        bottomNavigationView.getMenu().findItem(R.id.nav_profile).setChecked(true); /** Set Profile icon Selected by default **/
        bottomNavigationView.setOnNavigationItemSelectedListener(navClickListner);
        /** Set ProfileFragment Selected by default **/
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ProfileFragment()).commit();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navClickListner =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = null;
                            finish();
                            startActivity(new Intent(ProfileActivity.this,HomeActivity.class));
                            break;
                        case R.id.nav_notification:
                            selectedFragment = new NotificationsFragment();
                            break;
//                        case R.id.nav_profile:
//                            selectedFragment = new ProfileFragment();
//                            break;
                        case R.id.nav_post:
                            selectedFragment = null;
                            finish();
                            startActivity(new Intent(ProfileActivity.this,PostActivity.class));
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
