package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.DefectiveSensorAction;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DefectiveSensorActionResponse {
    private List<DefectiveSensorAction> defectiveSensorActionList;

    public List<DefectiveSensorAction> getDefectiveSensorActionList() {
        return defectiveSensorActionList;
    }

    public void setDefectiveSensorActionList(List<DefectiveSensorAction> defectiveSensorActionList) {
        this.defectiveSensorActionList = defectiveSensorActionList;
    }
}
