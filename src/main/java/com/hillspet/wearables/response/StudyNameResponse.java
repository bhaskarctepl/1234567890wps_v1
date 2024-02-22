package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.StudyName;
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudyNameResponse {
    List<StudyName> studyNameList;

    public List<StudyName> getStudyNameList() {
        return studyNameList;
    }

    public void setStudyNameList(List<StudyName> studyNameList) {
        this.studyNameList = studyNameList;
    }
}
