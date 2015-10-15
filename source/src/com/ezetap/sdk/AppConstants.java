/********************************************************
 * Copyright (C) 2012 Ezetap Mobile Solutions Pvt. Ltd.
 * 
 * This software is distributed  on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND.
 *
 *******************************************************/

package com.ezetap.sdk;

import com.ezetap.sdk.EzeConstants.LoginAuthMode;

/**
 * App (which uses the SDK) specific configuration information.
 * 
 */

public class AppConstants {
	public static String APP_ID = "SDK_AND";
	public static String APP_NAME = "Android SDK";
	public static final String SDK_DISPLAY_VERSION = "2.0.9";
	public static final String SDK_VERSION = "9";
	public static final int COMPATIBLE_SERVICE_APP_VERSION_CODE = 116;

	// Connection Config For demo server (For prod Server - Please contact
	// Ezetap)
	// the following set of properties are set to package level access
	// intentionally
	static String BASE_PACKAGE = "com.ezetap.service.demo";
	static String APK_URL = "http://d.eze.cc/demo/app/android/ezetap_android_service";

	static final String PROD_LOGO_DOWNLOAD_BASE_URL = "http://d.eze.cc/";
	static final String PROD_BASE_PACKAGE = "com.ezetap.service.prod";
	static final String PROD_APK_URL = "https://www.ezetap.com/portal/app/android/ezetap_android_service";

	static final String DEMO_LOGO_DOWNLOAD_BASE_URL = "http://d.eze.cc/";
	static final String DEMO_BASE_PACKAGE = "com.ezetap.service.demo";
	static final String DEMO_APK_URL = "http://d.eze.cc/demo/app/android/ezetap_android_service";

	static final String PREPROD_LOGO_DOWNLOAD_BASE_URL = "http://pp.eze.cc/";
	static final String PREPROD_BASE_PACKAGE = "com.ezetap.service.preprod";
	static final String PREPROD_APK_URL = "http://pp.eze.cc/portal/app/android/ezetap_android_service";
	
	static String MODE = "DEMO";

	// Login Config
	public static final LoginAuthMode AUTH_MODE = LoginAuthMode.EZETAP_LOGIN_CUSTOM;

	// (Following Mandatory for BYPASS MODE)
	public static final String APP_KEY = ""; // also mandatory for Card Info API
	public static final String MERCHANT_NAME = "";
	public static final String CURRENCY_CODE = "INR";

	// (Following applicable for CUSTOM MODE)
	// If set to false the app will need to handle the SESSION_TIMEOUT error
	// code
	public static final boolean LOGIN_SESSION_TIME_OUT_AUTO_HANDLE = true; // Ezetap
																			// will
																			// automatically
																			// prompt
																			// for
																			// session
																			// timeout

	// Misc Config
	public static final boolean TIP_ENABLED = false;

	// Operation Codes (relevant locally only, defined for convenience)
	public static final int REQ_CODE_LOGIN = 1001;
	public static final int REQ_CODE_LOGOUT = 1002;

	public static final int REQ_CODE_PAY_CARD = 2001;
	public static final int REQ_CODE_PAY_CASH = 2002;
	public static final int REQ_CODE_VOID_TXN = 2003;
	public static final int REQ_CODE_ATTACH_SIGN = 2004;
	public static final int REQ_CODE_GET_TXN_LIST = 2005;
	public static final int REQ_CODE_GET_CARD_INFO = 2006;
	
	public static final int REQ_CODE_START_PREAUTH = 2007;
	public static final int REQ_CODE_CONFIRM_PREAUTH = 2008;
	public static final int REQ_CODE_RELEASE_PREAUTH = 2009;

	public static final int REQ_CODE_REGISTER_DONGLE = 3001;
	public static final int REQ_CODE_CHECK_UPDATE = 3002;
	public static final int REQ_CODE_CHECK_INCOMPLETE_TXN = 3003;
	public static final int REQ_CODE_INIT_DEVICE = 3004;

}