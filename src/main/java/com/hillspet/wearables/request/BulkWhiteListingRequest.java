package com.hillspet.wearables.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
@ApiModel(description = "Contains all the information that needed whiteListing the assets with Wifi SSID", value = "BulkWhiteListingRequest")
@JsonInclude(value = Include.NON_NULL)
public class BulkWhiteListingRequest {
    private String wifiSSID; 
    private List<String> whitelistedSensors;

    public String getWifiSSID() {
        return wifiSSID;
    }

    public void setWifiSSID(String wifiSSID) {
        this.wifiSSID = wifiSSID;
    }

    public List<String> getWhitelistedSensors() {
        return whitelistedSensors;
    }

    public void setWhitelistedSensors(List<String> whitelistedSensors) {
        this.whitelistedSensors = whitelistedSensors;
    }
}