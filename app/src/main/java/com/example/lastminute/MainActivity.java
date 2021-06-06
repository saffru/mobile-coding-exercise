package com.example.lastminute;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://run.mocky.io/v3/eef3c24d-5bfd-4881-9af7-0b404ce09507";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        String[] ids = new String[jsonArray.length()];
                        String[] names = new String[jsonArray.length()];
                        String[] locations = new String[jsonArray.length()];
                        int[] stars = new int[jsonArray.length()];
                        JSONObject checkIns = new JSONObject();
                        JSONObject checkOuts = new JSONObject();
                        JSONObject contacts = new JSONObject();
                        int[] prices = new int[jsonArray.length()];
                        String[] currencies = new String[jsonArray.length()];

                        String[] ratings = new String[jsonArray.length()];
                        JSONArray ss []= new JSONArray[10];

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject hotel = jsonArray.getJSONObject(i);
                            names[i] = hotel.getString("name");
                            JSONObject location = hotel.getJSONObject(
                                    "location");
                            locations[i] =
                                    location.getString("address") + ", " + location.getString("city");
                            stars[i] = hotel.getInt("stars");
                            checkIns = hotel.getJSONObject(
                                    "checkIn");
                            checkOuts = hotel.getJSONObject(
                                    "checkOut");
                            contacts = hotel.getJSONObject(
                                    "contact");
                            ss[i] = hotel.getJSONArray("gallery");
                            ratings[i] = hotel.getString("userRating");
                            prices[i] = hotel.getInt("price");
                            currencies[i] = hotel.getString("currency");
                        }

                        MyListAdapter adapter =
                                new MyListAdapter(MainActivity.this, ids,
                                        names, locations, ss,
                                        ratings, prices, currencies,
                                        stars, checkIns, checkOuts, contacts);
                        list = findViewById(R.id.list);
                        list.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // Display the first 500 characters of the response string.
                    //textView.setText("Response is: "+ response.substring(0,500));
                }, error -> {
                });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}