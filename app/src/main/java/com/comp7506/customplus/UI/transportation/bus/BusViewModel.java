package com.comp7506.customplus.UI.transportation.bus;

import com.comp7506.customplus.UI.datamodel.ShuttleBusTimetable;
import com.comp7506.customplus.UI.network.APIClient;
import com.comp7506.customplus.UI.network.APIInterface;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusViewModel extends ViewModel {
    APIInterface apiInterface;
    private MutableLiveData<ShuttleBusTimetable> busTimetableMutableLiveData;
    public LiveData<ShuttleBusTimetable> getBusSchedule() {
        return busTimetableMutableLiveData;
    }

    private MutableLiveData<Boolean> requestFailed;
    public LiveData<Boolean> getFailStatus() {return requestFailed;}

    public void init() {
        busTimetableMutableLiveData = new MutableLiveData<>();
        requestFailed = new MutableLiveData<>();
        apiInterface = APIClient.getClient().create(APIInterface.class);
    }

    public void retrieveBusSchedule() {
        Call<ShuttleBusTimetable> call = apiInterface.doGetBusSchedule();
        call.enqueue(new Callback<ShuttleBusTimetable>() {
            @Override
            public void onResponse(Call<ShuttleBusTimetable> call, Response<ShuttleBusTimetable> response) {
                ShuttleBusTimetable shuttleBusTimetable = response.body();
                busTimetableMutableLiveData.postValue(shuttleBusTimetable);
            }

            @Override
            public void onFailure(Call<ShuttleBusTimetable> call, Throwable t) {
                requestFailed.postValue(true);
                t.printStackTrace();
            }
        });
    }
}
