package com.ascena.automation.utilities;

public class InitialDataSetup {

	private static String brand;
	private static String env;
	private static String browser;
	private static String suiteName;

	public static String getSuiteName() {
		return suiteName;
	}

	public static void setSuiteName(String suiteName) {
		InitialDataSetup.suiteName = suiteName;
	}

	public static String getBrand() {
		return brand;
	}

	public static void setBrand(String brand) {

			InitialDataSetup.brand = brand;
	}

	public static String getEnv() {
		return env;
	}

	public static void setEnv(String env) {

		if (System.getProperty("env") != null && !(System.getProperty("env").isEmpty()))
			InitialDataSetup.brand = System.getProperty("env");
		else
			InitialDataSetup.env = env;
	}

	public static String getBrowser() {
		return browser;
	}

	public static void setBrowser(String browser) {

		if (System.getProperty("browser") != null && !(System.getProperty("browser").isEmpty()))
			InitialDataSetup.browser = System.getProperty("browser");
		else
			InitialDataSetup.browser = browser;
	}

}
