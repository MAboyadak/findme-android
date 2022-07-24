package com.example.pc.findme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.pc.findme.Home.HomeActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(!SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn())
        {
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(MainActivity.this,HomeActivity.class);
            startActivity(intent);
            finish();
        }

    }
}
