package com.comp7506.customplus.UI.datamodel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SubwaySchedule {
    @SerializedName("data")
    public Schedule data;

    public class Schedule {
        @SerializedName("scheduleList")
        public ArrayList<ScheduleList> scheduleList;
        @SerializedName("onTime")
        public boolean onTime;

        public class ScheduleList {
            @SerializedName("start")
            public String start;
            @SerializedName("destination")
            public String destination;
            @SerializedName("departureTimes")
            public ArrayList<String> departureTimes;
        }
    }
}

