package com.example.pc.findme.Home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.pc.findme.Constants;
import com.example.pc.findme.MyAdapter_Notifications;
import com.example.pc.findme.NotificationItem;
import com.example.pc.findme.R;
import com.example.pc.findme.RequestHandler;
import com.example.pc.findme.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationsFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<NotificationItem> listItems;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_notifications,container,false);
        View rootView = inflater.inflate(R.layout.fragment_notifications, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewNotifications);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listItems = new ArrayList<NotificationItem>();

        loadRecyclerViewData();

        return rootView;
    }

    private void loadRecyclerViewData() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading..");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.NOTIFICATIONS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            Log.i("zgzg", "["+response+"]");
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("notifications");

                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject currentObject = jsonArray.getJSONObject(i);
                                NotificationItem listItem = new NotificationItem(
                                        currentObject.getString("id"),
                                        currentObject.getString("type"),
                                        currentObject.getString("message"),
                                        currentObject.getString("time")
                                );
                                listItems.add(listItem);
                            }

                            adapter = new MyAdapter_Notifications(listItems,getContext());
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
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userId",SharedPrefManager.getInstance(getContext()).getUserId());
                return params;
            }
        };
        RequestHandler.getInstance(getContext()).addToRequestQueue(stringRequest);
    }
}

