package com.comp7506.customplus.UI.datamodel;

import com.google.gson.annotations.SerializedName;

/**
 * @author chrisyuhimself@gmail.com
 * @date 4/21/2023 4:20 PM
 */
public class Railway {
    public String fromStation;

    public String toStation;

    public String startTime;

    public String arriveTime;

    public String trainCode;

    public String durationTime;

    public Railway(String fromStation, String toStation, String startTime, String arriveTime, String trainCode, String durationTime) {
        this.fromStation = fromStation;
        this.toStation = toStation;
        this.startTime = startTime;
        this.arriveTime = arriveTime;
        this.trainCode = trainCode;
        this.durationTime = durationTime;
    }


}
