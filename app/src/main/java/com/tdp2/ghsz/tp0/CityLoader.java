package com.tdp2.ghsz.tp0;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CityLoader {
    /* TODO : ESTA FUNCION DEBE DEVOLVER LAS CIUDADES O BIEN DESDE EL ARCHIVO JSON DE CIUDADES O BIEN INVOCANDO EL API DEL SERVER NODE */
    public List<City> getCities() {
        return Arrays.asList(
                new City(1, "Buenos Aires", "ARG"),
                new City(2, "New York", "USA"));
    }
}
