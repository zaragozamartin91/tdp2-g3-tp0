package com.tdp2.ghsz.tp0;

import android.os.AsyncTask;
import android.util.Log;
import android.util.SparseArray;

import com.tdp2.ghsz.tp0.util.Procedure;
import com.tdp2.ghsz.tp0.util.TaskResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetForecastTask extends AsyncTask<City, String, TaskResponse<ForecastResponse>> {
    private static final String TAG = GetCitiesTask.class.getName();
    public static final int FAIL_CODE = 999;
    private String srvUrl;
    private Runnable onPreExecute = () -> {};
    private Procedure<TaskResponse<ForecastResponse>> postExecute;

    public GetForecastTask(String srvUrl, Runnable onPreExecute, Procedure<TaskResponse<ForecastResponse>> postExecute) {
        this.srvUrl = srvUrl;
        this.onPreExecute = onPreExecute;
        this.postExecute = postExecute;
    }

    @Override
    protected TaskResponse<ForecastResponse> doInBackground(City... inCities) {
        City city = inCities[0];

        publishProgress("Obteniendo pronostico");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(srvUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetForecastService service = retrofit.create(GetForecastService.class);
        Call<ForecastResponse> call = service.getForecast(city.getId());
        try {
            Response<ForecastResponse> response = call.execute();
            ForecastResponse forecast = response.body();
            int code = forecast == null ? FAIL_CODE : forecast.getCode();
            return new TaskResponse<>(true, code, forecast);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
            return new TaskResponse<>(false, FAIL_CODE, null);
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        onPreExecute.run();
    }


    @Override
    protected void onPostExecute(TaskResponse<ForecastResponse> listTaskResponse) {
        super.onPostExecute(listTaskResponse);
        postExecute.run(listTaskResponse);
    }
}
