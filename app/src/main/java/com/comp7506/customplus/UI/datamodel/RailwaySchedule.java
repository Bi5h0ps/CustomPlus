package com.comp7506.customplus.UI.datamodel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * @author chrisyuhimself@gmail.com
 * @date 4/21/2023 11:06 AM
 */

public class RailwaySchedule {
    @SerializedName("data")
    public ArrayList<RailwayInfo> data;


    public class RailwayInfo {
        @SerializedName("queryLeftNewDTO")
        public QueryLeftNewDTO queryLeftNewDTO;

        public class QueryLeftNewDTO {
            @SerializedName("station_train_code")
            public String trainCode;

            @SerializedName("from_station_telecode")
            public String fromStation;

            @SerializedName("to_station_telecode")
            public String toStation;

            @SerializedName("start_time")
            public String startTime;

            @SerializedName("arrive_time")
            public String arriveTime;

            @SerializedName("lishi")
            public String durationTime;
        }

    }
}
