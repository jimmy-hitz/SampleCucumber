package com.ascena.automation.utilities;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Constants {
	
	public static final String TIMESTAMP = new SimpleDateFormat("_yyyy_MM_dd_HH_mm_ss").format(new Date());
	public static final String EXTENT_REPORT_PATH = System.getProperty("user.dir") + File.separator + "target"+ File.separator + "ExtentReports" + File.separator + "ExtentReportHTML"+ TIMESTAMP+".html";
	public static final String EXTENT_CONFIG_FILE_PATH= System.getProperty("user.dir") + "/src/test/resources/xml/extent-config.xml";
	public static final String BROWSER = "IE";
	public static final String BROWSER_VERSION = "v71.0";
	public static final String SELENIUM_VERSION = "v3.8.1";
	public static final String BASE_URI = "http://localhost";
	public static final int PORT = 8085;
	public static final String SITECONFIGSHEET = "SiteURL";
	public static final String SITEENVCOLHEADING = "Environment";
	public static final String SITEENVCOLVALUE = "Production";
	public static final String SHIPPINGEXCELSHEET = "ShippingAddress";
	public static final String SHIPPINGFNAME = "FirstName";
	public static final String SHIPPINGLNAME = "LastName";
	public static final String SHIPPINGADD1 = "Address1";
	public static final String SHIPPINGZIPCODE = "ZipCode";
	public static final String SHIPPINGPHONENO = "PhoneNo";
	public static final String SHIPPINGMETHOD = "ShippingMethod";
	public static final String SHIPPINGCITY = "City";
	public static final String PAYMENTEXCELSHEET = "PaymentMethod";
	public static final String PAYMENTADDRESSHEADER = "CardType";
	public static final String PAYMENTCARDNo = "CardNo";
	public static final String PAYMENTCARDMONTH = "Month";
	public static final String PAYMENTCARDYEAR = "Year";
	public static final String PAYMENTCARDCVV = "CVV";
	public static final int LONGTIMEOUT = 15;
	public static final int AVGTIMEOUT = 8;
	public static final int SHORTTIMEOUT = 5;
	public static final String GUSER ="GuestUser";
	public static final String REGUSER ="RegisteredUser";
	public static String USERTYPE =null;
	/**
	 * Defines the user directory of the environment configuration json file
	 */
	public static final String ENVIRONMENT_CONFIG_JSON = System.getProperty("user.dir")
			+ "\\resources\\json\\urls.json";
}