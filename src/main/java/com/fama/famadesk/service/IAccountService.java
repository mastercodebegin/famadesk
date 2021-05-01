package com.fama.famadesk.service;

import com.fama.famadesk.model.Account;
import com.fama.famadesk.model.User;
import com.fama.famadesk.requestobject.AccountSetupRequest;
import com.fama.famadesk.service.baseservice.IBaseService;

public interface IAccountService extends IBaseService<Account> {

	public void setupAccount(AccountSetupRequest acccountSetupRequest, User loggedInUser);

	public void removeAccount(Integer accountid, User loggedInUser);

}
