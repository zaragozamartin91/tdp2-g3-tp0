package com.tdp2.ghsz.tp0;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class CityListActivity extends ListActivity implements AbsListView.OnScrollListener {
    private static final String TAG = CityListActivity.class.getName();
    public static String CITY = "CITY";

    /* Estos datos son estaticos dado que las ciudades obtenidas y la pagina actual seran cacheadas para futuras invocaciones de la misma actividad */
    private static boolean citiesLoaded = false;
    private static List<City> cities;
    private static int page = 1;

    private ArrayAdapter<City> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Dado que probablemente el listado de ciudades sea muy grande */
        if (!citiesLoaded) {
            cities = CityLoader.get().cities(page);
            cities = cities == null ? new ArrayList<>() : cities;
            citiesLoaded = cities.size() > 0;
        }

//        String[] values = cities.stream().map(City::getName).collect(Collectors.toList()).toArray(new String[]{});
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>(cities));
        setListAdapter(adapter);

        getListView().setOnScrollListener(this);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        City city = (City) getListAdapter().getItem(position);
//        City city = cities.get(position);
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

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) { }

    @Override
    public void onScroll(AbsListView listView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // SI hay pocos items, hacer nada
        if (visibleItemCount == totalItemCount) return;

        Log.d(TAG, "listView.getChildCount(): " + listView.getChildCount());

        // We take the last son in the scrollview
//        View view = listView.getChildAt(listView.getChildCount() - 1);
        //        int diff = (view.getBottom() - (listView.getHeight() + listView.getScrollY()));

//        Log.d(TAG, "diff: " + diff);
        // if diff is zero, then the bottom has been reached
//        if (diff == 0) {
//            Log.d(TAG, "BOTTOM REACHED!");
//            onBottomScrollReached();
//        }

        int itemDiff = Math.abs(firstVisibleItem + visibleItemCount - totalItemCount);
        Log.d(TAG, "itemDiff: " + itemDiff);
        if (itemDiff <= 1) {
            Log.d(TAG, "BOTTOM REACHED!");
            onBottomScrollReached();
        }
        Log.d(TAG, "firstVisibleItem: " + firstVisibleItem + ", visibleItemCount: " + visibleItemCount + ", totalItemCount: " + totalItemCount);
    }

    private void onBottomScrollReached() {
        List<City> newCities = CityLoader.get().cities(++page);
        cities.addAll(newCities);
        adapter.addAll(newCities);
    }
}
