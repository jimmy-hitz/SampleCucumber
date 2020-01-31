package com.ascena.automation.extent.listeners;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import com.ascena.automation.utilities.DriverManager;
import com.ascena.automation.utilities.InitialDataSetup;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

	static ExtentReports extent;
	public static String screenshotName;
	private static String reportPath;
	static int i = 0;
	public static File destFile;

	public static synchronized String getReportPath() {
		return reportPath;
	}

	public synchronized static ExtentReports getReporter() {

		if (extent == null) {

			String reportPath = createExtentReportDirectory() + File.separator
					+ InitialDataSetup.getBrand().toUpperCase() + "_" + InitialDataSetup.getSuiteName()
					+ "AutomationReport" + ".html";

			ExtentHtmlReporter reporter = new ExtentHtmlReporter(reportPath);

			reporter.loadXMLConfig(
					new File(System.getProperty("user.dir") + "\\resources\\xml\\extenthtmlreporter.xml"));
			extent = new ExtentReports();
			extent.attachReporter(reporter);

			extent.setSystemInfo("OS", Platform.getCurrent().toString());
			extent.setSystemInfo("Host Name", "Ascena");
			extent.setSystemInfo("Environment", "QA");
			extent.setSystemInfo("User Name", System.getProperty("user.name"));

			reporter.config().setChartVisibilityOnOpen(true);
			reporter.config().setDocumentTitle("Ascena Automation Status Report");
			reporter.config().setReportName("Ascena Automation Status Report");
			reporter.config().setTestViewChartLocation(ChartLocation.TOP);
			reporter.config().setTheme(Theme.DARK);
			ExtentManager.reportPath = reportPath;

		}
		return extent;
	}

	public static void captureScreenshot() {
		i = i + 1;
		File scrFile = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.FILE);

		Date d = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("E dd MMM HH:mm:ss z yyyy");
		String strDate = formatter.format(d);
		screenshotName = strDate.replace(":", "_").replace(" ", "_") + "_" + i + ".png";
		destFile = new File(System.getProperty("user.dir") + File.separator + "reports" + File.separator + "screenshots"
				+ File.separator + screenshotName);

		try {
			FileUtils.copyFile(scrFile, destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String createExtentReportDirectory() {
		String filePath = System.getProperty("user.dir") + File.separator + "reports" + File.separator + "extentreports"
				+ File.separator + InitialDataSetup.getBrand();
		File file = new File(filePath);

		String extDir = file.exists() ? "EXTENT REPORT DIRECTORY ALREADY EXIST"
				: file.mkdir() ? InitialDataSetup.getBrand().toUpperCase() + "EXTENT REPORT DIRECTORY CREATED"
						: "COULD NOT CREATE EXTENT REPORT DIRECTORY";

		System.out.println(extDir);

		return filePath;
	}

	public static void createArchivesDirectory() {
		File file = new File(System.getProperty("user.dir") + File.separator + "reports" + File.separator
				+ "extentreports" + File.separator + "archives");
		if (!file.exists()) {
			if (file.mkdir()) {
				System.out.println("SUCCESSFULLY CREATED ARCHIVES DIRECTORY");
			} else {
				System.out.println("COULD NOT CREATE ARCHIVES DIRECTORY");
			}
		} else
			System.out.println("ARCHIVES DIRECTORY ALREADY EXIST");
	}

}