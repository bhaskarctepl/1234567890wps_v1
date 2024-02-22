package com.hillspet.wearables.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserFavouriteListDTO extends BaseDTO {

	private Integer favouriteId;
	private Integer menuId;
	private String menuName;
	private Integer entityId;
	private Integer userId;
	private Integer roleId;
	private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;
	private String recordName;
	
	private int petStudyId;

	
	public int getPetStudyId() {
		return petStudyId;
	}

	public void setPetStudyId(int petStudyId) {
		this.petStudyId = petStudyId;
	}

	public Integer getFavouriteId() {
		return favouriteId;
	}

	public void setFavouriteId(Integer favouriteId) {
		this.favouriteId = favouriteId;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public Integer getEntityId() {
		return entityId;
	}

	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getRecordName() {
		return recordName;
	}

	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}
}
