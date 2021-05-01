package com.fama.famadesk.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fama.famadesk.dao.ICountryDao;
import com.fama.famadesk.exception.DataNotFoundException;
import com.fama.famadesk.model.Country;
import com.fama.famadesk.repository.CountryRepository;

@Component
public class CountryDaoImpl implements ICountryDao {

	@Autowired
	private CountryRepository countryRepository;

	@Override
	public Country create(Country anEntity) {
		return countryRepository.save(anEntity);
	}

	@Override
	public Country update(Country anEntity) {
		return countryRepository.save(anEntity);
	}

	@Override
	public Country findByPk(Integer entityPk) {
		return countryRepository.findById(entityPk)
				.orElseThrow(() -> new DataNotFoundException("Country details not found"));
	}

	@Override
	public List<Country> findAll() {
		return countryRepository.findAll();
	}

}
