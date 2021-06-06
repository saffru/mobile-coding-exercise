package com.example.lastminute;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

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
        CarouselView carouselView = findViewById(R.id.carouselView);

        Bundle b = getIntent().getExtras();
        String images=b.getString("images");

        try {
            JSONArray array = new JSONArray(images);
            carouselView.setSize(array.length());
            carouselView.setResource(R.layout.center_carousel_item);
            carouselView.setAutoPlay(true);
            carouselView.setIndicatorAnimationType(IndicatorAnimationType.THIN_WORM);
            carouselView.setCarouselOffset(OffsetType.CENTER);
            carouselView.setCarouselViewListener(new CarouselViewListener() {
                @Override
                public void onBindView(View view, int position) {
                    // Example here is setting up a full image carousel
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
                }
            });
            // After you finish setting up, show the CarouselView
            carouselView.show();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //String[] images = getIntent().getStringArrayExtra("images");


    }
}