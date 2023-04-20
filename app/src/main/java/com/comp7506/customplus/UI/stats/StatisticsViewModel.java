package com.comp7506.customplus.UI.stats;

import com.comp7506.customplus.UI.datamodel.ArrivalInfo;
import com.comp7506.customplus.UI.network.APIClient;
import com.comp7506.customplus.UI.network.APIInterface;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatisticsViewModel extends ViewModel {
    private MutableLiveData<ArrivalInfo> data;
    public LiveData<ArrivalInfo> getData() {
        return data;
    }

    private MutableLiveData<Boolean> requestFailed;

    public LiveData<Boolean> getFailStatus() {
        return requestFailed;
    }

    APIInterface apiInterface;
    public void init() {
        data = new MutableLiveData<>();
        requestFailed = new MutableLiveData<>();
        apiInterface = APIClient.getClient().create(APIInterface.class);
    }

    public void retrieveArrivalInfo() {
        Call<ArrivalInfo> call = apiInterface.doGetArrialData();
        call.enqueue(new Callback<ArrivalInfo>() {
            @Override
            public void onResponse(Call<ArrivalInfo> call, Response<ArrivalInfo> response) {
                ArrivalInfo arrivalInfo = response.body();
                data.postValue(arrivalInfo);
            }

            @Override
            public void onFailure(Call<ArrivalInfo> call, Throwable t) {
                requestFailed.postValue(true);
                t.printStackTrace();
            }
        });
    }
}
