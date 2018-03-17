package com.tdp2.ghsz.tp0;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CityLoader {
    public static CityLoader get() { return new CityLoader(); }

    /* TODO : ESTA FUNCION DEBE DEVOLVER LAS CIUDADES O BIEN DESDE EL ARCHIVO JSON DE CIUDADES O BIEN INVOCANDO EL API DEL SERVER NODE */
    public List<City> cities(int page) {
        List<City> cities = new ArrayList<>();
        int multi = 20;
        int offset = (page - 1) * multi;
        for (int i = offset; i < offset + multi; i++) {
            int id = i + 1;
            cities.add(new City(id, "city_" + id, "WHEREVER"));
        }
        return cities;
    }
}
