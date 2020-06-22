package com.scout.transformer;

import org.apache.commons.collections4.Transformer;
import org.springframework.stereotype.Component;

import com.scout.entity.AddressEntity;
import com.scout.model.Address;

/**
 * This is used to convert address model to address entity
 * 
 * @author Kushagra Mathur
 *
 */
@Component
public class AddressToAddressEntityTf implements Transformer<Address, AddressEntity> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.commons.collections4.Transformer#transform(java.lang.Object)
	 */
	@Override
	public AddressEntity transform(Address address) {
		AddressEntity addressEntity = null;
		if (address != null) {
			addressEntity = new AddressEntity();
			addressEntity.setAddressId(address.getAddressId());
			addressEntity.setAddressName(address.getAddressName());
			addressEntity.setCityName(address.getCityName());
			addressEntity.setCountry(address.getCountry());
			addressEntity.setPinCode(address.getPinCode());
		}
		return addressEntity;
	}
}
