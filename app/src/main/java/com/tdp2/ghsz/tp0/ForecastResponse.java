package com.tdp2.ghsz.tp0;


import java.util.Arrays;

public class ForecastResponse {
    private int code;
    private Forecast[] forecast;

    public ForecastResponse(int code, Forecast[] forecast) {
        this.code = code;
        this.forecast = forecast;
    }

    public int getCode() {
        return code;
    }

    public Forecast[] getForecast() {
        return forecast;
    }

    @Override
    public String toString() {
        return "ForecastResponse{" +
                "code=" + code +
                ", forecast=" + Arrays.toString(forecast) +
                '}';
    }
}
