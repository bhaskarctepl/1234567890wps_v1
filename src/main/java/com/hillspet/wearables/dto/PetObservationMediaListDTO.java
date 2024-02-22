package com.hillspet.wearables.dto;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetObservationMediaListDTO extends BaseDTO {

	private int petId;
	private String petName;
	private String petPhoto;
	private String photoName; // PET DP
	private Integer petStatusId;
	private String petStatus;
	private String breedName;
	private Integer breedId;
	private Integer planId;
	private String planNames;
	private Integer studyId;
	private Integer petobservationId;
	private String studyNames;
	private String imagePath;
	private String videoURL;
	private String deviceNumber;
	private String orginalPhotoPath;
	private String orginalVideoPath;
	private String videoThumbnailURL;
	private List<String> imageList;
	private List<String> videoList;
	private List<String> videoThumbnailList;
	private String petPhotoUrl;
	private String duplicatePetNames;

	private Integer isPrimaryPet;
	private String petParentNames;
	private List<ObservationVideo> observationVideos;

	public String getVideoThumbnailURL() {
		return videoThumbnailURL;
	}

	public void setVideoThumbnailURL(String videoThumbnailURL) {
		this.videoThumbnailURL = videoThumbnailURL;
	}

	Map<String, String> videoMap;

	public int getPetId() {
		return petId;
	}

	public List<String> getImageList() {
		return imageList;
	}

	public void setImageList(List<String> imageList) {
		this.imageList = imageList;
	}

	public Map<String, String> getVideoMap() {
		return videoMap;
	}

	public void setVideoMap(Map<String, String> videoMap) {
		this.videoMap = videoMap;
	}

	public void setPetId(int petId) {
		this.petId = petId;
	}

	public String getOrginalPhotoPath() {
		return orginalPhotoPath;
	}

	public void setOrginalPhotoPath(String orginalPhotoPath) {
		this.orginalPhotoPath = orginalPhotoPath;
	}

	public String getOrginalVideoPath() {
		return orginalVideoPath;
	}

	public void setOrginalVideoPath(String orginalVideoPath) {
		this.orginalVideoPath = orginalVideoPath;
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

	public Integer getPetStatusId() {
		return petStatusId;
	}

	public void setPetStatusId(Integer petStatusId) {
		this.petStatusId = petStatusId;
	}

	public String getPetStatus() {
		return petStatus;
	}

	public void setPetStatus(String petStatus) {
		this.petStatus = petStatus;
	}

	public String getBreedName() {
		return breedName;
	}

	public void setBreedName(String breedName) {
		this.breedName = breedName;
	}

	public Integer getBreedId() {
		return breedId;
	}

	public void setBreedId(Integer breedId) {
		this.breedId = breedId;
	}

	public Integer getPlanId() {
		return planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
	}

	public String getPlanNames() {
		return planNames;
	}

	public void setPlanNames(String planNames) {
		this.planNames = planNames;
	}

	public Integer getStudyId() {
		return studyId;
	}

	public void setStudyId(Integer studyId) {
		this.studyId = studyId;
	}

	public Integer getPetobservationId() {
		return petobservationId;
	}

	public void setPetobservationId(Integer petobservationId) {
		this.petobservationId = petobservationId;
	}

	public String getStudyNames() {
		return studyNames;
	}

	public void setStudyNames(String studyNames) {
		this.studyNames = studyNames;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getVideoURL() {
		return videoURL;
	}

	public void setVideoURL(String videoURL) {
		this.videoURL = videoURL;
	}

	public String getDeviceNumber() {
		return deviceNumber;
	}

	public void setDeviceNumber(String deviceNumber) {
		this.deviceNumber = deviceNumber;
	}

	public String getPhotoName() {
		return photoName;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	public List<String> getVideoList() {
		return videoList;
	}

	public void setVideoList(List<String> videoList) {
		this.videoList = videoList;
	}

	public List<String> getVideoThumbnailList() {
		return videoThumbnailList;
	}

	public void setVideoThumbnailList(List<String> videoThumbnailList) {
		this.videoThumbnailList = videoThumbnailList;
	}

	public String getPetPhotoUrl() {
		return petPhotoUrl;
	}

	public void setPetPhotoUrl(String petPhotoUrl) {
		this.petPhotoUrl = petPhotoUrl;
	}

	public String getDuplicatePetNames() {
		return duplicatePetNames;
	}

	public void setDuplicatePetNames(String duplicatePetNames) {
		this.duplicatePetNames = duplicatePetNames;
	}

	public Integer getIsPrimaryPet() {
		return isPrimaryPet;
	}

	public void setIsPrimaryPet(Integer isPrimaryPet) {
		this.isPrimaryPet = isPrimaryPet;
	}

	public String getPetParentNames() {
		return petParentNames;
	}

	public void setPetParentNames(String petParentNames) {
		this.petParentNames = petParentNames;
	}

	public List<ObservationVideo> getObservationVideos() {
		return observationVideos;
	}

	public void setObservationVideos(List<ObservationVideo> observationVideos) {
		this.observationVideos = observationVideos;
	}

}
