package com.ascena.automation.utilities;

import java.util.logging.Level;

import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class DriverFactory {

	private static String configPropertyFilePath;
	private static String gridPath;
	private static boolean isLocalExecution;
	private DesiredCapabilities desiredCapabilities;
	private LoggingPreferences loggingPreferences;

	DesiredCapabilities getDesiredCapabilities() {
		loggingPreferences = new LoggingPreferences();
		loggingPreferences.enable(LogType.BROWSER, Level.OFF);
	    loggingPreferences.enable(LogType.CLIENT, Level.OFF);
	    loggingPreferences.enable(LogType.DRIVER, Level.OFF);
	    loggingPreferences.enable(LogType.PERFORMANCE, Level.OFF);
	    loggingPreferences.enable(LogType.PROFILER, Level.OFF);
	    loggingPreferences.enable(LogType.SERVER, Level.OFF);
		
		if (Utility.sJSONReader("platFormName").equalsIgnoreCase("android") || Utility.sJSONReader("platFormName").equalsIgnoreCase("ios")) {
			return getMobileDesiredCapabilities();
		} else {
			return getDesktopDesiredCapabilities();
		}
	}

	private DesiredCapabilities getDesktopDesiredCapabilities() {
		System.out.println("Getting Desktop Capabilities");
		desiredCapabilities = new DesiredCapabilities();
		desiredCapabilities.setCapability(CapabilityType.LOGGING_PREFS, loggingPreferences);
		return desiredCapabilities;
	}

	private DesiredCapabilities getMobileDesiredCapabilities() {
		System.out.println("Getting Mobile Capabilities");
		
		desiredCapabilities = new DesiredCapabilities();
		desiredCapabilities.setCapability("platformName", Utility.sJSONReader("platFormName"));
		desiredCapabilities.setCapability("deviceName", Utility.sJSONReader("device"));
		desiredCapabilities.setCapability("platformVersion", Utility.sJSONReader("platFormVersion"));
		desiredCapabilities.setCapability(CapabilityType.BROWSER_NAME, Utility.sJSONReader("browser"));
		desiredCapabilities.setCapability(CapabilityType.LOGGING_PREFS, loggingPreferences);
		
		if (Utility.sJSONReader("plateFormName").equalsIgnoreCase("android")
				&& Utility.sJSONReader("plateFormName").equalsIgnoreCase("chrome")) {
			desiredCapabilities.setCapability("bundleId", "com.android.chrome");
		}
		return desiredCapabilities;
	}

	public static String getGridPath() {
		return gridPath;
	}

	public static void setGridPath(String gridPath) {
		DriverFactory.gridPath = gridPath;
	}

	public static String getConfigPropertyFilePath() {
		return configPropertyFilePath;
	}

	public static void setConfigPropertyFilePath(String configPropertyFilePath) {
		DriverFactory.configPropertyFilePath = configPropertyFilePath;
	}

	public static boolean isLocalExecution() {
		return isLocalExecution;
	}

	public static void setLocalExecution(boolean localExecution) {
		DriverFactory.isLocalExecution = localExecution;
	}
}