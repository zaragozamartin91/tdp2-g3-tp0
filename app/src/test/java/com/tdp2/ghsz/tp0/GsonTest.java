package com.tdp2.ghsz.tp0;

import com.google.gson.Gson;

import org.junit.Test;

public class GsonTest {
    @Test
    public void testParseForecast() {
        String json = "{\"code\":200,\"forecast\":[{\"day\":{\"temp\":15.553562458711244,\"weather\":\"sunny\"},\"night\":{\"temp\":27.68633283546275,\"weather\":\"snow\"}},{\"day\":{\"temp\":23.475570750103035,\"weather\":\"windy\"},\"night\":{\"temp\":25.617135872442834,\"weather\":\"sunny\"}},{\"day\":{\"temp\":22.063432153388792,\"weather\":\"sunny\"},\"night\":{\"temp\":21.572863797044715,\"weather\":\"sunny\"}},{\"day\":{\"temp\":26.124196569954883,\"weather\":\"cloudy\"},\"night\":{\"temp\":18.335998791344238,\"weather\":\"sunny\"}},{\"day\":{\"temp\":16.100376654947574,\"weather\":\"snow\"},\"night\":{\"temp\":28.530510062355084,\"weather\":\"sunny\"}}]}";
        ForecastResponse forecastResponse = new Gson().fromJson(json, ForecastResponse.class);
        System.out.println(forecastResponse);
    }
}
