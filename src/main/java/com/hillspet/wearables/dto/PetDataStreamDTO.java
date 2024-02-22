package com.hillspet.wearables.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetDataStreamDTO extends BaseDTO {
	private static final long serialVersionUID = 1L;
	
	private Integer petStudyDeviceId;
	private Integer petId;
	private String petName;
	private String petPhoto;
	
	private Integer studyId;
	private String studyName;
	private String assetNumber;
	private String streamId;
	private LocalDate startDate;
	private LocalDate endDate;
	
	private String petType;
	private int petDuplicateConfigID;
	
	private LocalDate extractStartDate;
	private LocalDate extractEndDate;
	
	private LocalDate assignDate;
	
	private int petStudyId;
	
	
	private int excludeFromDataExtract;
	private int dupExcludeFromDataExtract;
	
	
	
	public int getDupExcludeFromDataExtract() {
		return dupExcludeFromDataExtract;
	}
	public void setDupExcludeFromDataExtract(int dupExcludeFromDataExtract) {
		this.dupExcludeFromDataExtract = dupExcludeFromDataExtract;
	}
	public int getExcludeFromDataExtract() {
		return excludeFromDataExtract;
	}
	public void setExcludeFromDataExtract(int excludeFromDataExtract) {
		this.excludeFromDataExtract = excludeFromDataExtract;
	}
	public int getPetStudyId() {
		return petStudyId;
	}
	public void setPetStudyId(int petStudyId) {
		this.petStudyId = petStudyId;
	}
	public LocalDate getAssignDate() {
		return assignDate;
	}
	public void setAssignDate(LocalDate assignDate) {
		this.assignDate = assignDate;
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
	public Integer getPetStudyDeviceId() {
		return petStudyDeviceId;
	}
	public void setPetStudyDeviceId(Integer petStudyDeviceId) {
		this.petStudyDeviceId = petStudyDeviceId;
	}
	public Integer getPetId() {
		return petId;
	}
	public void setPetId(Integer petId) {
		this.petId = petId;
	}
	public String getPetName() {
		return petName;
	}
	public void setPetName(String petName) {
		this.petName = petName;
	}
	public String getPetPhoto() {
		return petPhoto;
	}
	public void setPetPhoto(String petPhoto) {
		this.petPhoto = petPhoto;
	}
	public Integer getStudyId() {
		return studyId;
	}
	public void setStudyId(Integer studyId) {
		this.studyId = studyId;
	}
	public String getStudyName() {
		return studyName;
	}
	public void setStudyName(String studyName) {
		this.studyName = studyName;
	}
	public String getAssetNumber() {
		return assetNumber;
	}
	public void setAssetNumber(String assetNumber) {
		this.assetNumber = assetNumber;
	}
	public String getStreamId() {
		return streamId;
	}
	public void setStreamId(String streamId) {
		this.streamId = streamId;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public String getPetType() {
		return petType;
	}
	public void setPetType(String petType) {
		this.petType = petType;
	}
	public int getPetDuplicateConfigID() {
		return petDuplicateConfigID;
	}
	public void setPetDuplicateConfigID(int petDuplicateConfigID) {
		this.petDuplicateConfigID = petDuplicateConfigID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((assetNumber == null) ? 0 : assetNumber.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((petId == null) ? 0 : petId.hashCode());
		result = prime * result + ((petName == null) ? 0 : petName.hashCode());
		result = prime * result + ((petPhoto == null) ? 0 : petPhoto.hashCode());
		result = prime * result + ((petType == null) ? 0 : petType.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((streamId == null) ? 0 : streamId.hashCode());
		result = prime * result + ((studyId == null) ? 0 : studyId.hashCode());
		result = prime * result + ((studyName == null) ? 0 : studyName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PetDataStreamDTO other = (PetDataStreamDTO) obj;
		if (assetNumber == null) {
			if (other.assetNumber != null)
				return false;
		} else if (!assetNumber.equals(other.assetNumber))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (petId == null) {
			if (other.petId != null)
				return false;
		} else if (!petId.equals(other.petId))
			return false;
		if (petName == null) {
			if (other.petName != null)
				return false;
		} else if (!petName.equals(other.petName))
			return false;
		if (petPhoto == null) {
			if (other.petPhoto != null)
				return false;
		} else if (!petPhoto.equals(other.petPhoto))
			return false;
		if (petType == null) {
			if (other.petType != null)
				return false;
		} else if (!petType.equals(other.petType))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (streamId == null) {
			if (other.streamId != null)
				return false;
		} else if (!streamId.equals(other.streamId))
			return false;
		if (studyId == null) {
			if (other.studyId != null)
				return false;
		} else if (!studyId.equals(other.studyId))
			return false;
		if (studyName == null) {
			if (other.studyName != null)
				return false;
		} else if (!studyName.equals(other.studyName))
			return false;
		return true;
	}
	
	
	
	
}
