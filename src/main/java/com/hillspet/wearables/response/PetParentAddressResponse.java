package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.Address;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PetParentAddressResponse {

	private Address residentialAddress;
	private Address shippingAddress;

	private List<Address> residentialAddressList;
	private List<Address> shippingAddressList;

	public Address getResidentialAddress() {
		return residentialAddress;
	}

	public void setResidentialAddress(Address residentialAddress) {
		this.residentialAddress = residentialAddress;
	}

	public Address getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public List<Address> getResidentialAddressList() {
		return residentialAddressList;
	}

	public void setResidentialAddressList(List<Address> residentialAddressList) {
		this.residentialAddressList = residentialAddressList;
	}

	public List<Address> getShippingAddressList() {
		return shippingAddressList;
	}

	public void setShippingAddressList(List<Address> shippingAddressList) {
		this.shippingAddressList = shippingAddressList;
	}

}
