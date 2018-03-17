package com.tdp2.ghsz.tp0;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetForecastService {
    @GET("api/v1/forecast/{city}")
    Call<ForecastResponse> getForecast(@Path("city") int city);
}
