package com.tdp2.ghsz.tp0;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface GetCitiesService {
    @GET("api/v1/cities/{page}")
    Call<List<City>> getCities(@Path("page") int page);
}