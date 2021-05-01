package com.fama.famadesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fama.famadesk.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

}
