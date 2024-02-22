package com.hillspet.wearables.dto;

public class DefectiveSensorAction {
    private int defectiveSensorActionId;
    private String defectiveSensorActionName;

    public int getDefectiveSensorActionId() {
        return defectiveSensorActionId;
    }

    public void setDefectiveSensorActionId(int defectiveSensorActionId) {
        this.defectiveSensorActionId = defectiveSensorActionId;
    }

    public String getDefectiveSensorActionName() {
        return defectiveSensorActionName;
    }

    public void setDefectiveSensorActionName(String defectiveSensorActionName) {
        this.defectiveSensorActionName = defectiveSensorActionName;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DefectiveSensorAction{");
        sb.append("defectiveSensorActionId=").append(defectiveSensorActionId);
        sb.append(", defectiveSensorActionName='").append(defectiveSensorActionName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
