package com.hillspet.wearables.request;

import java.time.LocalDate;

import io.swagger.annotations.ApiModelProperty;

public class PreludeAssociated {

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

	@Override
	public String toString() {
		return formName + "#" + category + "#" + preludeGroup + "#" + fieldName;
	}
	
	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
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

	

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	


}
