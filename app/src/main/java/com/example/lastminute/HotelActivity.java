package com.example.lastminute;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jama.carouselview.CarouselView;
import com.jama.carouselview.CarouselViewListener;
import com.jama.carouselview.enums.IndicatorAnimationType;
import com.jama.carouselview.enums.OffsetType;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

public class HotelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CarouselView carouselView = findViewById(R.id.carouselView);

        Bundle b = getIntent().getExtras();
        String images = b.getString("images");
        String name = b.getString("name");
        String address = b.getString("address");
        String lat = b.getString("lat");
        String lng = b.getString("lng");
        int stars = b.getInt("stars");
        String checkin_from = b.getString("checkin_from");
        String checkin_to = b.getString("checkin_to");
        String checkout_from = b.getString("checkout_from");
        String checkout_to = b.getString("checkout_to");
        String email = b.getString("email");
        String phone = b.getString("phone");
        String ratings = b.getString("rating");
        int price = b.getInt("price");
        String currency = b.getString("currency");

        TextView tv_name = findViewById(R.id.tv_name);
        TextView tv_location = findViewById(R.id.tv_location);
        TextView tv_email = findViewById(R.id.tv_email);
        TextView tv_phone = findViewById(R.id.tv_phone);
        TextView tv_ci_from = findViewById(R.id.tv_ci_from);
        TextView tv_ci_to = findViewById(R.id.tv_ci_to);
        TextView tv_co_from = findViewById(R.id.tv_co_from);
        TextView tv_co_to = findViewById(R.id.tv_co_to);
        TextView tv_ratings = findViewById(R.id.tv_ratings);
        TextView tv_latlng = findViewById(R.id.tv_latlng);
        Button btn_book = findViewById(R.id.btn_book);

        tv_ci_from.setText(checkin_from);
        tv_ci_to.setText(checkin_to);
        tv_co_from.setText(checkout_from);
        tv_co_to.setText(checkout_to);
        tv_email.setText(email);
        tv_phone.setText(phone);
        tv_ratings.setText(ratings);
        btn_book.setText(String.format("Book at %s %s", price, currency));

        tv_latlng.setOnClickListener(v -> {
            // Creates an Intent that will load a map of San Francisco
            Uri gmmIntentUri = Uri.parse("geo:0,0?q="+lat+","+lng+"("+address+")");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        });

        LinearLayout ll_stars = findViewById(R.id.ll_stars);
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(48, 48);
        tv_name.setText(name);
        tv_location.setText(address);

        for (int x = 0; x < stars; x++) {
            ImageView image = new ImageView(this);
            image.setImageResource(R.drawable.ic_baseline_star_24);
            ll_stars.addView(image);
            image.setLayoutParams(parms);
        }

        try {
            JSONArray array = new JSONArray(images);
            carouselView.setSize(array.length());
            carouselView.setResource(R.layout.center_carousel_item);
            carouselView.setAutoPlay(true);
            carouselView.setIndicatorAnimationType(IndicatorAnimationType.THIN_WORM);
            carouselView.setCarouselOffset(OffsetType.CENTER);
            carouselView.setCarouselViewListener((view, position) -> {
                ImageView imageView = view.findViewById(R.id.imageView);
                try {
                    Picasso.get().load(array.getString(position)).into(imageView);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    imageView.setImageDrawable(getResources().getDrawable(array.getInt(position)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
            carouselView.show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}