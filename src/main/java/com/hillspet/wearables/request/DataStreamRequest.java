package com.hillspet.wearables.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "Contains all the information that needed to create data stream", value = "DataStreamRequest")
@JsonInclude(value = Include.NON_NULL)
public class DataStreamRequest {
	
	private String primaryStreamId;
	private String duplicateStreamId;
	private LocalDate extractStartDate;
	private LocalDate extractEndDate;

	public String getPrimaryStreamId() {
		return primaryStreamId;
	}

	public void setPrimaryStreamId(String primaryStreamId) {
		this.primaryStreamId = primaryStreamId;
	}

	public String getDuplicateStreamId() {
		return duplicateStreamId;
	}

	public void setDuplicateStreamId(String duplicateStreamId) {
		this.duplicateStreamId = duplicateStreamId;
	}

	public LocalDate getExtractStartDate() {
		return extractStartDate;
	}

	public void setExtractStartDate(LocalDate extractStartDate) {
		this.extractStartDate = extractStartDate;
	}

	public LocalDate getExtractEndDate() {
		return extractEndDate;
	}

	public void setExtractEndDate(LocalDate extractEndDate) {
		this.extractEndDate = extractEndDate;
	}

}
