package com.hillspet.wearables.dto;

public class Status {
    private int statusId;
    private String statusName;

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Status{");
        sb.append("statusId=").append(statusId);
        sb.append(", statusName='").append(statusName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
