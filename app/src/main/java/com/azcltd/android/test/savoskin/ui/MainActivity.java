package com.azcltd.android.test.savoskin.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.azcltd.android.test.savoskin.R;
import com.azcltd.android.test.savoskin.adapter.CityAdapter;
import com.azcltd.android.test.savoskin.api.Api;
import com.azcltd.android.test.savoskin.api.ApiClient;
import com.azcltd.android.test.savoskin.model.Cities;
import com.azcltd.android.test.savoskin.model.City;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.azcltd.android.test.savoskin.R.id.recyclerView;

public class MainActivity extends AppCompatActivity {
    public static final String ARG_CITY = "arg_city";
    private static final String ITEM_POSITION = "item_position";
    private static final String ITEMS = "items_list";

    private static Api api;

    protected RecyclerView mRecyclerView;
    protected CityAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;

    ProgressBar progressBar;
    List<City> cities;
    int mScrollPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        cities = new ArrayList<City>();

        mRecyclerView = (RecyclerView) findViewById(recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new CityAdapter(MainActivity.this, cities);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

        if (null != savedInstanceState) {
            restoreData(savedInstanceState);
        } else {
            fillData();
        }
    }

    private void fillData() {
        progressBar.setVisibility(View.VISIBLE);

        api = ApiClient.getClient().create(Api.class);
        api.getData().enqueue(new Callback<Cities>() {
            @Override
            public void onResponse(Call<Cities> call, Response<Cities> response) {
                progressBar.setVisibility(View.INVISIBLE);
                if (response.body() != null) {
                    cities = response.body().getCities();
                    mAdapter.setNewData(cities);
                    mAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, R.string.empty_data_error, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Cities> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                showDialog();
            }
        });
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.dialog_error_title);
        builder.setMessage(R.string.dialog_error_message);
        builder.setCancelable(true);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // Кнопка ОК
            @Override
            public void onClick(DialogInterface dialog, int i) {
                fillData();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void restoreData(Bundle savedInstanceState) {
        mScrollPosition = savedInstanceState.getInt(ITEM_POSITION);
        cities = savedInstanceState.getParcelableArrayList(ITEMS);

        mAdapter.setNewData(cities);
        mAdapter.notifyDataSetChanged();

        mRecyclerView.scrollToPosition(mScrollPosition);
    }


    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        mScrollPosition = ((LinearLayoutManager) mLayoutManager).findFirstVisibleItemPosition();
        bundle.putInt(ITEM_POSITION, mScrollPosition);
        bundle.putParcelableArrayList(ITEMS, new ArrayList<City>(cities));
    }

}
