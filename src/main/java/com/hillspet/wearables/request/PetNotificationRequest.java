package com.hillspet.wearables.request;

import java.util.List;

import com.hillspet.wearables.dto.PetNotificationDTO;

import io.swagger.annotations.ApiModelProperty;

public class PetNotificationRequest {
	
    @ApiModelProperty(value = "List of notification configurations for the pet", required = true)
    private List<PetNotificationDTO> petnotifications;

	public List<PetNotificationDTO> getPetnotifications() {
		return petnotifications;
	}

	public void setPetnotifications(List<PetNotificationDTO> petnotifications) {
		this.petnotifications = petnotifications;
	}



}
