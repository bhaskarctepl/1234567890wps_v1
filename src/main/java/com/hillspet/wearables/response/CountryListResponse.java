package com.hillspet.wearables.response;

import java.util.List;

import com.hillspet.wearables.dto.Country;

public class CountryListResponse {
	private List<Country> countries;

	public List<Country> getCountries() {
		return countries;
	}

	public void setCountries(List<Country> countries) {
		this.countries = countries;
	}

}
