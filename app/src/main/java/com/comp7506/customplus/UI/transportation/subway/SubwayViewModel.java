package com.comp7506.customplus.UI.transportation.railway;

import com.comp7506.customplus.UI.datamodel.SubwaySchedule;
import com.comp7506.customplus.UI.network.APIClient;
import com.comp7506.customplus.UI.network.APIInterface;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubwayViewModel extends ViewModel {
    APIInterface apiInterface;

    private MutableLiveData<SubwaySchedule> subwayScheduleMutableLiveData;
    public LiveData<SubwaySchedule> getSchedule() {
        return subwayScheduleMutableLiveData;
    }

    public void init() {
        subwayScheduleMutableLiveData = new MutableLiveData<>();
        apiInterface = APIClient.getClient().create(APIInterface.class);
    }

    public void retrieveSubwaySchedule(String controlPoint) {
        String line = "";
        String station = "";

        switch (controlPoint) {
            case "Airport":
                line = "AEL";
                station = "AIR";
                break;
            case "West Kowloon":
                line = "TCL";
                station = "KOW";
                break;
            case "Man Kam To":
                line = "EAL";
                station = "SHS";
                break;
            case "Heung Yuen Wai":
                line = "EAL";
                station = "SHS";
                break;
            case "Shenzhen Bay":
                line = "TML";
                station = "TIS";
                break;
            case "Lo Wu":
                line = "EAL";
                station = "LOW";
                break;
            case "Lok Ma Chau":
                line = "EAL";
                station = "LMC";
                break;
        }
        Call<SubwaySchedule> call = apiInterface.doGetSubwaySchedule(line, station);
        call.enqueue(new Callback<SubwaySchedule>() {
            @Override
            public void onResponse(Call<SubwaySchedule> call, Response<SubwaySchedule> response) {
                SubwaySchedule subwaySchedule = response.body();
                subwayScheduleMutableLiveData.postValue(subwaySchedule);
            }

            @Override
            public void onFailure(Call<SubwaySchedule> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
