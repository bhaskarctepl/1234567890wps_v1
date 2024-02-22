package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.PetParent;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class PetParentNameResponse {
    List<PetParent> petNameList;

    public List<PetParent> getPetNameList() {
        return petNameList;
    }

    public void setPetNameList(List<PetParent> petNameList) {
        this.petNameList = petNameList;
    }
}
