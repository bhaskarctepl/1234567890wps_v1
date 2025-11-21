/**
 * 
 */
package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.BaseDTO;
import com.hillspet.wearables.dto.ExportQuestionnaireDTO;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author radepu Date: Nov 6, 2024
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExportQuestionnaireResponse extends BaseResultCollection{
	
	@JsonProperty("rows")
	@ApiModelProperty(value = "Get the Questionnaires based on search criteria")
	private List<ExportQuestionnaireDTO> exportList;

	public List<ExportQuestionnaireDTO> getExportList() {
		return exportList;
	}

	public void setExportList(List<ExportQuestionnaireDTO> exportList) {
		this.exportList = exportList;
	}


}
