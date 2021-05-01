package com.fama.famadesk.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fama.famadesk.components.rolevalidator.IRoleValidator;
import com.fama.famadesk.components.util.ApplicationUtil;
import com.fama.famadesk.dao.IAccountDao;
import com.fama.famadesk.exception.BusinessValidationException;
import com.fama.famadesk.model.Account;
import com.fama.famadesk.model.GroupDetails;
import com.fama.famadesk.model.ProjectDetails;
import com.fama.famadesk.model.User;
import com.fama.famadesk.model.UserGroup;
import com.fama.famadesk.requestobject.AccountSetupRequest;
import com.fama.famadesk.service.IAccountService;
import com.fama.famadesk.service.IGroupDetailsService;
import com.fama.famadesk.service.IProjectDetailsService;
import com.fama.famadesk.service.IUserGroupService;

@Service
public class AccountServiceImpl implements IAccountService {

	@Autowired
	private IAccountDao accountDao;

	@Autowired
	private IProjectDetailsService projectDetailsService;

	@Autowired
	private IRoleValidator roleValidator;

	@Autowired
	private IGroupDetailsService groupDetailsService;

	@Autowired
	private IUserGroupService userGroupService;

	@Override
	public Account create(Account anEntity) {
		return accountDao.create(anEntity);
	}

	@Override
	public Account update(Account anEntity) {
		return accountDao.update(anEntity);
	}

	@Override
	public List<Account> findAll() {
		return accountDao.findAll();
	}

	@Override
	public Account findByPk(Integer entityPk) {
		return accountDao.findByPk(entityPk);
	}

	public void preValidationCheckToSetupAccount(AccountSetupRequest acccountSetupRequest, User loggedInUser) {
		if (!roleValidator.checkIfAccessValidationRole(loggedInUser.getRoleName())) {
			throw new BusinessValidationException("Not authorised to proceed further");
		}

		if (acccountSetupRequest.getProjectList() == null || acccountSetupRequest.getProjectList().isEmpty()) {
			throw new BusinessValidationException("Project is required");
		}

		if (acccountSetupRequest.getAccountName() == null || acccountSetupRequest.getAccountName().trim().isEmpty()) {
			throw new BusinessValidationException("Account name is required");

		}

		if (acccountSetupRequest.getOrganizationName() == null
				|| acccountSetupRequest.getOrganizationName().trim().isEmpty()) {
			throw new BusinessValidationException("Organization name is required");

		}
	}

	@Override
	public void setupAccount(AccountSetupRequest acccountSetupRequest, User loggedInUser) {

		preValidationCheckToSetupAccount(acccountSetupRequest, loggedInUser);

		Account account = new Account();
		account.setAccountName(acccountSetupRequest.getAccountName());
		account.setOrganizationName(acccountSetupRequest.getOrganizationName());
		account.setCreatedOn(new Date());
		account.setCreatedBy(loggedInUser);
		account.setUuid(ApplicationUtil.getRandomString(6));
		create(account);

		for (Integer projectId : acccountSetupRequest.getProjectList()) {
			ProjectDetails projectDetails = projectDetailsService.findByPk(projectId);
			projectDetails.setAccountAssociated(account);
			projectDetailsService.update(projectDetails);

		}
	}

	@Override
	public void removeAccount(Integer accountid, User loggedInUser) {
		if (!roleValidator.checkIfAccessValidationRole(loggedInUser.getRoleName())) {
			throw new BusinessValidationException("Not authorised to proceed further");
		}
		Account account = findByPk(accountid);
		account.setRemoved(true);
		account.setUpdatedOn(new Date());
		account.setUpdatedBy(loggedInUser);
		update(account);

		List<ProjectDetails> associateProjectList = projectDetailsService.getProjectsByAccountId(account.getId(),
				loggedInUser);

		if (!associateProjectList.isEmpty()) {
			for (ProjectDetails projectDetails : associateProjectList) {

				// Purposely we have put condition here because we are fetching those project
				// whose end date is null and if we will put this condition anywhere else then
				// we will get empty group list as project's end date will be persisted after
				// updation
				List<GroupDetails> associateGroupList = groupDetailsService.getGroupByProjectId(projectDetails.getId(),
						loggedInUser);

				projectDetails.setEndDate(new Date());
				projectDetails.setComment("Respective account is removed i.e project is also removed");
				projectDetailsService.update(projectDetails);

				if (!associateGroupList.isEmpty()) {
					for (GroupDetails groupDetails : associateGroupList) {
						groupDetails.setRemoved(true);
						groupDetails.setUpdatedOn(new Date());
						groupDetailsService.update(groupDetails);

						List<UserGroup> associateUserGroupList = userGroupService
								.getUserGroupDetailByGroupid(groupDetails.getId(), loggedInUser);

						if (!associateUserGroupList.isEmpty()) {
							for (UserGroup userGroup : associateUserGroupList) {
								userGroup.setUserRemoved(true);
								userGroup.setUpdatedOn(new Date());
								userGroup.setUpdatedBy(loggedInUser);
								userGroupService.update(userGroup);

							}
						}

					}
				}

			}

		}

	}

}
