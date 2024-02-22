package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.UserFavouriteListDTO;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserFavouriteListResponse extends BaseResultCollection {

	private List<UserFavouriteListDTO> favouriteList;

	@JsonProperty("rows")
	@ApiModelProperty(value = "List of details for user favourite list based on search criteria")
	public List<UserFavouriteListDTO> getFavouriteList() {
		return favouriteList;
	}

	public void setFavouriteList(List<UserFavouriteListDTO> favouriteList) {
		this.favouriteList = favouriteList;
	}

}
