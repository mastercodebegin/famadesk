package com.fama.famadesk.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fama.famadesk.dao.IAccountDao;
import com.fama.famadesk.exception.DataNotFoundException;
import com.fama.famadesk.model.Account;
import com.fama.famadesk.repository.AccountRepository;

@Component
public class AccountDaoImpl implements IAccountDao {

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public Account create(Account anEntity) {

		return accountRepository.save(anEntity);
	}

	@Override
	public Account update(Account anEntity) {

		return accountRepository.save(anEntity);
	}

	@Override
	public Account findByPk(Integer entityPk) {

		return accountRepository.findById(entityPk)
				.orElseThrow(() -> new DataNotFoundException("Account details not found"));
	}

	@Override
	public List<Account> findAll() {
		return accountRepository.findAll();
	}

}
