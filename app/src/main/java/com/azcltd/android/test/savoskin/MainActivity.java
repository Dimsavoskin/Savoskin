package com.azcltd.android.test.savoskin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

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
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private static Api api;
    private Retrofit retrofit;

    protected RecyclerView mRecyclerView;
    protected CityAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;

    List<City> cities;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cities = new ArrayList<City>();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);

        mAdapter = new CityAdapter(cities);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);


    //    if (savedInstanceState == null) {
            fillData();
     //   }



        mAdapter.notifyDataSetChanged();


        // Set CustomAdapter as the adapter for RecyclerView.

    }

    private void fillData() {
//        retrofit = new Retrofit.Builder()
//                .baseUrl("http://azcltd.com/testTask/android") //Базовая часть адреса
//                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
//                .build();
        api = ApiClient.getClient().create(Api.class);

        api.getData().enqueue(new Callback<Cities>() {
            @Override
            public void onResponse(Call<Cities> call, Response<Cities> response) {
                if (response.body() != null) {

                    cities = response.body().getCities();

                    System.out.println(cities.get(1).getName());
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Cities> call, Throwable t) {
                Toast.makeText(MainActivity.this, "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
