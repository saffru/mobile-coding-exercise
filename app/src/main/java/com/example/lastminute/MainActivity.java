package com.example.lastminute;

import androidx.appcompat.app.AppCompatActivity;

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
                        String[] titles = new String[jsonArray.length()];
                        String[] subtitles = new String[jsonArray.length()];
                        String[] ratings = new String[jsonArray.length()];
                        String[] prices = new String[jsonArray.length()];
                        String[] currencies = new String[jsonArray.length()];
                        int[] stars = new int[jsonArray.length()];
                        String[] images = new String[jsonArray.length()];

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject hotel = jsonArray.getJSONObject(i);
                            titles[i] = hotel.getString("name");
                            ratings[i] = hotel.getString("userRating");
                            prices[i] = hotel.getString("price");
                            currencies[i] = hotel.getString("currency");
                            images[i] = hotel.getJSONArray("gallery").getString(0);
                            JSONObject location = hotel.getJSONObject(
                                    "location");
                            subtitles[i] =
                                    location.getString("address") + ", " + location.getString("city");
                            stars[i] = hotel.getInt("stars");
                        }
                        MyListAdapter adapter =
                                new MyListAdapter(MainActivity.this,
                                        titles, subtitles, images,
                                        ratings, prices, currencies,
                                        stars);
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