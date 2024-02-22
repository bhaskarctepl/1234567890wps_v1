package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.InventoryStatus;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InventoryStatusResponse {
    private List<InventoryStatus> inventoryStatusList;

    public List<InventoryStatus> getInventoryStatusList() {
        return inventoryStatusList;
    }

    public void setInventoryStatusList(List<InventoryStatus> inventoryStatusList) {
        this.inventoryStatusList = inventoryStatusList;
    }
}
