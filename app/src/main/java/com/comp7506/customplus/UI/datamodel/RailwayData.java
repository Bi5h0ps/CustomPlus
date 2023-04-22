package com.comp7506.customplus.UI.datamodel;

/**
 * @author chrisyuhimself@gmail.com
 * @date 4/22/2023 9:51 PM
 */
public class RailwayData {
    private String fromStation;
    private String toStation;
    private String startTime;
    private String arriveTime;
    private String trainCode;
    private String durationTime;

    public RailwayData(String fromStation, String toStation, String startTime, String arriveTime, String trainCode, String durationTime) {
        this.fromStation = fromStation;
        this.toStation = toStation;
        this.startTime = startTime;
        this.arriveTime = arriveTime;
        this.trainCode = trainCode;
        this.durationTime = durationTime;
    }

    public String getTrainCode() {
        return trainCode;
    }

    public void setTrainCode(String trainCode) {
        this.trainCode = trainCode;
    }

    public String getFromStation() {
        return fromStation;
    }

    public void setFromStation(String fromStation) {
        this.fromStation = fromStation;
    }

    public String getToStation() {
        return toStation;
    }

    public void setToStation(String toStation) {
        this.toStation = toStation;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    public String getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(String durationTime) {
        this.durationTime = durationTime;
    }
}
