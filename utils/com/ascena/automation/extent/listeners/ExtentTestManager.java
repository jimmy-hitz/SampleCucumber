package com.ascena.automation.extent.listeners;

import java.io.IOException;

import com.ascena.automation.utilities.BasePage;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class ExtentTestManager {

	public static ThreadLocal<ExtentTest> testReport = new ThreadLocal<ExtentTest>();
	static ExtentReports extent = ExtentManager.getReporter();

	public static synchronized ExtentTest getTest() {
		return testReport.get();
	}

	public static synchronized void setTest(ExtentTest tst) {
		testReport.set(tst);
	}

	public static synchronized void logInfo(String message) {

		testReport.get().info(message);
	}

	public static synchronized void logPass(String message) {

		testReport.get().pass(message);
	}

	public static synchronized void scenarioPass() {

		String passLogg = "SCENARIO PASSED";
		Markup m = MarkupHelper.createLabel(passLogg, ExtentColor.GREEN);
		testReport.get().log(Status.PASS, m);
	}

	public static synchronized void scenarioWarning() {

		String warnLogg = "SCENARIO PASSED WITH WARNING";
		Markup m = MarkupHelper.createLabel(warnLogg, ExtentColor.ORANGE);
		testReport.get().log(Status.WARNING, m);
	}

	public static synchronized void logFail(String message) {
		try {
			testReport.get()
					.fail("<details>" + "<summary>" + "<b>" + "<font color=" + "red>"
							+ "Exception Occured : Click on the link to see message" + "</font>" + "</b >"
							+ "</summary>" + "<br>" + "<h6>" + "<b>" + BasePage.returnLocator(message) + "</b>"
							+ "</h6>" + "</br>" + message.replaceAll(",", "<br>") + "</details>" + " \n");
		} catch (Exception e) {
		}
	}

	public static synchronized void logFailed(String message) {
		try {
			testReport.get()
					.fail("<details>" + "<summary>" + "<b>" + "<font color=" + "red>"
							+ "Exception Occured : Click on the link to see message" + "</font>" + "</b >"
							+ "</summary>" + "<br>" + "<h6>" + "<b>" + BasePage.returnLocator(message) + "</b>"
							+ "</h6>" + "</br>" + message.replaceAll(",", "<br>") + "</details>" + " \n");
			addScreenShotsOnFailure();
		} catch (Exception e) {
		}
	}

	public static synchronized void logWarning(String message) {
		try {
			testReport.get()
					.warning("<details>" + "<summary>" + "<b>" + "<font color=" + "orange>"
							+ "Exception Occured : Click on the link to see message" + "</font>" + "</b >"
							+ "</summary>" + "<br>" + "<h6>" + "<b>" + BasePage.returnLocator(message) + "</b>"
							+ "</h6>" + "</br>" + message.replaceAll(",", "<br>") + "</details>" + " \n");
			addWarningScreenShotsOnFailure();
		} catch (Exception e) {
		}
	}

	public static synchronized boolean addWarningScreenShotsOnFailure() {

		ExtentManager.captureScreenshot();
		try {

			testReport.get().warning("<b>" + "<font color=" + "orange>" + "Screenshot of failure" + "</font>" + "</b>",
					MediaEntityBuilder.createScreenCaptureFromPath(ExtentManager.screenshotName).build());
		} catch (IOException e) {

		}
		return true;
	}

	public static synchronized void embedVideoLink(String message) {
		try {
			testReport.get().fail("<b>" + "<font color=" + "red>" + message + "</font>" + "</b>");
		} catch (Exception e) {
		}
	}

	public static synchronized void stepFail(String message) {
		try {
			testReport.get().fail("<b>" + "<font color=" + "red>" + message + "</font>" + "</b>");
		} catch (Exception e) {
		}
	}

	public static synchronized boolean addScreenShotsOnFailure() {

		ExtentManager.captureScreenshot();
		try {
			testReport.get().fail("<b>" + "<font color=" + "red>" + "Screenshot of failure" + "</font>" + "</b>",
					MediaEntityBuilder.createScreenCaptureFromPath(ExtentManager.destFile.toString()).build());
		} catch (IOException e) {

		}

		String failureLogg = "SCENARIO FAILED";
		Markup m = MarkupHelper.createLabel(failureLogg, ExtentColor.RED);
		testReport.get().log(Status.FAIL, m);
		return true;
	}

	public static synchronized boolean addScreenShotsOnBAMSErrorMessage() {

		ExtentManager.captureScreenshot();
		try {

			testReport.get().fail(
					"<b>" + "<font color=" + "red>" + "Screenshot of BAMS Error Message" + "</font>" + "</b>",
					MediaEntityBuilder.createScreenCaptureFromPath(ExtentManager.screenshotName).build());
		} catch (IOException e) {

		}
		return true;
	}

	public static synchronized boolean addScreenShots() {

		ExtentManager.captureScreenshot();
		try {
			testReport.get().info(("<b>" + "<font color=" + "green>" + "Screenshot" + "</font>" + "</b>"),
					MediaEntityBuilder.createScreenCaptureFromPath(ExtentManager.screenshotName).build());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}

	public static synchronized ExtentTest startTest(String testName) {
		return startTest(testName, "");
	}

	public static synchronized ExtentTest startTest(String testName, String desc) {
		ExtentTest test = extent.createTest(testName, desc);
		testReport.set(test);
		return test;
	}

}