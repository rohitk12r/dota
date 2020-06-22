package com.scout.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scout.entity.AddressEntity;

/**
 * Functionality related to all DAO layer It is used for connection with
 * database
 * 
 * @author Kushagra Mathur
 *
 */
@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {

}
