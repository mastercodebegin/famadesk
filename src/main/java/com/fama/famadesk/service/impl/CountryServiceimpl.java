package com.fama.famadesk.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fama.famadesk.dao.ICountryDao;
import com.fama.famadesk.model.Country;
import com.fama.famadesk.service.ICountryService;

@Service
public class CountryServiceimpl implements ICountryService {

	@Autowired
	private ICountryDao countryDao;

	@Override
	public Country create(Country anEntity) {
		return countryDao.create(anEntity);
	}

	@Override
	public Country update(Country anEntity) {
		return countryDao.update(anEntity);
	}

	@Override
	public List<Country> findAll() {
		return countryDao.findAll();
	}

	@Override
	public Country findByPk(Integer entityPk) {
		return countryDao.findByPk(entityPk);
	}

}
