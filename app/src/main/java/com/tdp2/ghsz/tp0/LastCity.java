package com.tdp2.ghsz.tp0;

import android.app.Activity;
import android.content.SharedPreferences;

public class LastCity {
    SharedPreferences prefs;

    public LastCity(Activity activity){
        prefs = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    // Si no se eligio ninguna ciudad, por defecto toma a buenos aires
    // TODO: Ver el formato de la ciudad para que lo tome la api
    String getCity(){
        return prefs.getString("city", "Buenos Aires, AR");
    }

    void setCity(City city){
        prefs.edit().putString("city", city.getName()).commit();
    }
}
