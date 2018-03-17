package com.tdp2.ghsz.tp0;

import android.Manifest;
import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class CityListActivity extends ListActivity implements AbsListView.OnScrollListener {
    private static final String TAG = CityListActivity.class.getName();
    public static final int RESULT_FAIL = -999;
    private static final int INTERNET_PERMISSION_REQ_CODE = 100;
    public static String CITY = "CITY";

    /* Estos datos son estaticos dado que las ciudades obtenidas y la pagina actual seran cacheadas para futuras invocaciones de la misma actividad */
    private static AtomicBoolean citiesLoaded = new AtomicBoolean(false);
    private static List<City> cities;
    private static int page = 1;
    private ProgressBar mProgressBar;

    private ArrayAdapter<City> adapter;
    private String srvUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);
        srvUrl = getString(R.string.srv_base_url);

//        mProgressBar = new ProgressBar(this);
//        mProgressBar.setIndeterminate(true);
//        mProgressBar.setMax(100);
//        mProgressBar.setProgress(1);


        checkPermissions();
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
            run();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, INTERNET_PERMISSION_REQ_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case INTERNET_PERMISSION_REQ_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    run();
                }
        }
    }

    private void run() {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        setListAdapter(adapter);
        getListView().setOnScrollListener(this);

        if (citiesLoaded.get()) {
            adapter.addAll(cities);
        } else {
            new GetCitiesTask(srvUrl,
                    response -> {
                        if (response.success) {
                            cities = new ArrayList<>(response.data);
                            citiesLoaded.set(cities.size() > 0);
                            adapter.addAll(cities);
                        } else {
                            Toast.makeText(this, R.string.srv_conn_err, Toast.LENGTH_SHORT).show();
                        }
                    }).execute(page);
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        City city = (City) getListAdapter().getItem(position);
//        City city = getCities.get(position);
        Toast.makeText(this, "Ciudad " + city + " seleccionada", Toast.LENGTH_SHORT).show();
        new LastCity(this).setCity(city);
        finishWithResult(city);
    }

    private void finishWithResult(City city) {
        Bundle conData = new Bundle();
        conData.putSerializable(CITY, city);
        Intent intent = new Intent();

        intent.putExtras(conData);
        setResult(RESULT_OK, intent);

        finish();
    }

    private void finishFail() {
        Intent intent = new Intent();
        setResult(RESULT_FAIL, intent);
        finish();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) { }

    @Override
    public void onScroll(AbsListView listView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // SI hay pocos items, hacer nada
        if (visibleItemCount == totalItemCount) return;

        Log.d(TAG, "listView.getChildCount(): " + listView.getChildCount());

        int itemDiff = Math.abs(firstVisibleItem + visibleItemCount - totalItemCount);
        Log.d(TAG, "itemDiff: " + itemDiff);
        if (itemDiff <= 1) {
            Log.d(TAG, "BOTTOM REACHED!");
            onBottomScrollReached();
        }
        Log.d(TAG, "firstVisibleItem: " + firstVisibleItem + ", visibleItemCount: " + visibleItemCount + ", totalItemCount: " + totalItemCount);
    }

    private void onBottomScrollReached() {
        new GetCitiesTask(srvUrl, response -> {
            if (response.success) {
                cities.addAll(response.data);
                adapter.addAll(response.data);
            } else { Toast.makeText(this, response.msg, Toast.LENGTH_SHORT).show(); }
        }).execute(++page);
    }
}
