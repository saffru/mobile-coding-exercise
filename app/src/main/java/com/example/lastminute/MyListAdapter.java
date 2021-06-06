package com.example.lastminute;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class MyListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] id, maintitle, subtitle, ratings, currencies;
    private final int[] stars, prices;
    private final JSONObject checkIn, checkOut, contact;
    private final JSONArray[] images;


    public MyListAdapter(Activity context, String[] id, String[] maintitle,
                         String[] subtitle, JSONArray[] images, String[] ratings,
                         int[] prices, String[] currencies, int[] stars,
                         JSONObject checkIn, JSONObject checkOut, JSONObject contact) {
        super(context, R.layout.mylist, maintitle);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.id = id;
        this.maintitle = maintitle;
        this.subtitle = subtitle;
        this.images = images;
        this.ratings = ratings;
        this.prices = prices;
        this.currencies = currencies;
        this.stars = stars;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.contact = contact;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.mylist, null, true);

        CardView card = rowView.findViewById(R.id.card);

        TextView titleText = rowView.findViewById(R.id.tv_title);
        TextView subtitleText = rowView.findViewById(R.id.tv_subtitle);
        TextView tv_price = rowView.findViewById(R.id.tv_price);
        ImageView imageView = rowView.findViewById(R.id.icon);
        TextView tv_ratings = rowView.findViewById(R.id.tv_rating);
        TextView tv_currency = rowView.findViewById(R.id.tv_currency);
        LinearLayout ll_stars = rowView.findViewById(R.id.ll_stars);
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(32, 32);

        titleText.setText(maintitle[position]);
        try {
            Picasso.get().load(images[position].getString(0)).into(imageView);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        subtitleText.setText(subtitle[position]);
        tv_ratings.setText(ratings[position]);
        tv_price.setText(String.valueOf(prices[position]));
        String currency;
        switch (currencies[position]) {
            case "USD":
            case "AUD":
                currency = "$";
                break;
            case "GBP":
                currency = "£";
                break;
            default:
                currency = "€";
        }
        tv_currency.setText(currency);

        for (int x = 0; x < stars[position]; x++) {
            ImageView image = new ImageView(context);
            image.setImageResource(R.drawable.ic_baseline_star_24);
            ll_stars.addView(image);
            image.setLayoutParams(parms);
        }

        card.setOnClickListener(v -> {
            Intent i_hotel = new Intent(this.context, HotelActivity.class);
            Bundle b = new Bundle();
            i_hotel.putExtra("images", images[position].toString());
            i_hotel.putExtras(b);
            context.startActivity(i_hotel);
        });

        return rowView;

    }
}
