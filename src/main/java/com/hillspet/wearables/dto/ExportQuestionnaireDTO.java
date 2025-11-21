/**
 * 
 */
package com.hillspet.wearables.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author radepu
 * Date: Nov 6, 2024
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExportQuestionnaireDTO extends BaseDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private LocalDate requestedDate;
	private String questionnaireName;
	private String status;
	private int questionnaireExportId;

	public LocalDate getRequestedDate() {
		return requestedDate;
	}
	public void setRequestedDate(LocalDate requestedDate) {
		this.requestedDate = requestedDate;
	}
	public String getQuestionnaireName() {
		return questionnaireName;
	}
	public void setQuestionnaireName(String questionnaireName) {
		this.questionnaireName = questionnaireName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getQuestionnaireExportId() {
		return questionnaireExportId;
	}
	public void setQuestionnaireExportId(int questionnaireExportId) {
		this.questionnaireExportId = questionnaireExportId;
	}
	
}
