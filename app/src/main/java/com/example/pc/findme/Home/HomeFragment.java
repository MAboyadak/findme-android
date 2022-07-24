package com.example.pc.findme.Home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.pc.findme.Constants;
import com.example.pc.findme.ListItem;
import com.example.pc.findme.MyAdapter;
import com.example.pc.findme.R;
import com.example.pc.findme.RequestHandler;
import com.example.pc.findme.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListItem> listItems;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewPosts);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listItems = new ArrayList<>();

        setHasOptionsMenu(true);
        loadRecyclerViewData();

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.top_nav,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.logout:
                SharedPrefManager.getInstance(getContext()).logOut();
                break;
            case R.id.profile:
                Toast.makeText(getContext(),"You clicked profile",Toast.LENGTH_LONG).show();
                break;
            case R.id.settings:
                Toast.makeText(getContext(),"You clicked Settings",Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }

    private void loadRecyclerViewData(){
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading..");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                Constants.POSTS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("posts");

                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject currentObject = jsonArray.getJSONObject(i);
                                ListItem listItem = new ListItem(
                                        currentObject.getInt("id"),
                                        currentObject.getString("fullName"),
                                        currentObject.getString("gender"),
                                        currentObject.getInt("age"),
                                        currentObject.getString("img1"),
                                        currentObject.getString("city"),
                                        currentObject.getString("personData"),
                                        currentObject.getString("username"),
                                        currentObject.getString("img"),
                                        currentObject.getString("time"),
                                        currentObject.getInt("number")


                                        );
                                listItems.add(listItem);
                            }

                            adapter = new MyAdapter(listItems,getContext());
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        );
        RequestHandler.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

}
