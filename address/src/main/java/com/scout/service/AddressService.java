package com.scout.service;

import java.util.List;

import com.scout.model.Address;

public interface AddressService {

	Long add(Address address);

	Long update(Address address);

	Long delete(Long Id);

	Address searchById(Long addressId);
	
	List<Address> search();
	

}
