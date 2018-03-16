package com.tdp2.ghsz.tp0;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class CityListActivity extends ListActivity {
    public static String CITY = "CITY";
    private static boolean citiesLoaded = false;
    private static City[] cityArr = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Dado que probablemente el listado de ciudades sea muy grande */
        if (!citiesLoaded) {
            List<City> cities = new CityLoader().getCities();
            cities = cities == null ? new ArrayList<>() : cities;
            cityArr = cities.toArray(new City[]{});
            citiesLoaded = cityArr.length > 0;
        }

//        String[] values = cities.stream().map(City::getName).collect(Collectors.toList()).toArray(new String[]{});
        ArrayAdapter<City> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cityArr);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        City city = (City) getListAdapter().getItem(position);
//        City city = cities.get(position);
        Toast.makeText(this, "Ciudad " + city + " seleccionada", Toast.LENGTH_SHORT).show();
    }
}
