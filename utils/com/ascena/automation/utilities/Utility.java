package com.ascena.automation.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import atu.testrecorder.ATUTestRecorder;
import atu.testrecorder.exceptions.ATUTestRecorderException;

public class Utility {

	// ---------------------------- Declaration & Initialization of Variables
	// ---------------------------------
	private static String sJSONFilePath = "./resources/json/ProductionTestData.json";
	private static final String RUN_CONFIG_JSON = "./resources/json/RestAPIResources.json";
	public static ATUTestRecorder recorder = null;
	static String videoFolder, videoFile, originalVideoFilePath;
	// ---------------------------------------------------------------------------------------------------------

	// ------------------------------ Method() To Capture Screenshot
	// ------------------------------
	public static String getScreenshot(String screenshotName) {
		String dateName = new SimpleDateFormat("_yyyy_MM_dd_HH_mm_ss").format(new Date());
		String destination = System.getProperty("user.dir") + "/target/FailedTestsScreenshots/" + screenshotName
				+ dateName + ".png";
		try {
			File sourceFile = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.FILE);
			File destinationPath = new File(destination);
			FileUtils.copyFile(sourceFile, destinationPath);
		} catch (Exception e) {
			System.err.println("Error occured during capturing screenshot" + e.getMessage());
		}
		return destination;
	}

	// ---------------------------------- Method() Returns Utility Class Object
	// -----------------------------
	public static Utility getInstance() {
		Utility runTimeInstance = null;
		if (runTimeInstance == null) {
			runTimeInstance = new Utility();
		}
		return runTimeInstance;
	}

	public static void setupAUTTestRecorder() {
		videoFolder = createATUVideoDirectory();
		videoFile = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());

		try {
			recorder = new ATUTestRecorder(videoFolder, videoFile, false);
			recorder.start();
			System.out.println("---------------- ATU Test Recording Started ----------------");
		} catch (ATUTestRecorderException e) {
			e.printStackTrace();
			System.err.println("ATU Test Recorder not setup successfully");
		}
	}

	public static String stopAUTTestRecorder() {
		String prefix = "file:///", completeVideoPath = null;
		try {
			recorder.stop();
			completeVideoPath = videoFolder + File.separator + videoFile + ".mov";
			originalVideoFilePath = completeVideoPath;
			completeVideoPath = completeVideoPath.replace("\\", "\\\\");
			System.out.println("---------------- ATU Test Recording Stopped ----------------");
		} catch (ATUTestRecorderException e) {
			System.err.println("ATU Test Recorder not stopped successfully");
		}
		return prefix.concat(completeVideoPath);
	}

	public static String createATUVideoDirectory() {

		String videoPath = System.getProperty("user.dir") + File.separator + "reports" + File.separator + "videos"
				+ File.separator + InitialDataSetup.getBrand().toUpperCase();
		File file = new File(videoPath);
		if (!file.exists()) {
			System.out.println("CREATING BRAND WISE VIDEO DIRECTORY");
			if (file.mkdir()) {
				System.out.println("SUCCESSFULLY CREATED BRAND WISE VIDEO DIRECTORY");
			} else {
				System.out.println("COULD NOT CREATE BRAND WISE VIDEO DIRECTORY");
			}
		} else
			System.out.println("BRAND WISE VIDEO DIRECTORY ALREADY EXIST");

		return videoPath;
	}

	// ------------------------------ Method() To Read Data From JSON
	// ------------------------------
	public static String sJSONReader(String sKey) {
		JSONParser parser = new JSONParser();
		try {
			Object object = parser.parse(new FileReader(sJSONFilePath));
			JSONObject jsonObject = (JSONObject) object;
			sKey = (String) jsonObject.get(sKey);
		} catch (FileNotFoundException e) {
			System.err.println("[ERROR]: configuration file not found :" + e.getMessage());
		} catch (Exception e) {
			System.err.println("[ERROR]: Exception occured while reading JSON File Path :" + e.getMessage());
		}
		return sKey;
	}

	public static int getJSONDatAsInt(int sKey) {
		JSONParser parser = new JSONParser();
		try {
			Object object = parser.parse(new FileReader(sJSONFilePath));
			JSONObject jsonObject = (JSONObject) object;
			sKey = (int) jsonObject.get(sKey);
		} catch (FileNotFoundException e) {
			System.err.println("[ERROR]: configuration file not found :" + e.getMessage());
		} catch (Exception e) {
			System.err.println("[ERROR]: Exception occured while reading JSON File Path :" + e.getMessage());
		}
		return sKey;
	}

	// ------------------------------ Method() To Read Data From JSON
	// ------------------------------
	public static String dataAPIReaderJSON(String sKey) {
		JSONParser parser = new JSONParser();
		try {
			Object object = parser.parse(new FileReader(RUN_CONFIG_JSON));
			JSONObject jsonObject = (JSONObject) object;
			sKey = (String) jsonObject.get(sKey);
		} catch (FileNotFoundException e) {
			System.err.println("[ERROR]: configuration file not found :" + e.getMessage());
		} catch (Exception e) {
			System.err.println("[ERROR]: Exception occured while reading JSON File Path :" + e.getMessage());
		}
		return sKey;
	}

	public static double startTime() {
		double starttime = System.currentTimeMillis();
		return starttime;
	}

	public static String endTime(double starttime) {
		double lasttime = System.currentTimeMillis();
		double diff = (lasttime - starttime) / 1000;
		String meanTime = String.format("%.02f", diff);
		return meanTime;
	}

	public static String featureFileName(String rawFeatureFile) {
		String featureName = "";
		featureName = featureName + rawFeatureFile.substring(0, 1).toUpperCase()
				+ rawFeatureFile.substring(1).toUpperCase();
		return featureName;
	}

	public static String getCurrentTime() {
		return new SimpleDateFormat("HH:mm:ss [dd MMMM yyyy]").format(new Date());
	}

	static String currentDir = System.getProperty("user.dir");
	static String excelPath = currentDir + File.separator + "resources" + File.separator + "input" + File.separator
			+ "ProductionTestData.xlsx";
	static XSSFSheet ExcelWSheet;
	static FileInputStream ExcelFile;
	static XSSFWorkbook ExcelWBook;

	public static XSSFSheet getExcelWSheet() {
		return ExcelWSheet;
	}

	public static void setExcelWSheet(String sheetName) throws Exception {
		try {
			File file = new File(excelPath);
			ExcelFile = new FileInputStream(file);
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(sheetName.trim());
		} catch (Exception e) {
			throw (e);
		}
	}

	static XSSFCell Cell;

	public synchronized static String AccessExcelDataSheet(String SheetName, int RowNum, int ColNum) throws Exception {
		String CellData = null;

		try {
			File file = new File(excelPath);
			FileInputStream ExcelFile = new FileInputStream(file);
			XSSFWorkbook ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			CellData = Cell.getStringCellValue();
			ExcelWBook.close();
			ExcelFile.close();
			return CellData;

		} catch (Exception e) {
			throw (e);
		}
	}

	public synchronized static String RetrieveTestDataFromExcel(String SheetName, String featureFileString)
			throws Exception {
		String testDataFromExcel = null;

		try {
			File file = new File(excelPath);
			FileInputStream ExcelFile = new FileInputStream(file);
			XSSFWorkbook ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			int noOfColumns = ExcelWSheet.getRow(0).getLastCellNum();
			String[] Headers = new String[noOfColumns];
			for (int i = 0; i < noOfColumns; i++) {
				Headers[i] = ExcelWSheet.getRow(0).getCell(i).toString();
			}

			for (int a = 0; a < noOfColumns; a++) {
				if ((Headers[a].trim()).equalsIgnoreCase(featureFileString)) {
					testDataFromExcel = ExcelWSheet.getRow(1).getCell(a).getStringCellValue();
					testDataFromExcel = testDataFromExcel.trim();
					break;
				}
			}

			ExcelWBook.close();
			ExcelFile.close();
			return testDataFromExcel;

		} catch (Exception e) {
			throw (e);
		}
	}

	public synchronized static String getDataAsStringByRow(String SheetName, int rowNumber, String columnName)
			throws Exception {
		String testDataFromExcel = null;
		DataFormatter formatter = new DataFormatter();

		try {
			File file = new File(excelPath);
			FileInputStream ExcelFile = new FileInputStream(file);
			XSSFWorkbook ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			int noOfColumns = ExcelWSheet.getRow(0).getLastCellNum();
			String[] Headers = new String[noOfColumns];
			for (int i = 0; i < noOfColumns; i++) {
				Headers[i] = ExcelWSheet.getRow(0).getCell(i).getStringCellValue();
			}

			for (int a = 0; a < noOfColumns; a++) {
				if ((Headers[a].trim()).equalsIgnoreCase(columnName)) {
					testDataFromExcel = formatter.formatCellValue(ExcelWSheet.getRow(rowNumber).getCell(a));
					testDataFromExcel = testDataFromExcel.trim();
					break;
				}
			}

			ExcelWBook.close();
			ExcelFile.close();
			return testDataFromExcel;

		} catch (Exception e) {
			throw (e);
		}
	}

	public synchronized static long getDataAsIntegerViaRow(String SheetName, int rowNumber, String columnName)
			throws Exception {
		long testData = 0;

		try {
			File file = new File(excelPath);
			FileInputStream ExcelFile = new FileInputStream(file);
			XSSFWorkbook ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			int noOfColumns = ExcelWSheet.getRow(0).getLastCellNum();
			String[] Headers = new String[noOfColumns];
			for (int i = 0; i < noOfColumns; i++) {
				Headers[i] = ExcelWSheet.getRow(0).getCell(i).getStringCellValue();
			}

			for (int a = 0; a < noOfColumns; a++) {
				if ((Headers[a].trim()).equalsIgnoreCase(columnName)) {
					testData = (int) ExcelWSheet.getRow(rowNumber).getCell(a).getNumericCellValue();
					break;
				}
			}

			ExcelWBook.close();
			ExcelFile.close();
			return testData;

		} catch (Exception e) {
			throw (e);
		}
	}

	public synchronized static String getDataAsString(String eWBookName, String eWBookColHeading,
			String cucumberTestDataRef, String expectedTestData) {
		String actualTestData = null;
		int rowNumber = 0;
		try {
			File file = new File(excelPath);
			FileInputStream ExcelFile = new FileInputStream(file);
			XSSFWorkbook ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(eWBookName.trim());
			int rowCount = ExcelWSheet.getPhysicalNumberOfRows();
			int noOfColumns = ExcelWSheet.getRow(0).getLastCellNum();
			String[] Headers = new String[noOfColumns];
			for (int i = 0; i < noOfColumns; i++) {
				Headers[i] = ExcelWSheet.getRow(0).getCell(i).getStringCellValue();
			}

			outerloop: for (int a = 0; a < noOfColumns; a++) {
				if ((Headers[a].trim()).equalsIgnoreCase(eWBookColHeading.trim())) {
					for (int i = 1; i <= rowCount; i++) {
						Cell = ExcelWSheet.getRow(i).getCell(a);
						String cellData = Cell.toString().trim();
						if (cellData.equalsIgnoreCase(cucumberTestDataRef.trim())) {
							rowNumber = i;
							break outerloop;
						}
					}

					actualTestData = ExcelWSheet.getRow(1).getCell(a).getStringCellValue();
					actualTestData = actualTestData.trim();
					break;
				}
			}

			DataFormatter formatter = new DataFormatter();

			for (int a = 0; a < noOfColumns; a++) {
				if ((Headers[a].trim()).equalsIgnoreCase(expectedTestData.trim())) {
					actualTestData = formatter.formatCellValue(ExcelWSheet.getRow(rowNumber).getCell(a));
					actualTestData = actualTestData.trim();
					break;
				}
			}

			ExcelWBook.close();
			ExcelFile.close();
		} catch (Exception e) {
		}
		return actualTestData;
	}

	public synchronized static String getDataAsStr(String bookColHeading, String rowHeading,
			String colHeading) {

		String actualTestData = null;
		int rowNumber = 0;
		try {
			int rowCount = ExcelWSheet.getPhysicalNumberOfRows();
			int noOfColumns = ExcelWSheet.getRow(0).getLastCellNum();
			String[] Headers = new String[noOfColumns];
			for (int i = 0; i < noOfColumns; i++) {
				Headers[i] = ExcelWSheet.getRow(0).getCell(i).getStringCellValue();
			}

			outerloop: for (int a = 0; a < noOfColumns; a++) {
				if ((Headers[a].trim()).equalsIgnoreCase(sJSONReader(bookColHeading).trim())) {
					for (int i = 1; i <= rowCount; i++) {
						Cell = ExcelWSheet.getRow(i).getCell(a);
						String cellData = Cell.toString().trim();
						if (cellData.equalsIgnoreCase(rowHeading.trim())) {
							rowNumber = i;
							break outerloop;
						}
					}

					actualTestData = ExcelWSheet.getRow(1).getCell(a).getStringCellValue();
					actualTestData = actualTestData.trim();
					break;
				}
			}

			DataFormatter formatter = new DataFormatter();

			for (int a = 0; a < noOfColumns; a++) {
				if ((Headers[a].trim()).equalsIgnoreCase(sJSONReader(colHeading).trim())) {
					actualTestData = formatter.formatCellValue(ExcelWSheet.getRow(rowNumber).getCell(a));
					actualTestData = actualTestData.trim();
					break;
				}
			}

			ExcelWBook.close();
			ExcelFile.close();
		} catch (Exception e) {
		}
		return actualTestData;
	}

	public synchronized static void passOnTestDataToExcel(String cellRowNo, int cellColNo, String orderID) {
		try {
			String testData = cellRowNo;
			testData = testData.substring(testData.length() - 1);
			int rowNo = Integer.parseInt(testData);

			File file = new File(excelPath);
			FileInputStream ExcelFile = new FileInputStream(file);
			XSSFWorkbook ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet("PaymentMethod");

			Row row = ExcelWSheet.getRow(rowNo);
			row.createCell(cellColNo).setCellValue(orderID);
			ExcelFile.close();

			FileOutputStream outFile = new FileOutputStream(new File(excelPath));
			ExcelWBook.write(outFile);
			ExcelWBook.close();
			outFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized static int getRowNoAsInt(String excelWorkBookName, String workbookColumnHeading,
			String testDataReq) throws Exception {
		String testDataFromExcel = null;
		int rowNumber = 0;
		try {
			File file = new File(excelPath);
			FileInputStream ExcelFile = new FileInputStream(file);
			XSSFWorkbook ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(excelWorkBookName);
			int rowCount = ExcelWSheet.getPhysicalNumberOfRows();
			int noOfColumns = ExcelWSheet.getRow(0).getLastCellNum();
			String[] Headers = new String[noOfColumns];
			for (int i = 0; i < noOfColumns; i++) {
				Headers[i] = ExcelWSheet.getRow(0).getCell(i).getStringCellValue();
			}

			outerloop: for (int a = 0; a < noOfColumns; a++) {
				if ((Headers[a].trim()).equalsIgnoreCase(workbookColumnHeading)) {
					for (int i = 1; i <= rowCount; i++) {
						Cell = ExcelWSheet.getRow(i).getCell(a);
						String cellData = Cell.toString().trim();
						if (cellData.equalsIgnoreCase(testDataReq)) {
							rowNumber = i;
							break outerloop;
						}
					}

					testDataFromExcel = ExcelWSheet.getRow(1).getCell(a).getStringCellValue();
					testDataFromExcel = testDataFromExcel.trim();
					break;
				}
			}

			ExcelWBook.close();
			ExcelFile.close();
			return rowNumber;

		} catch (Exception e) {
			throw (e);
		}
	}

	public static String[] locatorExtractor(String sReference) {
		String sLocatorType, sLocatorValue;
		String[] sArray = new String[2];
		sArray[0] = sReference;
		sArray[0] = sArray[0].split("->")[1];
		sArray = sArray[0].split(":");
		sLocatorType = sArray[0].replaceAll("\\s", "");
		sLocatorValue = sArray[1].trim();
		sLocatorValue = sLocatorValue.substring(0, sLocatorValue.length() - 1);
		sArray[0] = sLocatorType;
		sArray[1] = sLocatorValue;
		return sArray;
	}

	public static boolean isElementPresentAfterWait(WebDriver driver, String sReference) {
		boolean flagValue = false;
		String element[] = Utility.locatorExtractor(sReference);
		if (element[0].contains("css")) {
			if (driver.findElements(By.cssSelector(element[1])).size() != 0) {
				flagValue = driver.findElements(By.cssSelector(element[1])).get(0).isDisplayed();
			} else {
				flagValue = false;
			}
		} else if (element[0].contains("xpath")) {
			if (driver.findElements(By.xpath(element[1])).size() != 0) {
				flagValue = driver.findElements(By.xpath(element[1])).get(0).isDisplayed();
			} else {
				flagValue = false;
			}
		} else if (element[0].contains("name")) {
			if (driver.findElements(By.name(element[1])).size() != 0) {
				flagValue = driver.findElements(By.name(element[1])).get(0).isDisplayed();
			} else {
				flagValue = false;
			}
		}
		return flagValue;
	}

	public static String extractLocator(String locator) {
		locator = locator.substring(locator.indexOf("By"), locator.indexOf("]") + 1);
		return locator;
	}
}