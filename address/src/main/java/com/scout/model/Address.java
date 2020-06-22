package com.scout.model;

import lombok.Data;

/**
 * Model layer for mapping with Entity
 * 
 * @author Kushagra Mathur
 *
 */
@Data
public class Address {

	/**
	 * It's holds address ID
	 */
	private Long addressId;
	/**
	 * It's holds to addressName.
	 */
	private String addressName;

	/**
	 * It's holds to city name.
	 */
	private String cityName;

	/**
	 * It's holds to country name.
	 */
	private String country;

	/**
	 * It's holds the pin code of the address.
	 */
	private Integer pinCode;
}
