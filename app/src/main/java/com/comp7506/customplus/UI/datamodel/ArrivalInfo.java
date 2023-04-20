package com.comp7506.customplus.UI.datamodel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ArrivalInfo {
    @SerializedName("data")
    public ArrayList<Datum> data;

    @SerializedName("dates")
    public ArrayList<String> dates;

    @SerializedName("control_point_names")
    public ArrayList<String> controlPointNames;

    public class Datum{
        @SerializedName("control_point_name")
        public String control_point_name;
        @SerializedName("arrival_count")
        public ArrayList<Integer> arrival_count;
    }
}
