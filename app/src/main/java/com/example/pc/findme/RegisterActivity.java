package com.example.pc.findme;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText userName,password,password2,email,number,city;
    private Button btnRegister;
    private ProgressDialog progressDialog;
    private TextView textViewLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if(SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn())
        {
            finish();
            Intent intent = new Intent(RegisterActivity.this,HomeActivity.class);
            startActivity(intent);
        }

        userName = (EditText) findViewById(R.id.editTextUsername);
        password    = (EditText) findViewById(R.id.editTextPassword);
        password2 = (EditText) findViewById(R.id.editTextPassword2);
        city     = (EditText) findViewById(R.id.editTextCity);
        email     = (EditText) findViewById(R.id.editTextEmail);
        number   = (EditText) findViewById(R.id.editTextNumber);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering User ..");

        btnRegister.setOnClickListener(this);


    }

    private void registerUser()
    {
        final String _username = userName.getText().toString().trim();
        final String _password = password.getText().toString().trim();
        final String _password2= password2.getText().toString().trim();
        final String _email    = email.getText().toString().trim();
        final String _city     = city.getText().toString().trim();
        final String _number   = number.getText().toString().trim();

        if(!_password.equals(_password2))
        {
            Toast.makeText(this, "Password must be the same", Toast.LENGTH_LONG).show();
        }


        progressDialog.show();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if(!jsonObject.getBoolean("error")){
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(
                                        jsonObject.getString("id"),
                                        jsonObject.getString("username"),
                                        jsonObject.getString("email")

                                );
                                
                                Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_LONG).show();
//                                finish();
                                startActivity(new Intent(RegisterActivity.this,HomeActivity.class));

                            }else{
                                Toast.makeText(getApplicationContext(),jsonObject.getString("msg"),Toast.LENGTH_LONG).show();
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
                params.put("email", _email);
                params.put("city", _city);
                params.put("number", _number);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }

    @Override
    public void onClick(View view) {
        if(view == btnRegister){
            registerUser();
        }
    }
}
