package com.fama.famadesk.constants;

import java.math.BigDecimal;

public class ApplicationConstants
{
	public static final String FAMA_LOGO_RELATIVEPATH = "/assets/images/logo/famacash_logo_grey.png";
	public static final String FAMA_GOOGLEPLAY_RELATIVEPATH = "/assets/images/app_icon/googleplay.png";
	public static final String FAMA_APPSTORE_RELATIVEPATH = "/assets/images/app_icon/appstore.png";
	public static final String FAMA_WALLET_RELATIVEPATH = "/assets/images/app_icon/wallet.png";
	public static final String FAMA_BILLPAYMENT_RELATIVEPATH = "/assets/images/app_icon/bill_pay.png";
	public static final String FAMA_QRCODE_RELATIVEPATH = "/assets/images/app_icon/qrcode.png";

	public static final String FEATURE_ICON_URL = "/assets/images/app_icon/feature.jpg";
	public static final String FACEBOOK_ICON_URL = "/assets/images/app_icon/fb.png";
	public static final String TWITTER_ICON_URL = "/assets/images/app_icon/twt.png";
	public static final String LINKEDIN_ICON_URL = "/assets/images/app_icon/lkdn.png";
	
	public static final String BANK_API_MODE_TEST = "TEST";
	public static final String TEST = "TEST";

	
	public static final String FILENAME_SEPARATOR = "-";
	

	public static final String HYPHEN = "-";
	public static final String SPACE = " ";

	public static final String DOT = ".";


	public static final String FX_RATE_ONE_SENDER_VALUE = "1.00 ";
	public static final String FX_RATE_EQUAL = " = ";
	public static final String ADDRESS_SEPARATOR = ", ";

	public static final BigDecimal DIVISOR_FOR_PERCENT = new BigDecimal(100);
	public static final int CURRENCY_CONVERTING_ROUNDING_SCALE = 3;
	public static final int CURRENCY_ROUNDING_SCALE = 2;
	public static final int CURRENCY_APP_ROUNDING_MODE = BigDecimal.ROUND_UP;
	public static final int CURRENCY_ROUNDING_MODE = BigDecimal.ROUND_DOWN;
	public static final BigDecimal FX_CONVERSION_ERROR_SAFETY_VALUE = new BigDecimal("0.50");
	public static final String CURRENCY_ROUNDING_DELIMITER = "%.2f";
	
	public static final int CANCELLATION_WINDOW_IN_MINS = 30;
	
	public static final String APPLICATION_WALLET_DISPLAY_NAME = "Wallet";
	
	public static final String DEFAULT_TIME_ZONE = "GMT";

	
	//SMS
	public static final String SMS_STARTING_FLAG = "$(";
	public static final String SMS_ENDING_FLAG = ")";
	
	
	//Application different config modes
	public static final String MAIL_TEST_MODE = "TEST";
	public static final String TRANS_ID_GENERATION_TEST_MODE = "TEST";
	public static final String SUPPORT_AND_CONTACT_MAIL_MODE_PROD = "PROD";
	public static final String NOTIFY_TECHNICAL_TEAM_MAIL_MODE_PROD = "PROD";
	public static final String WALLET_ACTIVATION_REQUEST_MAIL_MODE_PROD = "PROD";
	public static final String DOCUMENT_STORAGE_TYPE_SERVER = "SERVER";
	public static final String DOCUMENT_STORAGE_TYPE_SERVER_AWS_S3 = "AWS_S3";
	public static final String FILE_FRONT_SIDE = "FRONT_SIDE-";
	public static final String FILE_BACK_SIDE = "BACK_SIDE-";


	//Mail Host values
	public static final String MAIL_HOST_FAMA_APP = "FAMA_APP";
	public static final String MAIL_TEST_AWS_SES = "AWS_SES";
		
	//QR code app constant
	public static final String QR_CODE_APP_TOKEN = "FamacashQrCode";

	//Airtime failed generic DB msg
	public static final String AIRTIME_TOPUP_FAILED_MSG = "Airtime Failed";

	
	//SSL wireless constant
	public static final String APP_SSL_TRANS_ID_PREFIX = "FAMA";
	
	public static final String BANGLADESH_CURRENCY_CODE = "BDT";
	//In our database, rupali bank PK ID is 49

	public static final Integer RUPALI_BANK_DB_ID = 49;
	public static final String CBW_DEFAULT_PAYER_ID = "999";
	

	
	public static String getRelativePathOfFAMALogo()
	{
		return FAMA_LOGO_RELATIVEPATH;
	}
}
