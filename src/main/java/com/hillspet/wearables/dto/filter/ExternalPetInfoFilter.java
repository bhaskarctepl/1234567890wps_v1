package com.hillspet.wearables.dto.filter;

import io.swagger.annotations.ApiParam;

import javax.ws.rs.QueryParam;

public class ExternalPetInfoFilter {
    @ApiParam(name = "petId")
    @QueryParam("petId")
    private Integer petId;

    @ApiParam(name = "studyId")
    @QueryParam("studyId")
    private Integer studyId;

    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }

    public Integer getStudyId() {
        return studyId;
    }

    public void setStudyId(Integer studyId) {
        this.studyId = studyId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ExternalPetInfoFilter{");
        sb.append("petId='").append(petId).append('\'');
        sb.append(", studyId='").append(studyId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
