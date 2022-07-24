package com.example.pc.findme;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.pc.findme.Home.HomeActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextUsername,editTextPassword;
    private Button btnLogin,btnRegister;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn())
        {
            finish();
            Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
            startActivity(intent);
        }


        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        progressDialog = new ProgressDialog(this);

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

    }

    private void userLogin()
    {
        final String _username = editTextUsername.getText().toString().trim();
        final String _password = editTextPassword.getText().toString().trim();

        progressDialog.show();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if(jsonObject.getBoolean("error") == false){
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(
                                        jsonObject.getString("id"),
                                        jsonObject.getString("username"),
                                        jsonObject.getString("email")
                                );

                                Toast.makeText(getApplicationContext(),jsonObject.getString("msg"),Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(new Intent(LoginActivity.this,HomeActivity.class));

                            }else{
                                Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", _username);
                params.put("password", _password);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
    @Override
    public void onClick(View view)
    {
        if(view == btnLogin){
            userLogin();
        }
        if(view == btnRegister){
            startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
        }
    }


}
