package com.example.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Scholarvolley extends AppCompatActivity {
    ArrayList<ModelClass> scholarshipsList;
    RecyclerView recyclerView;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scholarvolley);
        RequestQueue queue = Volley.newRequestQueue(this);

        scholarshipsList = new ArrayList<>();
        String url = "http://10:3000/scholarships";

        // Request a JSON response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {

                    Log.d("response",response.toString());
                    // Parse the JSON response
                    try {
                        JSONArray scholarshipsArray = response.getJSONArray("scholarships");

                        for (int i = 0; i < scholarshipsArray.length(); i++) {
                            JSONObject scholarshipObject = scholarshipsArray.getJSONObject(i);

                            ModelClass scholarship = new ModelClass();
                            scholarship.setTextview1(scholarshipObject.getString("name"));
                            scholarship.setTextview2(scholarshipObject.getString("provider"));
                            scholarship.setTextview3(scholarshipObject.getString("start_date"));
                            scholarship.setTextview3(scholarshipObject.getString("end_date"));
                            scholarship.setTextView4(scholarshipObject.getString("amount"));
                            scholarship.setTextView5(scholarshipObject.getString("url"));
                            scholarship.setTextView6(scholarshipObject.getString("Eligibility"));
                            scholarship.setTextView7(scholarshipObject.getString("url"));

                            scholarshipsList.add(scholarship);
                        }

                        // Do something with the scholarshipsList
                        // ...

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    // Handle error
                    error.printStackTrace();
                });

        queue.add(jsonObjectRequest);





    }
    private void initRecyclerView() {

        Log.d("Size of array", String.valueOf(scholarshipsList.size()));
        recyclerView=findViewById(R.id.recyclerview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(Scholarvolley.this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new Adapter(Scholarvolley.this,scholarshipsList);
        recyclerView.setAdapter(adapter);

    }
}