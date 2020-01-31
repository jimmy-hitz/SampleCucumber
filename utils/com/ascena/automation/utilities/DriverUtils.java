package com.ascena.automation.utilities;

import static org.testng.Assert.fail;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Platform;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Base Step implementation class for Cucumber Actions defined in Action File
 */

public class DriverUtils {

	private WebDriver driver;
	public boolean grid = true;
	private static DriverUtils runTimeInstance = null;
	private static String browser;

	// ************************** ()()()() Method To Launch Browser ()()()()
	// ****************************
	public synchronized WebDriver getBrowserInstantiation() {

		String browserHeadlessMode, osName;
		DesiredCapabilities cap = null;

		DriverFactory.setGridPath(Utility.sJSONReader("gridPath"));
		DriverFactory.setLocalExecution(grid);
		// --------------- Reading Browser's Configuration from JSON ---------------
		osName = System.getProperty("os.name").toUpperCase();
		browserHeadlessMode = Utility.sJSONReader("browserHeadlessMode");
		// -------------------------------------------------------------------------

		try {
			if (System.getenv("ExecutionType") != null && System.getenv("ExecutionType").equals("Grid")) {
				grid = true;
			}

			browser = InitialDataSetup.getBrowser();
			browser = browser.substring(0, 1).toUpperCase() + browser.substring(1);
			
			DriverFactory.setLocalExecution(grid);

			if (DriverFactory.isLocalExecution()) {
				switch (browser.toUpperCase()) {
				case WebDriverType.CHROME:
					
					WebDriverManager.chromedriver().setup();
					System.setProperty("webdriver.chrome.args", "--disable-logging");
					System.setProperty("webdriver.chrome.silentOutput", "true");

					ChromeOptions options = new ChromeOptions();
					options.addArguments("start-maximized");
					options.addArguments("disable-infobars");
					options.setAcceptInsecureCerts(true);

					driver = new ChromeDriver(options);
					
					if (driver != null) {
						driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
						driver.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);
					}
					
					driver.manage().deleteAllCookies();
					
					System.out.println("Chrome Browser Launched on : " + osName + " | Downloaded Version : "
							+ WebDriverManager.chromedriver().getDownloadedVersion());
					break;
				case WebDriverType.FIREFOX:
					WebDriverManager.firefoxdriver().setup();
					FirefoxOptions fOptions = new FirefoxOptions();
					fOptions.setCapability(CapabilityType.PLATFORM_NAME, Platform.WINDOWS);
					fOptions.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
					fOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
					fOptions.setAcceptInsecureCerts(true);
					if (browserHeadlessMode.equalsIgnoreCase("enable")) {
						fOptions.setHeadless(true);
					}
					fOptions.setCapability("ignoreProtectedModeSettings", true);
					fOptions.setCapability("marionette", false);
					fOptions.addArguments("test-type");
					fOptions.addArguments("--disable-extensions");
					fOptions.addArguments("--disable-notifications");
					fOptions.addArguments("start-maximized");
					driver = new FirefoxDriver(fOptions);
					driver.manage().deleteAllCookies();
					System.out.println("Firefox Browser Launched on : " + osName + " | Downloaded Version : "
							+ WebDriverManager.firefoxdriver().getDownloadedVersion());
					break;
				case WebDriverType.INTERNETEXPLORER:
					WebDriverManager.iedriver().arch32().setup();
					InternetExplorerOptions iOptions = new InternetExplorerOptions();
					iOptions.setCapability("ignoreProtectedModeSettings", true);
					iOptions.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
					iOptions.setCapability(InternetExplorerDriver.NATIVE_EVENTS, true);
					iOptions.setCapability(CapabilityType.BROWSER_NAME, "internet explorer");
					iOptions.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
					iOptions.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
							true);
					iOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
					iOptions.setCapability(CapabilityType.SUPPORTS_ALERTS, true);
					iOptions.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
					iOptions.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
					iOptions.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
					iOptions.setCapability(InternetExplorerDriver.UNEXPECTED_ALERT_BEHAVIOR,
							UnexpectedAlertBehaviour.IGNORE);
					iOptions.setCapability(InternetExplorerDriver.ENABLE_ELEMENT_CACHE_CLEANUP, true);
					driver = new InternetExplorerDriver(iOptions);
					driver.manage().deleteAllCookies();
					System.out.println("IE Explorer Browser Launched on : " + osName + " | Downloaded Version : "
							+ WebDriverManager.iedriver().getDownloadedVersion());
					break;
				case WebDriverType.SAFARI:
					driver = new SafariDriver();
					driver.manage().deleteAllCookies();
					break;
				case WebDriverType.EDGE:
					WebDriverManager.edgedriver().setup();
					EdgeOptions eOptions = new EdgeOptions();
					eOptions.setPageLoadStrategy("eager");
					driver = new EdgeDriver(eOptions);
					driver.manage().deleteAllCookies();
				default:
					System.err.println("Browser configs value not matched in ProductionTestData.json");
					fail(browser + " is not found in browser list");
					driver = null;
					break;
				}
			} else {
				if (browser.equalsIgnoreCase("firefox")) {
					cap = DesiredCapabilities.firefox();
					cap.setBrowserName("firefox");
					cap.setPlatform(Platform.ANY);
				} else if (browser.equalsIgnoreCase("chrome")) {
					cap = DesiredCapabilities.chrome();
					cap.setBrowserName("chrome");
					cap.setPlatform(Platform.ANY);
					System.out.println("GRID.........................");
					
				} else if (browser.equalsIgnoreCase("ie")) {
					cap = DesiredCapabilities.internetExplorer();
					cap.setBrowserName("iexplore");
					cap.setPlatform(Platform.WIN10);
				}
				driver = new RemoteWebDriver(new URL(DriverFactory.getGridPath()), cap);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		DriverManager.setWebDriver(driver);

		System.out.println("Driver has been successfully initialized, so what are you waiting for? let's get started");

		return driver;
	}

	public static DriverUtils getInstance() {
		if (runTimeInstance == null) {
			runTimeInstance = new DriverUtils();
		}
		return runTimeInstance;
	}

	public void quit() {
		if (DriverManager.getDriver() != null) {
			DriverManager.getDriver().quit();
		}
		System.out.println("Test Execution Completed !!!");
	}
}