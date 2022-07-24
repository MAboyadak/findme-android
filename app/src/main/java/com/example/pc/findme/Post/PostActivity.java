package com.example.pc.findme.Post;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.pc.findme.Constants;
import com.example.pc.findme.Home.HomeActivity;
import com.example.pc.findme.R;
import com.example.pc.findme.RequestHandler;
import com.example.pc.findme.SectionsPageAdapter;
import com.example.pc.findme.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PostActivity extends AppCompatActivity implements View.OnClickListener{

    private SectionsPageAdapter sectionsPageAdapter;
    private ViewPager viewPager;
    private Button uploadBtn,selectBtn;
    private EditText firstname,fatername,lastname,age,city,persondata;
    private RadioGroup radioGroup;
    private ImageView imgView;
    private final int IMG_REQUEST = 1;
    private Bitmap bitmap;
    private RadioButton radioButton;
    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        /** Get The Bottom Nav to response to each item click **/
//        TabLayout topTabs =(TabLayout) findViewById(R.id.top_navigation);
//        topTabs.().findItem(R.id.post_found).setChecked(true);             /** Set Post found Title Selected by default **/
//        topTabs.setOnNavigationItemSelectedListener(tapClickListner);     /** Set Nav item Selected Listner  **/
        /** Set Found Fragment Selected by default **/
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving person, please wait..");
        uploadBtn = (Button) findViewById(R.id.uploadBtn);
        selectBtn = (Button) findViewById(R.id.selectBtn);
        imgView   = (ImageView) findViewById(R.id.imageView);
        firstname=findViewById(R.id.editTextFirstName);
        fatername=findViewById(R.id.editTextFatherName);
        lastname=findViewById(R.id.editTextLastName);
        city=findViewById(R.id.editTextCity);
        age=findViewById(R.id.editTextAge);
        persondata=findViewById(R.id.editTextDetails);

        // find the radiobutton by returned id
        radioGroup=findViewById(R.id.radioGroup);



        selectBtn.setOnClickListener(this);
        uploadBtn.setOnClickListener(this);

//        sectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
//        viewPager = (ViewPager)findViewById(R.id.container);
//        setupViewPager(viewPager);

//        TabLayout tabLayout = (TabLayout) findViewById(R.id.top_navigation);
//        tabLayout.setupWithViewPager(viewPager);
//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new FoundFragment()).commit();
    }

    public void onClick(View v){
        switch(v.getId())
        {
            case R.id.selectBtn:
                selectImage();
                break;

        }
        switch(v.getId())
        {
            case R.id.uploadBtn:
                progressDialog.show();
                uploadImage();
                break;

        }
    }

    private void selectImage()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMG_REQUEST);

    }

    private void uploadImage()
    {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selectedId);
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.ADD_PERSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            Log.i("zgzg", "["+response+"]");
                            JSONObject jsonObject = new JSONObject(response);

                                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
//                                finish();
                                startActivity(new Intent(PostActivity.this,HomeActivity.class));

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            Toast.makeText(getApplicationContext(), "exception", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"error responses",Toast.LENGTH_LONG).show();
                        if (error instanceof TimeoutError) {
                            Toast.makeText(getApplicationContext(), "TimeoutError", Toast.LENGTH_LONG).show();

                        }else if(error instanceof NoConnectionError){
                            Toast.makeText(getApplicationContext(), "No connection", Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(getApplicationContext(), "AuthFailureError", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(getApplicationContext(), "ServerError", Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError) {
                            Toast.makeText(getApplicationContext(), "NetworkError", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(getApplicationContext(), "ParseError", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(), " ", Toast.LENGTH_LONG).show();
                        }
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("firstname",firstname.getText().toString().trim());
                params.put("fathername",fatername.getText().toString().trim());
                params.put("lastname",lastname.getText().toString().trim());
                params.put("age",age.getText().toString().trim());
                params.put("city",city.getText().toString().trim());
                params.put("gender","male");
                params.put("personData",persondata.getText().toString().trim());
                params.put("group",radioButton.getText().toString());
                params.put("image",imgToString(bitmap));
                params.put("userId",SharedPrefManager.getInstance(getApplicationContext()).getUserId()); // get the id of the logged user from shared preferneces
//                params.put("imageName",UUID.randomUUID().toString());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(100000,
                                                            0,
                                                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    private String imgToString(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes,Base64.DEFAULT);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMG_REQUEST && resultCode==RESULT_OK && data != null )
        {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
//                imgView.setImageBitmap(bitmap);
//                imgView.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

//    private void setupViewPager(ViewPager viewPager){
//        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
//        adapter.addFragment(new FoundFragment(),"Found Person");
//        adapter.addFragment(new LostFragment(),"Lost Person");
//        viewPager.setAdapter(adapter);
//    }



//    private BottomNavigationView.OnNavigationItemSelectedListener tapClickListner =
//            new BottomNavigationView.OnNavigationItemSelectedListener() {
//                @Override
//                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                    switch (menuItem.getItemId()){
//                        case R.id.post_lost :
//                            getSupportFragmentManager().beginTransaction()
//               s                     .replace(R.id.fragment_container,new LostFragment()).commit();
//                            break;
//
//                        case R.id.post_found :
//                            getSupportFragmentManager().beginTransaction()
//                                    .replace(R.id.fragment_container,new FoundFragment()).commit();
//                            break;
//                    }
//                    return true;
//                }
//            };
}
