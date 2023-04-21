package com.comp7506.customplus.UI.network;

import com.comp7506.customplus.UI.datamodel.ArrivalInfo;
import com.comp7506.customplus.UI.datamodel.RailwaySchedule;
import com.comp7506.customplus.UI.datamodel.SubwaySchedule;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {
    @GET("/data/arrival")
    Call<ArrivalInfo> doGetArrialData();

    @GET("/data/subway")
    Call<SubwaySchedule> doGetSubwaySchedule(@Query("line") String line, @Query("sta") String sta);

    @GET("/otn/leftTicketPrice/queryAllPublicPrice")
    Call<RailwaySchedule> doGetRailwaySchedule(@Query("leftTicketDTO.train_date") String date, @Query("leftTicketDTO.from_station") String fromStationCode, @Query("leftTicketDTO.to_station") String toStationCode, @Query("purpose_codes") String ticketType);

}
