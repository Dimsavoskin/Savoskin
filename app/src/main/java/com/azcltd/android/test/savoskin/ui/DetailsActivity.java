package com.azcltd.android.test.savoskin.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.azcltd.android.test.savoskin.R;
import com.azcltd.android.test.savoskin.api.ApiClient;
import com.azcltd.android.test.savoskin.model.City;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    ImageView imageView;
    TextView descriptionView;
    City city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        city = intent.getParcelableExtra(MainActivity.ARG_CITY);

        imageView = (ImageView) findViewById(R.id.activityDetailsImage);
        descriptionView = (TextView) findViewById(R.id.activityDetailsDescription);

        if(city != null) {
            String url = ApiClient.BASE_URL + city.getImageUrl();
            Picasso.with(this)
                    .load(url)
                    .error(android.R.mipmap.sym_def_app_icon)
                    .into(imageView);

            descriptionView.setText(city.getDescription());
        }
    }
}
