package com.tdp2.ghsz.tp0;

import com.tdp2.ghsz.tp0.util.TaskResponse;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetCitiesTaskTest {
    @Test
    public void doInBackground() throws Exception {
        List<City> cities = Arrays.asList(
                new City(1, "Buenos Aires", "ARG"),
                new City(2, "New York", "USA")
        );

        Response<List<City>> response = mock(Response.class);
        when(response.body()).thenReturn(cities);

        Call<List<City>> call = mock(Call.class);
        when(call.execute()).thenReturn(response);

        GetCitiesService service = mock(GetCitiesService.class);
        when(service.getCities(anyInt())).thenReturn(call);

        Retrofit retrofit = mock(Retrofit.class);
        when(retrofit.create(GetCitiesService.class)).thenReturn(service);

        GetCitiesTask getCitiesTask = new GetCitiesTask(
                "localhost:5000",
                () -> {},
                r -> {},
                retrofit
        );
        Integer page = 1;
        TaskResponse<List<City>> taskResponse = getCitiesTask.doInBackground(page);
        assertTrue(taskResponse.success);
        assertEquals(200 , taskResponse.code);
        assertEquals(cities , taskResponse.data);
    }
}