package com.azcltd.android.test.savoskin.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.azcltd.android.test.savoskin.R;
import com.azcltd.android.test.savoskin.api.ApiClient;
import com.azcltd.android.test.savoskin.model.City;
import com.azcltd.android.test.savoskin.ui.DetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.azcltd.android.test.savoskin.ui.MainActivity.ARG_CITY;


public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {
    private static final String TAG = "CityAdapter";

    private Context mContext;

    private List<City> mDataSet = new ArrayList<City>();


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView cityName;
        private ImageView cityPhoto;
        private LinearLayout cityLayout;
        private LayoutClickListner layoutClickListner;

        public ViewHolder(View v) {
            super(v);
            cityName = (TextView) v.findViewById(R.id.cityName);
            cityPhoto = (ImageView) v.findViewById(R.id.cityPhoto);
            cityLayout = (LinearLayout) v.findViewById(R.id.cityLayout);

            layoutClickListner = new LayoutClickListner();
            cityLayout.setOnClickListener(layoutClickListner);
        }

        public TextView getCityName() {
            return cityName;
        }

        public ImageView getCityPhoto() {
            return cityPhoto;
        }
    }

    public CityAdapter(Context context, List<City> dataSet) {
        mContext = context;
        mDataSet = dataSet;
    }

    public void setNewData(List<City> dataSet) {
        mDataSet = dataSet;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_city, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        final City city = mDataSet.get(position);

        if (city != null) {
            viewHolder.layoutClickListner.setRecord(city);
            viewHolder.getCityName().setText(city.getName());

            Picasso.with(CityAdapter.this.mContext)
                    .load(ApiClient.BASE_URL + city.getImageUrl())
                    .error(android.R.mipmap.sym_def_app_icon)
                    .into(viewHolder.cityPhoto);
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    private class LayoutClickListner implements View.OnClickListener {
        private City city;

        @Override
        public void onClick(View v) {
            showDetails(city);
        }

        public void setRecord(City city) {
            this.city = city;
        }
    }

    void showDetails(City city) {
        Intent intent = new Intent(mContext, DetailsActivity.class);
        intent.putExtra(ARG_CITY, city);
        mContext.startActivity(intent);
    }
}