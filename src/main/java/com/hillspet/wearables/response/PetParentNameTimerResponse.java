package com.hillspet.wearables.response;

import java.util.List;

import com.hillspet.wearables.dto.PetParentNameTimer;

public class PetParentNameTimerResponse {
	
	List<PetParentNameTimer> petParentNameList;

	public List<PetParentNameTimer> getPetParentNameList() {
		return petParentNameList;
	}

	public void setPetParentNameList(List<PetParentNameTimer> petParentNameList) {
		this.petParentNameList = petParentNameList;
	}

	
	

}
