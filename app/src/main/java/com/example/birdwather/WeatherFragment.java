package com.example.birdwather;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.birdwather.NearbyVariables;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class WeatherFragment extends Fragment {

    RecyclerView recyclerView;
    private NearbyAdapter adapter;
    private String regionCode = "ZA";
    private static final String API_KEY = "b9s2f5kpo8hr";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        recyclerView = view.findViewById(R.id.recycle_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.ebird.org/v2/data/obs/" +regionCode+ "/recent/notable/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        EBirdJson jsonPlaceHolder = retrofit.create(EBirdJson.class);

        Call<List<NearbyVariables>> call = jsonPlaceHolder.getNotableObservations();
        call.enqueue(new Callback<List<NearbyVariables>>() {
            @Override
            public void onResponse(Call<List<NearbyVariables>> call, Response<List<NearbyVariables>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), response.code(), Toast.LENGTH_LONG).show();
                    return;
                }
                List<NearbyVariables> ebirdsList = response.body();
                adapter = new NearbyAdapter(getContext(), ebirdsList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<NearbyVariables>> call, Throwable t) {

                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

        return view;
    }

}