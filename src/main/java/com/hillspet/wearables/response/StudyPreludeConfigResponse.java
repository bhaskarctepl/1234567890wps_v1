package com.hillspet.wearables.response;

import java.util.List;

import com.hillspet.wearables.dto.PreludeMandatory;
import com.hillspet.wearables.request.PreludeAssociated;

public class StudyPreludeConfigResponse {

	private List<PreludeAssociated> preludeAssociated;
	private List<PreludeMandatory> PreludeMandatory;

	public List<PreludeAssociated> getPreludeAssociated() {
		return preludeAssociated;
	}

	public void setPreludeAssociated(List<PreludeAssociated> preludeAssociated) {
		this.preludeAssociated = preludeAssociated;
	}

	public List<PreludeMandatory> getPreludeMandatory() {
		return PreludeMandatory;
	}

	public void setPreludeMandatory(List<PreludeMandatory> preludeMandatory) {
		PreludeMandatory = preludeMandatory;
	}

}
