package com.ascena.automation.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import com.ascena.automation.extent.listeners.ExtentManager;
import com.ascena.automation.extent.listeners.ExtentTestManager;
import com.ascena.lb.stepsdefinitions.PageObjects;
import com.aventstack.extentreports.Status;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

/**
 * Hooks implementation class for Cucumber Steps defined in Feature file
 */

public class Hooks {

	static volatile int x = 0;

	@Before
	public void setUpScenario(Scenario scenario) {

		System.out.println("***** FEATURE FILE :-- "
				+ Utility.featureFileName(scenario.getId().replace("/", "-").split("-")[1].split("[.]")[0])
				+ " --: *****");
		System.out.println("---------- Scenario Name :-- " + scenario.getName() + "----------");
		System.out.println("---------- Scenario Execution Started at " + Utility.getCurrentTime() + "----------");

		BasePage2.message = scenario;

		ExtentTestManager.startTest("Scenario No . " + (x = x + 1) + " : " + scenario.getName());
		ExtentTestManager.getTest().log(Status.INFO, "Scenario No . " + x + " Started : - " + scenario.getName());

		Utility.setupAUTTestRecorder();

		// --------- Opening Browser() before every test case execution for the
		// URL given in Feature File. ---------
		DriverUtils.getInstance().getBrowserInstantiation();

		PageObjects pageObj = new PageObjects();

	}

	@After
	public void afterScenario(Scenario scenario) {
		String videoFile = Utility.stopAUTTestRecorder();
		try {
			if (scenario.isFailed()) {
				ExtentTestManager.addScreenShotsOnFailure();
				// ------------------------- Attaching video in the Report
				// -------------------------
				String failedTestVideo = "<a href=\"" + videoFile + "\">Download Failed Test Case Video</a>";
				ExtentTestManager.embedVideoLink(failedTestVideo);
				System.out.println("***** Scenario Execution is - " + scenario.getStatus().firstLetterCapitalizedName()
						+ " (Completed) : at " + Utility.getCurrentTime() + " *****");
				DriverUtils.getInstance().quit();
			} else {
				if (ExtentTestManager.getTest().getStatus().toString().equalsIgnoreCase("pass")) {
					ExtentTestManager.scenarioPass();
					System.out.println(
							"***** Scenario Execution is - " + scenario.getStatus().firstLetterCapitalizedName()
									+ " (Completed) : at " + Utility.getCurrentTime() + " *****");
					DriverUtils.getInstance().quit();
				} else if (ExtentTestManager.getTest().getStatus().toString().equalsIgnoreCase("warning")) {
					ExtentTestManager.scenarioWarning();
					System.out.println(
							"***** Scenario Execution is - " + scenario.getStatus().firstLetterCapitalizedName()
									+ " (Completed) : at " + Utility.getCurrentTime() + " *****");
					DriverUtils.getInstance().quit();
				}
			}

		} catch (Exception e) {
			scenario.write("WARNING. Failed to take screenshot with following exception : " + e.getMessage());
			System.err.println("WARNING. Failed to take screenshot with following exception : " + e.getMessage());
		}
	}

	public static void zipExtentReport() {
		try {
			File file = new File(ExtentManager.getReportPath());

			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");

			ExtentManager.createArchivesDirectory();

			String zipFileName = System.getProperty("user.dir") + File.separator + "reports" + File.separator
					+ "extentreports" + File.separator + "archives" + File.separator + InitialDataSetup.getBrand()
					+ formater.format(calendar.getTime()) + ".zip";

			addToZipFile(file, zipFileName);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void addToZipFile(File file, String zipFileName) throws FileNotFoundException, IOException {

		FileOutputStream fos = new FileOutputStream(zipFileName);
		ZipOutputStream zos = new ZipOutputStream(fos);

		ZipEntry zipEntry = new ZipEntry(file.getName());
		zos.putNextEntry(zipEntry);

		FileInputStream fis = new FileInputStream(file);

		byte[] bytes = new byte[1024];
		int length;
		while ((length = fis.read(bytes)) > 0) {
			zos.write(bytes, 0, length);
		}
		zos.closeEntry();
		zos.close();
		fis.close();
		fos.close();
		System.out.println(file.getCanonicalPath() + " is zipped to " + zipFileName);
	}
}