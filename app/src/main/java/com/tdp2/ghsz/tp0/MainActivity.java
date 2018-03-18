package com.tdp2.ghsz.tp0;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final int SELECT_CITY = 100;
    private static final String TAG = MainActivity.class.getName();
    public static final int PERM_REQ_CODE = 200;
    public static final int SUCC_CODE = 200;
    private City city;
    private ListView listView;
    private TextView cityTitleLbl;
    private View progressLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        cityTitleLbl = findViewById(R.id.city_title_lbl);
        listView = findViewById(R.id.listview);
        progressLayout = findViewById(R.id.main_progress_layout);
        hideProgress();
        city = new LastCity(this).getCity();
        city = city.isVoid() ? City.getBuenosAires() : city;

        updateCityTitle();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener((View view) -> getForecast());

        checkPermissions();
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.INTERNET}, PERM_REQ_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERM_REQ_CODE:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    run();
                } else {
                    Toast.makeText(this, "Se negaron los permisos requeridos", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }

    private void run() {
        getForecast();
    }

    private void showProgress() {
        progressLayout.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        progressLayout.setVisibility(View.GONE);
    }

    private void updateCityTitle() {
        cityTitleLbl.setText(city.toString());
    }

    private void getForecast() {
        new GetForecastTask(getString(R.string.srv_base_url), this::showProgress, res -> {
            if (res.success) {
                int code = res.data.getCode();
                if (code != SUCC_CODE) {
                    int msgId = getMsgId(code);
                    Toast.makeText(this, getString(msgId), Toast.LENGTH_SHORT).show();
                    return;
                }

                Forecast[] values = res.data.getForecast();
                ForecastArrayAdapter forecastArrayAdapter = new ForecastArrayAdapter(this, values);
                listView.setAdapter(forecastArrayAdapter);
            } else {
                Toast.makeText(this, getString(R.string.srv_conn_err), Toast.LENGTH_SHORT).show();
            }
            hideProgress();
        }).execute(city);
    }

    private int getMsgId(int code) {
        switch (code) {
            case 500:
                return R.string.no_info;
            default:
                return R.string.unknown_err;
        }
    }

    public void selectCity(View view) {
        Intent intent = new Intent(this, CityListActivity.class);
        startActivityForResult(intent, SELECT_CITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SELECT_CITY:
                if (data != null) {
                    City selectedCity = (City) data.getSerializableExtra(CityListActivity.CITY);
                    if (selectedCity != null && !city.equals(selectedCity)) {
                        city = selectedCity;
                        updateCityTitle();
                        getForecast();
                    }
                }
        }
    }
}
