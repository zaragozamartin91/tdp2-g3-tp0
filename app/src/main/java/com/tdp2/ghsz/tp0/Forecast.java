package com.tdp2.ghsz.tp0;


public class Forecast {
    private WeatherStatus day;
    private WeatherStatus night;

    public Forecast(WeatherStatus day, WeatherStatus night) {
        this.day = day;
        this.night = night;
    }

    public WeatherStatus getDay() {
        return day;
    }

    public WeatherStatus getNight() {
        return night;
    }

    @Override
    public String toString() {
        return "Forecast{" +
                "day=" + day +
                ", night=" + night +
                '}';
    }
}
