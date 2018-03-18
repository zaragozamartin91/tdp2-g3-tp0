package com.tdp2.ghsz.tp0;

import android.app.Activity;
import android.content.SharedPreferences;

public class LastCity {
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String COUNTRY = "country";
    SharedPreferences prefs;

    public LastCity(Activity activity) {
        prefs = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    // Si no se eligio ninguna ciudad, por defecto toma a buenos aires
    // TODO: Ver el formato de la ciudad para que lo tome la api
    City getCity() {
        int id = prefs.getInt(ID, 0);
        if (id == 0) { return City.getVoidCity(); }

        String name = prefs.getString(NAME, "VOID");
        String country = prefs.getString(COUNTRY, "VOID");
        return new City(id, name, country);
    }

    void setCity(City city) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(ID, city.getId());
        editor.putString(NAME, city.getName());
        editor.putString(COUNTRY, city.getCountry());
        editor.commit();
    }
}
