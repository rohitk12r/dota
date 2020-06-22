package com.scout.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scout.dao.AddressRepository;
import com.scout.entity.AddressEntity;
import com.scout.exception.AddressNotFoundException;
import com.scout.model.Address;
import com.scout.transformer.AddressEntityToAddressTf;
import com.scout.transformer.AddressToAddressEntityTf;

/**
 * It is implementing the business logic
 * 
 * @author Kushagra Mathur
 *
 */
@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private AddressEntityToAddressTf addressEntityToAddressTf;

	@Autowired
	private AddressToAddressEntityTf addressToAddressEntityTf;

	/**
	 * It is used for adding address in database
	 * 
	 * @param address
	 *            Storing the address into database
	 * @return {@link Long}
	 */
	@Override
	public Long add(Address address) {
		AddressEntity addressEntity = addressToAddressEntityTf.transform(address);
		AddressEntity newAddressEntity = addressRepository.save(addressEntity);
		return newAddressEntity.getAddressId();
	}

	/**
	 * It is used to update service in database
	 * 
	 * @param address
	 *            it is used for update the address
	 * @return {@link Long}
	 */
	@Override
	public Long update(Address address) {
		AddressEntity addressEntity = addressToAddressEntityTf.transform(address);
		AddressEntity newAddressEntity = addressRepository.save(addressEntity);
		AddressEntity updateAddressEntity = addressRepository.save(newAddressEntity);
		return updateAddressEntity.getAddressId();
	}

	/**
	 * It is used to delete service in database
	 * 
	 * @param addressId
	 *            its is used for deleting the address
	 * @return {@link Long}
	 */
	@Override
	public Long delete(Long id) {
		try {
			addressRepository.deleteById(id);
			return id;
		} catch (Exception e) {
			throw new AddressNotFoundException("Address is not available with ID: " + id);
		}

	}

	/**
	 * It is used to search service by address ID in database
	 * 
	 * @param addressId
	 *            It is use for searching the address
	 * @return {@link Address}
	 */
	@Override
	public Address searchById(Long addressId) {
		try {
			Optional<AddressEntity> addressEntity = addressRepository.findById(addressId);
				Address address = addressEntityToAddressTf.transform(addressEntity.get());
				return address;
			} 
		catch (Exception e) {
			throw new AddressNotFoundException("Address is not found with ID: " + addressId);
		}

	}

	/**
	 * It is used to search service in database It is return the complete
	 * address
	 * 
	 * @return {@link List}
	 */
	@Override
	public List<Address> search() {
		try {
			List<AddressEntity> addressEntities = addressRepository.findAll();
			if (addressEntities.isEmpty()) {
				throw new AddressNotFoundException("Address are not avaiable");

			} else {
				List<Address> addressCollection = CollectionUtils.collect(addressEntities, this.addressEntityToAddressTf,
						new ArrayList<Address>());
				return addressCollection;
			}
		} catch (Exception e) {
			throw new AddressNotFoundException("Address are not avaiable");
		}

	}

}
