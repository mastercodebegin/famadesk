package com.fama.famadesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fama.famadesk.model.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {

}
