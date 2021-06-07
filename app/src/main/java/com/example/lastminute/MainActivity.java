package com.example.lastminute;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.cachapa.expandablelayout.ExpandableLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import it.mirko.rangeseekbar.OnRangeSeekBarListener;
import it.mirko.rangeseekbar.RangeSeekBar;

public class MainActivity extends AppCompatActivity implements OnRangeSeekBarListener {
    ListView list;
    JSONArray jsonArray;
    MyListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton ib_order = findViewById(R.id.ib_order);
        ImageButton ib_filter = findViewById(R.id.ib_filter);
        ExpandableLayout expandableLayout =
                findViewById(R.id.expandable_layout);
        ExpandableLayout expandableLayout_filter =
                findViewById(R.id.expandable_layout_filter);

        list = findViewById(R.id.list);

        ib_filter.setOnClickListener(view -> {
            if (expandableLayout_filter.isExpanded()) {
                expandableLayout_filter.collapse();
            } else {
                expandableLayout_filter.expand();
            }
        });

        ib_order.setOnClickListener(view -> {
            if (expandableLayout.isExpanded()) {
                expandableLayout.collapse();
            } else {
                expandableLayout.expand();
            }
        });

        RangeSeekBar rangeSeekBar = findViewById(R.id.rangeSeekBar);
        rangeSeekBar.setEndProgress(500);
        rangeSeekBar.setMax(500);
        rangeSeekBar.setRangeColor(Color.BLUE);
        rangeSeekBar.setTrackColor(Color.LTGRAY);
        rangeSeekBar.setOnRangeSeekBarListener(MainActivity.this);

        TextView tv_user_rating = findViewById(R.id.tv_ratings);
        tv_user_rating.setOnClickListener(view -> {
            try {
                handlingHotels(getSortedList(jsonArray, "userRating", false));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        TextView tv_price_asc = findViewById(R.id.tv_price_asc);
        tv_price_asc.setOnClickListener(view -> {
            try {
                handlingHotels(getSortedList(jsonArray, "price", true));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        TextView tv_price_desc = findViewById(R.id.tv_price_desc);
        tv_price_desc.setOnClickListener(view -> {
            try {
                handlingHotels(getSortedList(jsonArray, "price", false));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        TextView tv_stars = findViewById(R.id.tv_stars);
        tv_stars.setOnClickListener(view -> {
            try {
                handlingHotels(getSortedList(jsonArray, "stars", false));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://run.mocky.io/v3/eef3c24d-5bfd-4881-9af7-0b404ce09507";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        jsonArray = new JSONArray(response);
                        handlingHotels(jsonArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
        });
        queue.add(stringRequest);
    }

    private void handlingHotels(JSONArray jsonArray) throws JSONException {
        String[] ids = new String[jsonArray.length()];
        String[] names = new String[jsonArray.length()];
        JSONObject[] locations = new JSONObject[jsonArray.length()];
        int[] stars = new int[jsonArray.length()];
        JSONObject[] checkIns = new JSONObject[jsonArray.length()];
        JSONObject[] checkOuts = new JSONObject[jsonArray.length()];
        JSONObject[] contacts = new JSONObject[jsonArray.length()];
        int[] prices = new int[jsonArray.length()];
        String[] currencies = new String[jsonArray.length()];

        String[] ratings = new String[jsonArray.length()];
        JSONArray[] images = new JSONArray[10];

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject hotel = jsonArray.getJSONObject(i);
            names[i] = hotel.getString("name");
            locations[i] =
                    hotel.getJSONObject(
                            "location");
            stars[i] = hotel.getInt("stars");
            checkIns[i] = hotel.getJSONObject(
                    "checkIn");
            checkOuts[i] = hotel.getJSONObject(
                    "checkOut");
            contacts[i] = hotel.getJSONObject(
                    "contact");
            images[i] = hotel.getJSONArray("gallery");
            ratings[i] = hotel.getString("userRating");
            prices[i] = hotel.getInt("price");
            currencies[i] = hotel.getString("currency");
        }

        adapter = new MyListAdapter(MainActivity.this, ids, names, locations, images,
                ratings, prices, currencies, stars, checkIns, checkOuts, contacts);

        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public static JSONArray getSortedList(JSONArray array, String type,
                                          boolean asc) throws JSONException {
        List<JSONObject> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            list.add(array.getJSONObject(i));
        }
        list.sort(new Sort(type, asc));
        return new JSONArray(list);
    }

    @Override
    public void onRangeValues(RangeSeekBar rangeSeekBar, int start, int end) {
        TextView tv_seek_end = findViewById(R.id.tv_seek_end);
        TextView tv_seek_start = findViewById(R.id.tv_seek_start);
        tv_seek_start.setText(String.valueOf(start)); // example using start value
        tv_seek_end.setText(String.valueOf(end)); // example using end value

        try {
            List<JSONObject> list = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                if (object.getInt("price") > start && object.getInt("price") < end) {
                    list.add(object);
                }
            }
            JSONArray filtered = new JSONArray(list);
            handlingHotels(filtered);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static class Sort implements Comparator<JSONObject> {
        String type;
        boolean asc;

        public Sort(String type, boolean asc) {
            this.type = type;
            this.asc = asc;
        }

        @Override
        public int compare(JSONObject l, JSONObject r) {
            try {
                if (asc)
                    return Integer.compare(l
                            .getInt(type), r.getInt(type));
                else
                    return Integer.compare(r
                            .getInt(type), l.getInt(type));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return 0;

        }
    }
}