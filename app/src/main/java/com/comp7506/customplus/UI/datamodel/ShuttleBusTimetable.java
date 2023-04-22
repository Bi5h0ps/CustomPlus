package com.comp7506.customplus.UI.datamodel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ShuttleBusTimetable{
    @SerializedName("HK-Macau")
    public ArrayList<String> hKMacau;
    @SerializedName("Macau-HK")
    public ArrayList<String> macauHK;
    @SerializedName("HK-ZH")
    public ArrayList<String> hKZH;
    @SerializedName("ZH-HK")
    public ArrayList<String> zHHK;
    @SerializedName("Routes")
    public ArrayList<String> routes;
}

