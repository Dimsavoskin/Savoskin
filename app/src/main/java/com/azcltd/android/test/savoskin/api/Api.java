package com.azcltd.android.test.savoskin.api;

import com.azcltd.android.test.savoskin.model.Cities;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
    @GET("cities.json")
    Call<Cities> getData();
}
