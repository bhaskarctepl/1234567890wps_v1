package com.hillspet.wearables.dto;

import io.swagger.annotations.ApiModelProperty;

public class PreludeMandatory {
	@ApiModelProperty(value = "extractDefId", required = true)
	private Integer extractDefId;
	

	@ApiModelProperty(value = "externalStudyId", required = true)
	private String externalStudyId;
	
	@ApiModelProperty(value = "form", required = true)
	private String formName;

	@ApiModelProperty(value = "category", required = true)
	private String category;

	@ApiModelProperty(value = "extractGroup", required = true)
	private String preludeGroup;

	@ApiModelProperty(value = "field", required = true)
	private String fieldName;
	@ApiModelProperty(value = "label", required = true)
	private String label;
	
	
	
	@Override
	public String toString() {
		return label + "#" +formName + "#" + category + "#" + preludeGroup + "#" + fieldName;
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
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getPreludeGroup() {
		return preludeGroup;
	}
	public void setPreludeGroup(String preludeGroup) {
		this.preludeGroup = preludeGroup;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	
}
