package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.SensorName;
@JsonIgnoreProperties(ignoreUnknown = true)
public class SensorNameResponse {
    List<SensorName> sensorNameList;

    public List<SensorName> getSensorNameList() {
        return sensorNameList;
    }

    public void setSensorNameList(List<SensorName> sensorNameList) {
        this.sensorNameList = sensorNameList;
    }
}
