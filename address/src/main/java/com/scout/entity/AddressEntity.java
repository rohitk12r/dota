package com.scout.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * It's holds Address details.
 *
 */
@Data
@Entity
@Table(name = "ADDRESS")
public class AddressEntity {

	/**
	 * This is primary key of Address, which is auto generation.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
