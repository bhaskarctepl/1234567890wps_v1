package com.hillspet.wearables.dto;

import java.util.StringJoiner;

public class ThresholdActionByCount {
    private String actionName;
    private long actionCount;

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public long getActionCount() {
        return actionCount;
    }

    public void setActionCount(long actionCount) {
        this.actionCount = actionCount;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ThresholdActionByCount.class.getSimpleName() + "[", "]")
                .add("actionName='" + actionName + "'")
                .add("actionCount=" + actionCount)
                .toString();
    }
}
