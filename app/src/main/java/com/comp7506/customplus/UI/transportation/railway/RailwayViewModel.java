package com.comp7506.customplus.UI.transportation.railway;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.comp7506.customplus.UI.datamodel.RailwaySchedule;
import com.comp7506.customplus.UI.network.APIClient;
import com.comp7506.customplus.UI.network.APIInterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RailwayViewModel extends ViewModel {

    APIInterface apiInterface;

    private MutableLiveData<RailwaySchedule> railwayScheduleMutableLiveData;

    public LiveData<RailwaySchedule> getSchedule() {
        return railwayScheduleMutableLiveData;
    }

    private MutableLiveData<Boolean> requestFailed;

    public LiveData<Boolean> getFailStatus() {
        return requestFailed;
    }


    public void init() {
        railwayScheduleMutableLiveData = new MutableLiveData<>();
        requestFailed = new MutableLiveData<>();
        apiInterface = APIClient.getRailwayClient().create(APIInterface.class);
    }

    public void retrieveRailwaySchedule(String toStationName)  {
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String fromStationCode = nameToCode("West Kowloon");
        String toStationCode = nameToCode(toStationName);

        Call<RailwaySchedule> call = apiInterface.doGetRailwaySchedule(date, fromStationCode, toStationCode, "ADULT");
        call.enqueue(new Callback<RailwaySchedule>() {
            @Override
            public void onResponse(Call<RailwaySchedule> call, Response<RailwaySchedule> response) {
                RailwaySchedule railwaySchedule = response.body();
                railwayScheduleMutableLiveData.postValue(railwaySchedule);
            }

            @Override
            public void onFailure(Call<RailwaySchedule> call, Throwable t) {
                requestFailed.postValue(true);
                t.printStackTrace();
            }
        });

    }

    private String nameToCode(String station) {
        switch (station) {
            case "West Kowloon":
                station = "XJA";
                break;
            case "Futian":
                station = "NZQ";
                break;
            case "Shenzhen North":
                station = "IOQ";
                break;
            case "Dongguan South":
                station = "DNA";
                break;
            case "Dongguan":
                station = "RTQ";
                break;
            case "Guangzhou East":
                station = "GGQ";
                break;
            case "Guangzhou South":
                station = "IZQ";
                break;
        }
        return station;
    }
}
