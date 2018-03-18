package com.tdp2.ghsz.tp0;

import android.os.AsyncTask;
import android.util.Log;

import com.tdp2.ghsz.tp0.util.Procedure;
import com.tdp2.ghsz.tp0.util.TaskResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetCitiesTask extends AsyncTask<Integer, String, TaskResponse<List<City>>> {
    private static final String TAG = GetCitiesTask.class.getName();
    public static final int SUCC_CODE = 200;
    public static final int FAIL_CODE = 999;
    private String srvUrl;
    private Runnable onPreExecute = () -> {};
    private Procedure<TaskResponse<List<City>>> postExecute;

    public GetCitiesTask(String srvUrl, Procedure<TaskResponse<List<City>>> postExecute) {
        this.srvUrl = srvUrl;
        this.postExecute = postExecute;
    }

    public GetCitiesTask(String srvUrl, Runnable onPreExecute, Procedure<TaskResponse<List<City>>> postExecute) {
        this.srvUrl = srvUrl;
        this.onPreExecute = onPreExecute;
        this.postExecute = postExecute;
    }

    @Override
    protected TaskResponse<List<City>> doInBackground(Integer... integers) {
        int page = integers[0];

        publishProgress("Buscando ciudades");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(srvUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetCitiesService service = retrofit.create(GetCitiesService.class);
        Call<List<City>> getCitiesCall = service.getCities(page);
        try {
            Response<List<City>> response = getCitiesCall.execute();
            List<City> cities = response.body();
            return new TaskResponse<>(true, SUCC_CODE, cities);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
            return new TaskResponse<>(false, FAIL_CODE, new ArrayList<>());
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        onPreExecute.run();
    }


    @Override
    protected void onPostExecute(TaskResponse<List<City>> listTaskResponse) {
        super.onPostExecute(listTaskResponse);
        postExecute.run(listTaskResponse);
    }
}
