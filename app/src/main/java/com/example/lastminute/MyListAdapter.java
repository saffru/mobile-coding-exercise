package com.example.lastminute;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.squareup.picasso.Picasso;

public class MyListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] maintitle;
    private final String[] subtitle;
    private final String[] imgid;
    private final String[] ratings;
    private final String[] prices;
    private final String[] currencies;
    private final int[] stars;

    public MyListAdapter(Activity context, String[] maintitle,
                         String[] subtitle, String[] imgid, String[] ratings,
                         String[] prices, String[] currencies, int[] stars) {
        super(context, R.layout.mylist, maintitle);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.maintitle=maintitle;
        this.subtitle=subtitle;
        this.imgid=imgid;
        this.ratings=ratings;
        this.prices=prices;
        this.currencies=currencies;
        this.stars=stars;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mylist, null,true);

        TextView titleText = rowView.findViewById(R.id.tv_title);
        TextView subtitleText = rowView.findViewById(R.id.tv_subtitle);
        ImageView imageView = rowView.findViewById(R.id.icon);
        TextView tv_ratings = rowView.findViewById(R.id.tv_rating);
        TextView tv_price = rowView.findViewById(R.id.tv_price);
        TextView tv_currency = rowView.findViewById(R.id.tv_currency);
        LinearLayout ll_stars = rowView.findViewById(R.id.ll_stars);
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(32,32);

        titleText.setText(maintitle[position]);
        Picasso.get().load(imgid[position]).into(imageView);
        subtitleText.setText(subtitle[position]);
        tv_ratings.setText(ratings[position]);
        tv_price.setText(prices[position]);
        String currency;
        switch (currencies[position]){
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

        for(int x=0;x<stars[position];x++) {
            ImageView image = new ImageView(context);
            image.setImageResource(R.drawable.ic_baseline_star_24);
            ll_stars.addView(image);
            image.setLayoutParams(parms);
        }

        return rowView;

    }
}
