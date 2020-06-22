package com.scout.transformer;

import org.apache.commons.collections4.Transformer;
import org.springframework.stereotype.Component;

import com.scout.entity.AddressEntity;
import com.scout.model.Address;

/**
 * This is used to convert address entity to addressmodel
 * @author Kushagra Mathur
 *
 */
@Component
public class AddressEntityToAddressTf implements Transformer<AddressEntity, Address> {

	/* (non-Javadoc)
	 * @see org.apache.commons.collections4.Transformer#transform(java.lang.Object)
	 */
	@Override
	public Address transform(AddressEntity entity) {
		Address address = null;
		if (entity != null) {
			address = new Address();
			address.setAddressId(entity.getAddressId());
			address.setAddressName(entity.getAddressName());
			address.setCityName(entity.getCityName());
			address.setCountry(entity.getCountry());
			address.setPinCode(entity.getPinCode());
		}
		return address;
	}

}
