package org.shadower.example.dubbo.service;

import org.shadower.example.api.AddressService;

public class AddressServiceImpl implements AddressService{

	@Override
	public String getAddress(int id) {
		return "address-"+id;
	}

}
