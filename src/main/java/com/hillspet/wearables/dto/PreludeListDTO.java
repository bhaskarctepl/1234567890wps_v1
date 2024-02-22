package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PreludeListDTO {
	private Integer extractDefId;
	private String externalStudyId;
	private String form;
	private String category;
	private String extractGroup;
	private String field;
	private String wearablesFieldName;
	
	public String getWearablesFieldName() {
		return wearablesFieldName;
	}
	public void setWearablesFieldName(String wearablesFieldName) {
		this.wearablesFieldName = wearablesFieldName;
	}
	public Integer getExtractDefId() {
		return extractDefId;
	}
	public void setExtractDefId(Integer extractDefId) {
		this.extractDefId = extractDefId;
	}
	public String getExternalStudyId() {
		return externalStudyId;
	}
	public void setExternalStudyId(String externalStudyId) {
		this.externalStudyId = externalStudyId;
	}
	public String getForm() {
		return form;
	}
	public void setForm(String form) {
		this.form = form;
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
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	
	
}
