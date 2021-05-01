package com.fama.famadesk.components.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class EncryptionHelper {
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Bean
	private BCryptPasswordEncoder getBCryptPasswordEncoder() {
		if (bCryptPasswordEncoder == null) {
			bCryptPasswordEncoder = new BCryptPasswordEncoder();
		}
		return bCryptPasswordEncoder;
	}

	public String encryptData(String data) {
		bCryptPasswordEncoder = getBCryptPasswordEncoder();
		return bCryptPasswordEncoder.encode(data);
	}

	public Boolean checkPassword(String inputData, String inputEncryptedString) {
		bCryptPasswordEncoder = getBCryptPasswordEncoder();
		return bCryptPasswordEncoder.matches(inputData, inputEncryptedString);
	}

	public Boolean checkEmailVerificationCode(String inputData, String inputEncryptedString) {
		bCryptPasswordEncoder = getBCryptPasswordEncoder();
		return bCryptPasswordEncoder.matches(inputData, inputEncryptedString);
	}

	public Boolean checkMobileVerificationCode(String inputData, String inputEncryptedString) {
		bCryptPasswordEncoder = getBCryptPasswordEncoder();
		return bCryptPasswordEncoder.matches(inputData, inputEncryptedString);
	}

}