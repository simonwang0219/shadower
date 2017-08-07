package org.shadower.example.dubbo.service;

import org.shadower.example.api.AddressService;
import org.shadower.example.api.UserService;

public class UserServiceImpl implements UserService{

	private AddressService addressService;
	@Override
	public String getUserName(int id) {
		String address=addressService.getAddress(id);
		String name="user-"+id;
		return name+","+address;
	}
	
	
	
	public AddressService getAddressService() {
		return addressService;
	}
	public void setAddressService(AddressService addressService) {
		this.addressService = addressService;
	}
	
}
