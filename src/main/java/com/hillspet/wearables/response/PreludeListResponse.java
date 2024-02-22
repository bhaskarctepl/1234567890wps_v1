package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hillspet.wearables.dto.PreludeListDTO;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PreludeListResponse {
	
	private List<PreludeListDTO> prelude;
	private List<PreludeListDTO> preludeList;
	public List<PreludeListDTO> getPrelude() {
		return prelude;
	}
	public void setPrelude(List<PreludeListDTO> prelude) {
		this.prelude = prelude;
	}
	public List<PreludeListDTO> getPreludeList() {
		return preludeList;
	}
	public void setPreludeList(List<PreludeListDTO> preludeList) {
		this.preludeList = preludeList;
	}
	
	

}
