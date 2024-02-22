package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class PetName {
    private int petId;
    private String petName;
    private int petParentId;

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public int getPetParentId() {
        return petParentId;
    }

    public void setPetParentId(int petParentId) {
        this.petParentId = petParentId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PetName{");
        sb.append("petId=").append(petId);
        sb.append(", petName='").append(petName).append('\'');
        sb.append(", petParentId=").append(petParentId);
        sb.append('}');
        return sb.toString();
    }
}
