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
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final int SELECT_CITY = 100;
    private static final String TAG = MainActivity.class.getName();
    private City city = City.getVoidCity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            new GetForecastTask(getString(R.string.srv_base_url), () -> {}, res -> {
                Log.d(TAG, "" + res.data);
            }).execute(city);
        });
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
                city = data == null ? city : (City) data.getSerializableExtra(CityListActivity.CITY);
                reload();
        }
    }

    private void reload() {
        // TODO : HACER ALGO CON LA CIUDAD SELECCIONADA COMO OBTENER EL FORECAST O MODIFICAR ICONOS O LO QUE SEA
    }
}
