package com.fama.famadesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fama.famadesk.model.UserAddress;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Integer> {

}
