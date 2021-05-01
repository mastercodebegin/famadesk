package com.fama.famadesk.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fama.famadesk.components.helper.EncryptionHelper;
import com.fama.famadesk.components.helper.JwtHelper;
import com.fama.famadesk.components.rolevalidator.IRoleValidator;
import com.fama.famadesk.constants.ResponseConstants;
import com.fama.famadesk.dao.IUserDao;
import com.fama.famadesk.enums.UserStatus;
import com.fama.famadesk.exception.BusinessValidationException;
import com.fama.famadesk.model.Country;
import com.fama.famadesk.model.User;
import com.fama.famadesk.requestobject.Login;
import com.fama.famadesk.requestobject.UserCreationRequest;
import com.fama.famadesk.service.ICountryService;
import com.fama.famadesk.service.IUserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements IUserService {

	@Autowired
	IUserDao userDao;

	@Autowired
	IRoleValidator roleValidator;

	@Autowired
	private EncryptionHelper encryptionHelper;

	@Autowired
	private ICountryService countryService;

	@Override
	public User create(User anEntity) {
		return userDao.create(anEntity);
	}

	@Override
	public User update(User anEntity) {
		return userDao.update(anEntity);
	}

	@Override
	public List<User> findAll() {
		return userDao.findAll();
	}

	@Override
	public User findByPk(Integer entityPk) {
		return userDao.findByPk(entityPk);
	}

	@Override
	public User findByEmail(String email) {
		return userDao.findByEmail(email);
	}

	@Override
	public String login(Login login, HttpServletRequest request, HttpServletResponse response) {
		String email = login.getEmail();

		User requestedUser = findByEmail(email);

		if (!encryptionHelper.checkPassword(login.getPassword(), requestedUser.getPassword())) {
			log.error("Password does not match for email: " + email);
			throw new BusinessValidationException("Your Password does not match");
		}

		if (requestedUser.isActive()) {
			String jwtToken = JwtHelper.createJwtToken(email, requestedUser.getUserid().toString(),
					requestedUser.getRoleName());
			response.setHeader("sessionid", jwtToken);
//			if (request.getHeader("device-id") != null && !request.getHeader("device-id").trim().isEmpty()) {
//				log.info("Getting device id for user :{}", requestedUser.getEmail());
//				String deviceId = request.getHeader("device-id");

//				if (requestedUser.getDeviceId() != null && !requestedUser.getDeviceId().equals(deviceId)) {
//					updateDeviceIdOfPerviouslyLoggedInUser(deviceId, requestedUser);
//				}
//
//				else if (requestedUser.getDeviceId() == null) {
//					updateDeviceIdOfPerviouslyLoggedInUser(deviceId, requestedUser);
//				}
//				requestedUser.setDeviceId(deviceId);
			// }
//			if (request.getHeader("DEVICE_OS") != null && !request.getHeader("DEVICE_OS").trim().isEmpty()) {
//				log.info("Getting DEVICE_OS for user : {}", requestedUser.getOfficialEmail());
//				requestedUser.setDeviceType(request.getHeader("DEVICE_OS"));
//			}

			requestedUser.setLastLoginTime(new Date());

			update(requestedUser);
			return jwtToken;
		}

		else {
			log.error("User account is not active." + requestedUser.getEmail());
			throw new BusinessValidationException("Your account is not active, please contact admin.");
		}

	}

	private void checkIfEmailExist(String email) {

		User user = userDao.findByEmail(email);
		if (user != null && user.getEmail() != null) {
			log.error(ResponseConstants.EMAIL_ALREADY_EXIST, email);
			throw new BusinessValidationException(ResponseConstants.EMAIL_ALREADY_EXIST);
		}
	}

	private void checkifMobileExists(String mobile) {

		User user = userDao.findByMobile(mobile);
		if (user != null && user.getMobile() != null) {
			log.error("Mobile Number : {} is already registered with us, please provide other one.", mobile);
			throw new BusinessValidationException(
					"Mobile Number is already registered with us, please provide other one.");
		}
	}

	public void preValidationChecks(UserCreationRequest userCreationRequest) {
		if (userCreationRequest.getFirstName() == null || userCreationRequest.getFirstName().isEmpty()) {
			throw new BusinessValidationException("First name is required");
		}

		if (userCreationRequest.getLastName() == null || userCreationRequest.getLastName().isEmpty()) {
			throw new BusinessValidationException("Last name is required");
		}
		if (userCreationRequest.getGender() == null || userCreationRequest.getGender().trim().isEmpty()) {
			throw new BusinessValidationException("Gender is required");
		}

		checkifMobileExists(userCreationRequest.getMobile());
		checkIfEmailExist(userCreationRequest.getEmail());
	}

	@Override
	public void createNewUser(UserCreationRequest userCreationRequest, User loggedInUser) {
		if (!roleValidator.checkIfAccessValidationRole(loggedInUser.getRoleName())) {
			throw new BusinessValidationException("Not authorised to proceed further");
		}

		preValidationChecks(userCreationRequest);
		Country country = countryService.findByPk(userCreationRequest.getCountryId());

		User user = new User();
		user.setFirstName(userCreationRequest.getFirstName());
		if (userCreationRequest.getMiddleName() != null && !userCreationRequest.getMiddleName().trim().isEmpty()) {
			user.setMiddleName(userCreationRequest.getMiddleName());
		}
		user.setLastName(userCreationRequest.getLastName());
		user.setEmail(userCreationRequest.getEmail());
		user.setMobile(userCreationRequest.getMobile());

		user.setGender(userCreationRequest.getGender());
		user.setCreatedOn(new Date());
		user.setCreatedBy(loggedInUser);
		user.setCountry(country);
		user.setActive(true);
		user.setPasswordChangeRequired(false);
		// user.setPassword(password);
		// At initial level user status will be unverified, when user accept the
		// invitation then we we will mark verified
		user.setUserStatus(UserStatus.UNVERIFIED);
		create(user);

	}

	@Override
	public List<User> getVerifiedActiveAgent(User loggedInUser) {
		if (roleValidator.validateAdminRole(loggedInUser.getRoleName())) {
			return userDao.getAllVerifiedActiveAgent();
		} else {
			return userDao.getVerifiedActiveAgent(loggedInUser.getUserid());
		}
	}

}
