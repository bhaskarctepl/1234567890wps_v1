package com.hillspet.wearables.dto;

public class RootCause {
    private int rootCauseId;
    private String rootCauseName;

    public int getRootCauseId() {
        return rootCauseId;
    }

    public void setRootCauseId(int rootCauseId) {
        this.rootCauseId = rootCauseId;
    }

    public String getRootCauseName() {
        return rootCauseName;
    }

    public void setRootCauseName(String rootCauseName) {
        this.rootCauseName = rootCauseName;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RootCause{");
        sb.append("rootCauseId=").append(rootCauseId);
        sb.append(", rootCauseName='").append(rootCauseName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
