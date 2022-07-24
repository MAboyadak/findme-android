package com.example.pc.findme;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MatchingActivity extends AppCompatActivity {

    private TextView iedentifiedName;
    private ImageView iedentifiedImage;
    private TextView iedentifiedGender;
    private TextView iedentifiedCity;
    private TextView iedentifiedAge;
    private TextView iedentifiedDetails;
    private TextView iedentifiedUsername;
//    private ImageView iedentifiedUserImage;
    private CircleImageView iedentifiedUserImage;
    private TextView iedentifiedNumber;
    private ProgressDialog progressDialog;


    public TextView textViewAge;
    public ImageView imageViewPost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching);
        Intent i = getIntent();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Result ..");

        iedentifiedName = findViewById(R.id.identifiedName);
        iedentifiedImage = findViewById(R.id.identifiedImage);
        iedentifiedGender = findViewById(R.id.identifiedGender);
        iedentifiedCity = findViewById(R.id.identifiedCity);
        iedentifiedAge =  findViewById(R.id.identifiedAge);
        iedentifiedDetails = findViewById(R.id.identifiedDetails);
        iedentifiedUsername = findViewById(R.id.identifiedUsername);
        iedentifiedUserImage = findViewById(R.id.identifiedUserImage);
        iedentifiedNumber = findViewById(R.id.identifiedNumber);
        //The second parameter below is the default string returned if the value is not there.
        final String notifyId = i.getExtras().getString("notifyId");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.MATCHING_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            Log.i("zgzg", "["+response+"]");
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject myPerson = jsonObject.getJSONObject("identifiedPerson");

                            Log.i("myPerson", "["+myPerson+"]");

                            if(jsonObject.getBoolean("error") == false) {
                                String name = myPerson.getString("fullName");
                                String personimg = myPerson.getString("imgName");
                                String age = myPerson.getString("age");
                                String gender = myPerson.getString("gender");
                                String city = myPerson.getString("city");
                                String data = myPerson.getString("personData");
                                String number = myPerson.getString("number");
                                String username = myPerson.getString("username");
                                String userimg = myPerson.getString("img");

                                iedentifiedName.setText(name);
                                iedentifiedGender.setText(gender);
                                iedentifiedCity.setText(city);
                                iedentifiedAge.setText(age);
                                iedentifiedDetails.setText(data);
                                iedentifiedNumber.setText(number);
                                iedentifiedUsername.setText(username);

                                Picasso.with(getApplicationContext())
                                .load(userimg)
                                .error(android.R.drawable.stat_notify_error)
                                .into(iedentifiedUserImage);

                                Picasso.with(getApplicationContext())
                                .load(personimg)
                                .error(android.R.drawable.stat_notify_error)
                                .into(iedentifiedImage);

                                progressDialog.dismiss();
                            }
                            else{
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_LONG).show();
                            }
                            } catch (JSONException e1) {
                            e1.printStackTrace();
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
                params.put("notifyId", notifyId);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}
