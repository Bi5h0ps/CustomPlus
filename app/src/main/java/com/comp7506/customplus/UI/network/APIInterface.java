package com.comp7506.customplus.UI.network;

import com.comp7506.customplus.UI.datamodel.ArrivalInfo;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {
    @GET("/data/arrival")
    Call<ArrivalInfo> doGetArrialData();
}
