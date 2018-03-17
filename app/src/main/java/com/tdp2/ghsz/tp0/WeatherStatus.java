package com.tdp2.ghsz.tp0;


public class WeatherStatus {
    public final double temp;
    public final String weather;

    public WeatherStatus(double temp, String status) {
        this.temp = temp;
        this.weather = status;
    }

    @Override
    public String toString() {
        return "WeatherStatus{" +
                "temp=" + temp +
                ", weather='" + weather + '\'' +
                '}';
    }
}
