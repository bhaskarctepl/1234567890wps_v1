package com.hillspet.wearables.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringJoiner;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FRLookUpResponse {
    private List<HashMap<String,String>> actions;
    private List<HashMap<String,String>> actionsAll;
    private List<HashMap<String,String>> pets;
    private List<HashMap<String,String>> threshold;

    public FRLookUpResponse() {
        this.actions= new ArrayList<>();
        this.pets= new ArrayList<>();
        this.threshold= new ArrayList<>();
        this.actionsAll= new ArrayList<>();
    }

    public List<HashMap<String, String>> getActions() {
        return actions;
    }

    public void setActions(List<HashMap<String, String>> actions) {
        this.actions = actions;
    }

    public List<HashMap<String, String>> getPets() {
        return pets;
    }

    public void setPets(List<HashMap<String, String>> pets) {
        this.pets = pets;
    }

    public List<HashMap<String, String>> getThreshold() {
        return threshold;
    }

    public void setThreshold(List<HashMap<String, String>> threshold) {
        this.threshold = threshold;
    }

    public List<HashMap<String, String>> getActionsAll() {
        return actionsAll;
    }

    public void setActionsAll(List<HashMap<String, String>> actionsAll) {
        this.actionsAll = actionsAll;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", FRLookUpResponse.class.getSimpleName() + "[", "]")
                .add("actions=" + actions)
                .add("actionsAll=" + actionsAll)
                .add("pets=" + pets)
                .add("threshold=" + threshold)
                .toString();
    }
}
