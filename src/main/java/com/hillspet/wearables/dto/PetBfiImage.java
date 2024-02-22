package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetBfiImage {

	private Integer petBfiImageDtlsId;
	private String imagePositionId;
	private String imagePosition;
	private String imageName;
	private String imageUrl;
	private String thumbnailUrl;
	private String description;

	public Integer getPetBfiImageDtlsId() {
		return petBfiImageDtlsId;
	}

	public void setPetBfiImageDtlsId(Integer petBfiImageDtlsId) {
		this.petBfiImageDtlsId = petBfiImageDtlsId;
	}

	public String getImagePositionId() {
		return imagePositionId;
	}

	public void setImagePositionId(String imagePositionId) {
		this.imagePositionId = imagePositionId;
	}

	public String getImagePosition() {
		return imagePosition;
	}

	public void setImagePosition(String imagePosition) {
		this.imagePosition = imagePosition;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
