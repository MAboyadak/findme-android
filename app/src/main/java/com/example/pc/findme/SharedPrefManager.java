package com.example.pc.findme;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private static SharedPrefManager mInstance;
    private static Context ctx;

    //constants
    private static final String SHARED_PREF_NAME = "mysharedpref1";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_USER_EMAIL = "email";
    private static final String KEY_USER_ID = "id";

    private SharedPrefManager(Context context)
    {
        ctx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context)
    {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }
    public static String getUserId() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_ID,null);
    }

    public boolean userLogin(String id, String username, String email)
    {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_USER_ID, id);
        editor.putString(KEY_USERNAME,username);
        editor.putString(KEY_USER_EMAIL,email);

        editor.apply();

        return true;
    }

    public boolean isLoggedIn()
    {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_USERNAME,null) != null)
        {
            return true;
        }
        return false;
    }

    public boolean logOut()
    {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

}