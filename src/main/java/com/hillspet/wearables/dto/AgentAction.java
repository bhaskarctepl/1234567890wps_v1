package com.hillspet.wearables.dto;

public class AgentAction {
    private int agentActionId;
    private String agentActionName;
    private int showDefectiveSensor;

    public int getAgentActionId() {
        return agentActionId;
    }

    public void setAgentActionId(int agentActionId) {
        this.agentActionId = agentActionId;
    }

    public String getAgentActionName() {
        return agentActionName;
    }

    public void setAgentActionName(String agentActionName) {
        this.agentActionName = agentActionName;
    }

    public int getShowDefectiveSensor() {
        return showDefectiveSensor;
    }

    public void setShowDefectiveSensor(int showDefectiveSensor) {
        this.showDefectiveSensor = showDefectiveSensor;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AgentAction{");
        sb.append("agentActionId=").append(agentActionId);
        sb.append(", agentActionName='").append(agentActionName).append('\'');
        sb.append(", showDefectiveSensor=").append(showDefectiveSensor);
        sb.append('}');
        return sb.toString();
    }
}
