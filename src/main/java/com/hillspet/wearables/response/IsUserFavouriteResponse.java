package com.hillspet.wearables.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hillspet.wearables.dto.UserFavourite;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IsUserFavouriteResponse {

	private UserFavourite favourite;

	public UserFavourite getFavourite() {
		return favourite;
	}

	public void setFavourite(UserFavourite favourite) {
		this.favourite = favourite;
	}

}
