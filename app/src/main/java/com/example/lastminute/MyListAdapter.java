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

        TextView titleText = rowView.findViewById(R.id.title);
        TextView subtitleText = rowView.findViewById(R.id.subtitle);
        ImageView imageView = rowView.findViewById(R.id.icon);
        //TextView ratings = rowView.findViewById(R.id.);
        //TextView price = rowView.findViewById(R.id.tv_price);
        //LinearLayout ll_stars = rowView.findViewById(R.id.subtitle);

        titleText.setText(maintitle[position]);
        Picasso.get().load(imgid[position]).into(imageView);
        subtitleText.setText(subtitle[position]);

        return rowView;

    };
}
