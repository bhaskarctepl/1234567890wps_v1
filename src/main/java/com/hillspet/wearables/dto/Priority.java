package com.hillspet.wearables.dto;

public class Priority {
    private int priorityId;
    private String priorityName;

    public int getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(int priorityId) {
        this.priorityId = priorityId;
    }

    public String getPriorityName() {
        return priorityName;
    }

    public void setPriorityName(String priorityName) {
        this.priorityName = priorityName;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Priority{");
        sb.append("priorityId=").append(priorityId);
        sb.append(", priorityName='").append(priorityName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
