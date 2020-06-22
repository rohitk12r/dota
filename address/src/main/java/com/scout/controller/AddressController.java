package com.scout.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scout.model.Address;
import com.scout.service.AddressService;
import com.scout.service.AddressServiceImpl;

/**
 * It is exposing the REST services for address
 * 
 * @author Kushagra Mathur
 *
 */
@RestController
@RequestMapping("/api/address")
public class AddressController {

	/**
	 * Interface created for: {@link AddressServiceImpl}
	 */
	private final AddressService addressService;

	@Autowired
	public AddressController(AddressService addressService) {
		this.addressService = addressService;
	}

	/**
	 * It is exposing add service for address
	 * 
	 * @param address
	 *            Storing the address into database
	 * @return {@link Long}
	 */
	@PostMapping("/add")
	public ResponseEntity<?> add(@RequestBody Address address) {
		try {
			final Long addressId = addressService.add(address);
			return ResponseEntity.ok(addressId);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}

	}

	/**
	 * It is exposing search service by address ID for address
	 * 
	 * @param addressId
	 *            It is use for searching the address
	 * @return {@link Address}
	 */
	@GetMapping("/search/{addressId}")
	public ResponseEntity<?> search(@PathVariable("addressId") Long addressId) {
		try {
			Address address = addressService.searchById(addressId);
			return ResponseEntity.ok(address);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}

	}

	/**
	 * It is exposing search service for address It is return the complete
	 * address
	 * 
	 * @return {@link List}
	 */
	@GetMapping("/search")
	public ResponseEntity<?> search() {
		try {
			List<Address> addresses = addressService.search();
			return ResponseEntity.ok(addresses);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	/**
	 * It is exposing update service for address
	 * 
	 * @param address
	 *            it is used for update the address
	 * @return {@link Long}
	 */
	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody Address address) {
		try {
			final Long addressId = addressService.update(address);
			return ResponseEntity.ok(addressId);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}

	}

	/**
	 * It is exposing delete service for address
	 * 
	 * @param addressId
	 *            its is used for deleting the address
	 * @return {@link Long}
	 */
	@DeleteMapping("/delete/{addressId}")
	public ResponseEntity<?> delete(@PathVariable("addressId") Long addressId) {
		try {
			final Long addressID = addressService.delete(addressId);
			return ResponseEntity.ok(addressID);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}
