package com.hillspet.wearables.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExternalPetInfoListDTO {
    private String plainId;
    private String petId;
    private String category;
    private String extractGroup;
    private String fieldName;
    private String fieldValue;

    public String getPlainId() {
        return plainId;
    }

    public void setPlainId(String plainId) {
        this.plainId = plainId;
    }

    public String getPetId() {
        return petId;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getExtractGroup() {
        return extractGroup;
    }

    public void setExtractGroup(String extractGroup) {
        this.extractGroup = extractGroup;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ExternalPetInfoListDTO{");
        sb.append("plainId='").append(plainId).append('\'');
        sb.append(", petId='").append(petId).append('\'');
        sb.append(", category='").append(category).append('\'');
        sb.append(", extractGroup='").append(extractGroup).append('\'');
        sb.append(", fieldName='").append(fieldName).append('\'');
        sb.append(", fieldValue='").append(fieldValue).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
