package com.tdp2.ghsz.tp0;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final int SELECT_CITY = 100;
    private static final String TAG = MainActivity.class.getName();
    private City city;
    private ListView listView;
    private TextView cityTitleLbl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        cityTitleLbl = findViewById(R.id.city_title_lbl);
        listView = findViewById(R.id.listview);
        city = new LastCity(this).getCity();

        if (!city.isVoid()) { setCityTitle(); }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener((View view) -> getForecast());
    }

    private void setCityTitle() {
        cityTitleLbl.setText(city.toString());
    }

    private void getForecast() {
        new GetForecastTask(getString(R.string.srv_base_url), () -> {}, res -> {
            if (res.success) {
                Forecast[] values = res.data.getForecast();
                ForecastArrayAdapter forecastArrayAdapter = new ForecastArrayAdapter(this, values);
                listView.setAdapter(forecastArrayAdapter);
            } else {
                Toast.makeText(this, "Error al obtener el pronostico", Toast.LENGTH_SHORT).show();
            }
        }).execute(city);
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
                    if (!city.equals(selectedCity)) {
                        city = selectedCity;
                        setCityTitle();
                        getForecast();
                    }
                }
        }
    }
}
