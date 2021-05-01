package com.fama.famadesk.components.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.apache.commons.lang3.text.WordUtils;

import com.fama.famadesk.constants.ApplicationConstants;
import com.fama.famadesk.exception.BusinessValidationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApplicationUtil extends ApplicationConstants {
	private static final String GENERATE_STR = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	private static final String CAPITAL_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String SMALL_CHARS = "abcdefghijklmnopqrstuvwxyz";
	private static final String NUMBERS = "0123456789";
	private static final String SYMBOLS = "@%+'!#$^?(){}.[]~-_/\\";
	private static final String EMAIL_REGEX = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

	private static Random rndm_method = new Random();
	private static SecureRandom alphaNumbericRandom = new SecureRandom();

	private static char[] getPassword(String inputString, int length) {
		char[] password = new char[length];

		for (int i = 0; i < length; i++) {
			password[i] = inputString.charAt(rndm_method.nextInt(inputString.length()));
		}
		return password;
	}

	public static String generatePassword() {
		char[] ca_chars = getPassword(CAPITAL_CHARS, 2);
		char[] sm_chars = getPassword(SMALL_CHARS, 2);
		char[] num_chars = getPassword(NUMBERS, 2);
		char[] sys_chars = getPassword(SYMBOLS, 2);

		String cs_String = new String(ca_chars);
		String num_String = new String(num_chars);
		String sys_String = new String(sys_chars);
		String sm_String = new String(sm_chars);

		return cs_String + num_String + sys_String + sm_String;
	}

	// This method will be used to check email validation where email is optional in
	// request
	// Since it is optional, we cannot use annotation therefore should use method
	public static void checkEmail(String email) {
		if (email != null) {
			Pattern pattern = Pattern.compile(EMAIL_REGEX);

			Matcher matcher = pattern.matcher(email);
			boolean match = matcher.matches();

			if (!match) {
				throw new BusinessValidationException("Please enter valid email");
			}
		}
	}

	public static final String getStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		return (sw.toString());
	}

	public static String getRandomSecurityPin() {
		Random rand = new Random();
		int min = 1000;
		int max = 9999;
		Integer randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum.toString();
	}

	public static String getRandomString(int len) {
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(GENERATE_STR.charAt(alphaNumbericRandom.nextInt(GENERATE_STR.length())));
		return sb.toString();
	}

	public static String getQrCodeToken() {
		return getRandomString(6);
	}

	public static String getSmsText(String textStr, Map<String, String> values) {
		StrSubstitutor strSub = new StrSubstitutor(values, SMS_STARTING_FLAG, SMS_ENDING_FLAG);
		return strSub.replace(textStr);
	}

	public static String convertImageToByteArray(InputStream decodedInput) {
		byte[] bytes = null;
		try {
			bytes = IOUtils.toByteArray(decodedInput);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (bytes == null) {
			// returning null if any error occurred while converting to byte
			return null;
		}

		return Base64.getEncoder().encodeToString(bytes);
	}

	public static boolean isNumberic(String value) {
		return NumberUtils.isDigits(value);
	}

	public static String toCamelCase(String value) {
		if (value != null) {
			return WordUtils.capitalizeFully(value);
		}

		return value;
	}

	public static String removeAngleBraces(String string) {
		int start = string.indexOf("<");
		int end = string.indexOf(">");
		String trimString = string.substring(start + 1, end);
		return trimString;
	}

	public static String generateTickedId() {
		Random r = new Random();
		int low = 100;
		int high = 1000;
		Integer result = r.nextInt(high - low) + low;
		String ticketAbbrevaition = "TEST";
		String convertToString = String.valueOf(result);
		String uniqueId = ticketAbbrevaition.concat(convertToString);
		return uniqueId;
	}

}
