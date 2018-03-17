package com.tdp2.ghsz.tp0;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitTest {
    @Test
    public void testGetCities() throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetCitiesService service = retrofit.create(GetCitiesService.class);
        Call<List<City>> getCitiesCall = service.getCities(1);
        Response<List<City>> response = getCitiesCall.execute();
        System.out.println(response.body());
    }
}
