package com.hillspet.wearables.dto;

import java.util.HashMap;
import java.util.List;
import java.util.StringJoiner;

public class ThresholdByStudyReport {

    private String threshold;
    private String message;
    private HashMap<String,Long> thresholdActionByCount;

    public String getThreshold() {
        return threshold;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }

    public HashMap<String, Long> getThresholdActionByCount() {
        return thresholdActionByCount;
    }

    public void setThresholdActionByCount(HashMap<String, Long> thresholdActionByCount) {
        this.thresholdActionByCount = thresholdActionByCount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ThresholdByStudyReport.class.getSimpleName() + "[", "]")
                .add("threshold='" + threshold + "'")
                .add("message='" + message + "'")
                .add("thresholdActionByCount=" + thresholdActionByCount)
                .toString();
    }
}
