package com.ascena.automation.utilities;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.IAssert;
import org.testng.asserts.SoftAssert;

import com.ascena.automation.extent.listeners.ExtentTestManager;
import com.aventstack.extentreports.Status;

import cucumber.api.Scenario;

public abstract class BasePage2 {

	public WebDriver driver = null;

	protected static Wait<WebDriver> wait = null;
	protected int LOAD_TIMEOUT = 10;
	protected int AJAX_ELEMENT_TIMEOUT = 12;
	long startTime;
	public static Scenario message = null;
	public static TestSoftAssert softAssert = new TestSoftAssert();

	public BasePage2() {
		this.driver = DriverManager.getDriver();
	}

	public static class TestSoftAssert extends SoftAssert {

		List<String> messages = new ArrayList<>();

		@Override
		public void onAssertFailure(IAssert<?> assertCommand, AssertionError ex) {
			messages.add("onAssertFailure");
		}

		@Override
		public void assertAll() {
			try {
				messages.add("assertAll");
				super.assertAll();
			} catch (AssertionError e) {
				throw e;
			}
		}

		/**
		 * Assertion method to Soft Assert the validation and log the respective
		 * result.
		 * 
		 * @param condition,
		 *            failureMessage
		 * @throws Exception
		 */

		public synchronized void softAssertTrue(Boolean condition, String failureMessage) {

			this.assertTrue(condition, failureMessage);
			if (this.messages.contains("onAssertFailure")) {
				ExtentTestManager.getTest().log(Status.FAIL, failureMessage);
			} else {
				ExtentTestManager.getTest().log(Status.PASS, failureMessage.replace("not", ""));
			}

			messages.clear();
		}

		/**
		 * Assertion method to Soft Assert the validation and log the respective
		 * result.
		 * 
		 * @param condition,
		 *            failureMessage
		 * @throws Exception
		 */

		public synchronized void softAssertFalse(Boolean condition, String failureMessage) {

			this.assertFalse(condition, failureMessage);
			if (this.messages.contains("onAssertFailure")) {
				ExtentTestManager.getTest().log(Status.FAIL, failureMessage);
			} else {
				ExtentTestManager.getTest().log(Status.PASS, failureMessage.replace("not", ""));
			}

			messages.clear();
		}

		/**
		 * Assertion method to Soft Assert the validation and log the respective
		 * result.
		 * 
		 * @param condition,
		 *            failureMessage
		 * @throws Exception
		 */

		public synchronized void softAssertEquals(String actual, String expected, String failureMessage) {

			this.assertEquals(actual, expected, failureMessage);
			if (this.messages.contains("onAssertFailure")) {
				ExtentTestManager.getTest().log(Status.FAIL, failureMessage);
			} else {
				ExtentTestManager.getTest().log(Status.PASS, failureMessage.replace("not", ""));
			}

			messages.clear();
		}
	}
	
	/**
	 * @author AHA
	 * @purpose Does soft assert all
	 */
	public void softAssertAll() {
		softAssert.assertAll();
	}

	/**
	 * @author aha
	 * @purpose Invokes the browser by fetching the url from json file
	 */
	public synchronized void invokeBrowser() throws Exception {
		System.out.println("Thread id = " + Thread.currentThread().getId());
		System.out.println("Hashcode of webDriver instance = " + driver.hashCode());

		driver.get(JSONReader.getURL(InitialDataSetup.getEnv()));
		ExtentTestManager.logInfo("Launced the browser with " + JSONReader.getURL(InitialDataSetup.getEnv()));
	}

	/**
	 * @purpose scrolls to given element
	 */
	protected synchronized void scrollIntoViewElement(WebElement element, int timeOut, String elementName) {
		try {
			wait = new FluentWait<WebDriver>((WebDriver) driver).withTimeout(Duration.ofSeconds(timeOut))
					.pollingEvery(Duration.ofSeconds(1));
			wait.until(ExpectedConditions.visibilityOf(element));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			ExtentTestManager.logInfo(elementName + "got scrolled successfully");
		} catch (Exception e) {
		}
	}

	/**
	 * @purpuse Logs the information to console
	 */
	protected synchronized void addMessageToConsole(String message) {
		String browser = Utility.sJSONReader("browser");
		browser = browser.substring(0, 1).toUpperCase() + browser.substring(1);
		System.out.println(
				"Thread ID : " + getThreadValue(DriverManager.dr.get()) + " | Browser : " + browser + " | " + message);
	}

	/**
	 * @purpose Logs the exception to extent report, to console, and then throws
	 *          assertion error to exit the program
	 */
	protected synchronized void hardAssertionFail(String message, Throwable e) {
		ExtentTestManager.logFailed(fullStackTrace(e));
		addMessageToConsole(message + " : " + returnLocator(fullStackTrace(e)));
		hardAssertion(message + " : " + returnLocator(fullStackTrace(e)));
	}

	/**
	 * @purpose Throws assertion error and logs the message
	 */
	protected synchronized static void hardAssertion(String message) {
		Assert.assertTrue(message, false);
	}

	protected synchronized String fullStackTrace(Throwable e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		String fullStackTrace = sw.toString();
		return fullStackTrace;
	}

	/**
	 * @author aha
	 * @purpose Wait for loader to finish to wait for loader
	 * @throws Exception
	 */

	public void waitForLoaderToFinish(WebDriver driver) throws InterruptedException {
		try {
			new WebDriverWait(driver, 20)
					.until(ExpectedConditions.invisibilityOf(driver.findElement(By.xpath("//div[@class='loader']"))));
			Thread.sleep(2000);
			ExtentTestManager.logInfo("Loader dissapeared.");
		} catch (Exception e) {
			ExtentTestManager.logInfo("Loader not found.");
		}
	}

	// ********************* Method() Waits Until Page Does Not Load Using
	// JavaScript *********************
	protected synchronized void waitForPageLoaded() throws InterruptedException {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};
		try {
			WebDriverWait wait = new WebDriverWait(driver, 20);
			wait.until(expectation);
			Thread.sleep(1000);
			ExtentTestManager.logInfo("Page gets loaded successfully.");
		} catch (Exception e) {
			softAssertionFail("Error occured while page load using javascript with following message :", e);
		}
	}

	protected synchronized void softAssertionFail(String message, Throwable e) {
		try {
			ExtentTestManager.logFailed(fullStackTrace(e));
			addMessageToConsole(message + " : " + returnLocator(fullStackTrace(e)));
			softAssertion(message + " : " + returnLocator(fullStackTrace(e)));
		} catch (Exception ex) {
			ExtentTestManager.logInfo("Exception occured during the processing of softAssertionFail()");
		}
	}

	/**
	 * @purpose Does soft assertion
	 */
	protected synchronized static void softAssertion(String message) {
		try {
			Assert.assertTrue(message, false);
		} catch (Throwable t) {

		}
	}

	/**
	 * Method to click on an element
	 * 
	 * @param WebElement
	 *            : element reference (id, name, class, xpath, css)
	 * @param String
	 *            : element name
	 */
	protected synchronized void clickOnElement(WebElement element, String elementName) {
		try {
			element.click();
			ExtentTestManager.logInfo("Clicking on element : " + elementName);
		} catch (Exception e) {
			hardAssertionFail("Exception occurred while clicking on " + elementName + " with following message", e);
		}
	}

	// **************************** Method() Waits For WebElement To Be Visible
	// ******************************
	protected synchronized boolean waitForElementVisibility(WebElement element, int timeOut, String elementName) {
		boolean flagStatus = false;
		try {
			wait = new FluentWait<WebDriver>((WebDriver) driver).withTimeout(Duration.ofSeconds(timeOut))
					.pollingEvery(Duration.ofSeconds(1));
			flagStatus = wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
			ExtentTestManager.logInfo("Visibility of " + elementName + " was found true");
		} catch (Exception e) {
			hardAssertionFail("Exception came while trying to wait & identify element " + elementName
					+ " with following message ", e);
		}
		return flagStatus;
	}

	protected synchronized boolean verifyElementDisplayedForSeconds(WebElement element, int timeOut,
			String elementName) {
		boolean flagStatus = false;
		try {
			wait = new FluentWait<WebDriver>((WebDriver) driver).withTimeout(Duration.ofSeconds(timeOut))
					.pollingEvery(Duration.ofSeconds(1));
			flagStatus = wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
			ExtentTestManager.logInfo("Visibility of " + elementName + " was found true");
		} catch (Exception e) {

		}
		return flagStatus;
	}

	// **************************** Method() Waits For List<WebElement> To Be
	// Visible ***************************

	protected synchronized boolean waitForElementVisibilityFromList(List<WebElement> elements, int time,
			String elementName) {
		boolean flagStatus = false;
		try {
			wait = new FluentWait<WebDriver>((WebDriver) driver).withTimeout(Duration.ofSeconds(time))
					.pollingEvery(Duration.ofSeconds(1));
			wait.until(ExpectedConditions.visibilityOfAllElements(elements));
			ExtentTestManager.logInfo("Visibility of " + elementName + " was found true");
		} catch (Exception e) {
			hardAssertionFail("Exception came while trying to wait & identify element " + elementName
					+ " with following message ", e);
		}
		return flagStatus;
	}

	// ************************************ Method() To Wait & Click On
	// WebElement ***********************************
	protected synchronized void waitForElementVisibilityAndClick(WebElement element, int timeOut, String elementName) {
		try {
			wait = new FluentWait<WebDriver>((WebDriver) driver).withTimeout(Duration.ofSeconds(timeOut))
					.pollingEvery(Duration.ofSeconds(1));
			wait.until(ExpectedConditions.visibilityOf(element));
			element.click();
			ExtentTestManager.logInfo("Clicking on element : " + elementName);
		} catch (Exception e) {
			hardAssertionFail("Exception occurred while clicking on " + elementName + " with following message", e);
		}
	}

	// ********************************** Method() To Wait & Click On WebElement
	// *********************************
	protected synchronized void waitForElementClickableAndClick(WebElement element, int timeOut, String elementName) {
		try {
			wait = new FluentWait<WebDriver>((WebDriver) driver).withTimeout(Duration.ofSeconds(timeOut))
					.pollingEvery(Duration.ofSeconds(1));
			wait.until(ExpectedConditions.elementToBeClickable(element));
			element.click();
			ExtentTestManager.logInfo("Clicking on element : " + elementName);
		} catch (Exception e) {
			hardAssertionFail("Exception occurred while clicking on " + elementName + " with following message", e);
		}
	}

	// ****************************** Method() To Click On List<WebElement>
	// ***********************************
	protected synchronized void waitForElementsAndClick(List<WebElement> element, int timeOut, String elementName) {
		try {
			wait = new FluentWait<WebDriver>((WebDriver) driver).withTimeout(Duration.ofSeconds(timeOut))
					.pollingEvery(Duration.ofSeconds(1));
			wait.until(ExpectedConditions.visibilityOfAllElements(element));
			element.get(0).click();
			ExtentTestManager.logInfo("Clicking on element : " + elementName);
		} catch (Exception e) {
			hardAssertionFail("Exception occurred while clicking on " + elementName + " with following message", e);
		}
	}

	// ****************************** Method() To Enter the Value In WebElement
	// ******************************
	protected synchronized void typeElementValue(WebElement element, String value, String elementName) {
		try {
			element.sendKeys(value);
			ExtentTestManager.logInfo("Typing in : " + elementName + " entered the value as : " + value);
		} catch (Exception e) {
			hardAssertionFail("Exception occurred while Typing in : " + elementName + " value as : " + value
					+ " with following message", e);
		}
	}

	// ****************************** Method() To Enter the Value In WebElement
	// ******************************
	protected synchronized void clickEnterOnElement(WebElement element, String elementName) {
		try {
			wait = new FluentWait<WebDriver>((WebDriver) driver).withTimeout(Duration.ofSeconds(10))
					.pollingEvery(Duration.ofSeconds(1));
			wait.until(ExpectedConditions.elementToBeClickable(element));
			element.sendKeys(Keys.ENTER);
			ExtentTestManager.logInfo("Pressing Enter on : " + elementName);
		} catch (Exception e) {
			softAssertionFail("Exception occurred while pressing enter on : " + elementName
					+ " with following message : " + e.getMessage(), e);
		}
	}

	// ******************************* Method() To Select WebElement Drop-down
	// *******************************
	protected synchronized void selectElement(WebElement element, String value, String elementName) {
		try {
			wait = new FluentWait<WebDriver>((WebDriver) driver).withTimeout(Duration.ofSeconds(20))
					.pollingEvery(Duration.ofSeconds(1));
			wait.until(ExpectedConditions.elementToBeClickable(element));
			Select oSelectMonth = new Select(element);
			oSelectMonth.selectByIndex(4);
			ExtentTestManager.logInfo("Selecting the : " + elementName + " where value as : " + value);
		} catch (Exception e) {
			hardAssertionFail("Exception occurred while selecting : " + elementName + " value as : " + value
					+ " with following message", e);
		}
	}

	protected synchronized void invisibilityOfElementLocated(By element, String elementName) {
		try {
			wait = new FluentWait<WebDriver>((WebDriver) driver).withTimeout(Duration.ofSeconds(20))
					.pollingEvery(Duration.ofSeconds(1));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(element));
			ExtentTestManager.logInfo("Element " + elementName + " got invisible");
		} catch (Exception e) {
			softAssertionFail("Exception came while trying to wait & identify element " + elementName
					+ " with following message ", e);
		}
	}

	protected synchronized boolean isElementNotPresent(WebElement element) {
		boolean elementCheck = false;
		try {
			wait = new FluentWait<WebDriver>((WebDriver) driver).withTimeout(Duration.ofSeconds(10))
					.pollingEvery(Duration.ofSeconds(1));
			elementCheck = wait.until(ExpectedConditions.invisibilityOf(element));
			ExtentTestManager.logInfo(element + ", Is Element not present anymore: True");
			return elementCheck;
		} catch (Exception e) {
			softAssertionFail("Element is still visible :", e);
			return elementCheck;
		}
	}

	// ****************************** Method() To Check If WebElement Is Present
	// ******************************
	protected synchronized boolean isElementPresentBy(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (Exception e) {
			softAssertionFail("Exception occurred while waiting for element(address) with following message :", e);
			return false;
		}
	}

	protected synchronized WebElement waitForElementToBeClickable(WebElement element, int timeOut, String elementName) {
		try {
			wait = new FluentWait<WebDriver>((WebDriver) driver).withTimeout(Duration.ofSeconds(timeOut))
					.pollingEvery(Duration.ofSeconds(1));
			element = wait.until(ExpectedConditions.elementToBeClickable(element));
			ExtentTestManager.logInfo(elementName + " found & is Clickable was : True");
		} catch (Exception e) {
			softAssertionFail("Exception came while trying to wait & identify element " + elementName
					+ " with following message :", e);
		}
		return element;
	}

	// ************************** Method() Waits For WebElement To Be Visible &
	// Click ************************
	protected synchronized WebElement waitForElementVisibleAndClick(WebElement element, int timeout) {
		try {
			wait = new FluentWait<WebDriver>((WebDriver) driver).withTimeout(Duration.ofSeconds(timeout))
					.pollingEvery(Duration.ofSeconds(1));
			element = wait.until(ExpectedConditions.visibilityOf(element));
			element.click();
			ExtentTestManager.logInfo("Element was found and perform clicked");
		} catch (Exception e) {
			softAssertionFail("Exception occurred while waiting to click on element with following message", e);
		}
		return element;
	}

	// *************************** Method() Waits For List<WebElement> To Be
	// Visible ***************************
	protected synchronized List<WebElement> waitForVisibilityOfElementsLocated(List<WebElement> element, int timeOut,
			String elementName) {
		try {
			wait = new FluentWait<WebDriver>((WebDriver) driver).withTimeout(Duration.ofSeconds(timeOut))
					.pollingEvery(Duration.ofSeconds(1));
			wait.until(ExpectedConditions.visibilityOfAllElements(element));
			ExtentTestManager.logInfo(elementName + " found & is Clickable was : True");
		} catch (Exception e) {
			softAssertionFail("Exception came while trying to wait & identify element " + elementName
					+ " with following message :", e);
		}
		return element;
	}

	// ********************* Method() Performs Mouse Hover Action On WebElement
	// *********************
	protected synchronized void waitForMouseHoverAction(WebElement element, int timeOut, String elementName) {
		try {
			wait = new FluentWait<WebDriver>((WebDriver) driver).withTimeout(Duration.ofSeconds(timeOut))
					.pollingEvery(Duration.ofSeconds(1));
			wait.until(ExpectedConditions.visibilityOf(element));
			Actions actions = new Actions(driver);
			actions.moveToElement(element).build().perform();
			ExtentTestManager.logInfo("Mouse hover performed on Element : " + elementName);
		} catch (Exception e) {
			hardAssertionFail(
					"Exception occured while performing mouse hover on " + elementName + " with following message", e);
		}
	}

	// ********************* Method() Performs Mouse Hover & Click Action On
	// WebElement *********************
	protected synchronized void waitForMouseHoverAndClick(WebElement HoverElement, String elementName)
			throws Exception {
		try {
			Actions actions = new Actions(driver);
			actions.moveToElement(HoverElement);
			actions.click().build().perform();
			ExtentTestManager.logInfo("Mouse hover performed on Element : " + elementName);
		} catch (Exception e) {
			hardAssertionFail(
					"Exception occured while performing mouse hover on " + elementName + " with following message", e);
		}
	}

	// ************************* Method() Clicks WebElement Using JavaScript
	// *************************
	protected synchronized void clickWebElementJavaScriptExecutor(WebElement element, String elementName)
			throws Exception {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			Thread.sleep(2000);
			js.executeScript("arguments[0].click()", element);
			ExtentTestManager.logInfo("Clicking on element : " + elementName);
		} catch (Exception e) {
			hardAssertionFail("Exception occured while clicking  on element " + elementName
					+ " via javascript with following message", e);
		}
	}

	// ***************************** Method() To Wait For Sub-Category
	// ****************************
	protected synchronized void dynamicElementWaitHandling(WebElement element) {
		double startTime = startTime();
		boolean elementDisplay = false;
		long starttime = System.currentTimeMillis();
		long endtime = starttime + Long.parseLong(Utility.sJSONReader("bounceXTimeOut"));
		while (System.currentTimeMillis() < endtime) {
			try {
				elementDisplay = element.isDisplayed();
				if (elementDisplay == true) {
					String endTime = endTime(startTime);
					ExtentTestManager
							.logInfo("Sub-Category Menu was found displayed after waiting for " + endTime + " Sec.");
					addMessageToConsole("Sub-Category Menu was found displayed after waiting for " + endTime + " Sec.");
					waitForPageLoaded();
					break;
				}
			} catch (Exception e) {
				softAssertionFail("Exception came while trying to wait & identify sub-category menu :", e);
			}
		}
	}

	// ********************************* Method() Mouse Over To View Element
	// *********************************
	protected synchronized void onMouseOverToViewElement(WebElement element, int timeOut, String elementName) {
		try {
			wait = new FluentWait<WebDriver>((WebDriver) driver).withTimeout(Duration.ofSeconds(timeOut))
					.pollingEvery(Duration.ofSeconds(1));
			wait.until(ExpectedConditions.visibilityOf(element));
			((JavascriptExecutor) driver).executeScript("arguments[0].onmouseover();", element);
			ExtentTestManager.logInfo("Mouse hover done successfully on " + elementName);
		} catch (Exception e) {

		}
	}

	// *************************** Method() to Click on Anchor tag<a> Using Href
	// ***************************
	protected synchronized void clickLinkByHref(WebDriver driver, String href) {
		try {
			List<WebElement> anchors = driver.findElements(By.tagName("a"));
			Iterator<WebElement> i = anchors.iterator();
			while (i.hasNext()) {
				WebElement anchor = i.next();
				if (anchor.getAttribute("href").contains(href)) {
					anchor.click();
					ExtentTestManager.logInfo("Successfully clicked on element");
					break;
				}
			}
		} catch (Exception e) {
			softAssertionFail("Exception came while trying to wait & identify element with following message :", e);
		}
	}

	// ***************************** Method() Clicks List<WebElement> Inside
	// Modal ***********************
	protected synchronized void clickInsideModalWithWebElements(WebElement element, int timeOut, String elementName) {
		try {
			wait = new FluentWait<WebDriver>((WebDriver) driver).withTimeout(Duration.ofSeconds(timeOut))
					.pollingEvery(Duration.ofSeconds(1));
			wait.until(ExpectedConditions.elementToBeClickable(element));
			clickOnElement(element, elementName);
		} catch (Exception e) {
			softAssertionFail("Exception came while trying to wait & identify element with following message :", e);
		}
	}

	// ******************************* Method() Clicks WebElement Inside Modal
	// *************************
	protected synchronized void clickInsideModal(WebElement element, int timeOut, String elementName) {
		try {
			wait = new FluentWait<WebDriver>((WebDriver) driver).withTimeout(Duration.ofSeconds(timeOut))
					.pollingEvery(Duration.ofSeconds(1));
			wait.until(ExpectedConditions.visibilityOf(element));
			clickOnElement(element, elementName);
		} catch (Exception e) {
			softAssertionFail("Exception occurred while trying to locate " + elementName + " with following message",
					e);
		}
	}

	protected synchronized void waitForModalLoading(WebElement element, int timeOut, String elementName) {
		try {
			wait = new FluentWait<WebDriver>((WebDriver) driver).withTimeout(Duration.ofSeconds(timeOut))
					.pollingEvery(Duration.ofSeconds(1));
			wait.until(ExpectedConditions.visibilityOf(element));
			ExtentTestManager.logInfo(elementName + " got triggered succesfully");
		} catch (Exception e) {
			softAssertionFail("Exception occurred while trying to locate " + elementName + " with following message :",
					e);
		}
	}

	// ********************************** Method() Performs Wait & Click on
	// Element ****************************
	protected synchronized boolean waitForElementAndClick(WebDriver driver, String elementXpath) {
		boolean elementPresent = false;
		try {
			wait = new FluentWait<WebDriver>((WebDriver) driver).withTimeout(Duration.ofSeconds(20))
					.pollingEvery(Duration.ofSeconds(1));
			elementPresent = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(elementXpath)))
					.isDisplayed();
			ExtentTestManager.logInfo("Element is present [T/F]..? " + elementPresent);
		} catch (Exception e) {
			softAssertionFail("Exception occurred while trying to wait & identify " + elementXpath
					+ " Modal with following message :", e);
		}
		return elementPresent;
	}

	// ************************************ Method() Returns the Thread ID
	// **********************************
	protected synchronized String getThreadValue(Object value) {
		String text = value.toString();
		String[] nextText = text.split(" ");
		String text2 = nextText[nextText.length - 1].replace("(", "").replace(")", "");
		return text2;
	}

	// ************************************** Method() Switches to iFrame
	// **************************************
	protected synchronized void swtichToIframe(WebDriver driver, WebElement element) {
		try {
			waitForElementVisibility(element, Constants.LONGTIMEOUT, "Sparkred-iFrame");
			driver.switchTo().frame(element);
			Thread.sleep(2000);
			ExtentTestManager.logInfo("Switched within iFrame successfully");
		} catch (Exception e) {
			softAssertionFail("Exception occurred while trying to wait & switch to iFrame with following message :", e);
		}
	}

	// ************************************** Method() Switches to iFrame
	// **************************************
	protected synchronized void switchToDefaultContent(WebDriver driver) throws Exception {
		try {
			driver.switchTo().defaultContent();
			ExtentTestManager.logInfo("Switched outside iFrame successfully");
		} catch (Exception e) {
			softAssertionFail("Exception occurred while trying to wait & switch to iFrame with following message :", e);
		}
	}

	protected synchronized void softAssertionWarning(String message, Exception e) {
		try {
			ExtentTestManager.logWarning(fullStackTrace(e));
			addMessageToConsole(message + " : " + returnLocator(fullStackTrace(e)));
			softAssertion(message + " : " + returnLocator(fullStackTrace(e)));
		} catch (Exception ex) {
			ExtentTestManager.logInfo("Exception occured during the processing of softAssertionWarning()");
		}
	}

	public synchronized static String returnLocator(String text) {
		int startPos = text.indexOf("Caused by");
		int endPos = text.indexOf("}", startPos);
		if (startPos >= 0 && endPos >= 0) {
			text = text.substring(startPos, endPos);
		}
		return text;
	}

	/**
	 * Verify that the page loaded completely.
	 *
	 * @return the CheckPaymentPage class instance.
	 */
	public String verifyPageLoaded() {
		(new WebDriverWait(driver, LOAD_TIMEOUT)).until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver d) {
				return d.getPageSource().contains("");
			}
		});
		return "";
	}

	// ********************* Method() Filters XPath *********************
	protected synchronized String removeUnwantedCharacters(String element) {
		String parts[] = element.split("->");
		return parts[1].substring(0, parts[1].length() - 1).trim();
	}

	// ********************* Method() Returns Start Time *********************
	protected synchronized static double startTime() {
		double starttime = System.currentTimeMillis();
		return starttime;
	}

	// ********************* Method() Returns Time Taken During The Wait For An
	// Element *********************
	protected synchronized static String endTime(double starttime) {
		double lasttime = System.currentTimeMillis();
		double diff = (lasttime - starttime) / 1000;
		String meanTime = String.format("%.02f", diff);
		return meanTime;
	}

	// ------------------------------------------------------------------------------------------------------------------
	/**
	 * Verify that the page loaded completely.
	 * 
	 * @param <T>
	 * @param Class<T>
	 *            is only one parameter used to pass the generic class reference
	 * @return the generic class instance
	 */
	public <T> Object openPage(Class<T> clazz) {
		Object page = null;
		try {
			page = PageFactory.initElements(driver, clazz);
			PageFactory.initElements(new AjaxElementLocatorFactory(driver, AJAX_ELEMENT_TIMEOUT), page);
			waitForPageToLoad(((BasePage2) page).getPageLoadCondition());
		} catch (NoSuchElementException e) {
			System.out.println(String.format("This is not the %s page", clazz.getSimpleName()));
			throw new IllegalStateException(String.format("This is not the %s page", clazz.getSimpleName()));
		}
		return page;
	}

	// **************************************** Abstract Method()
	// *********************************************
	protected abstract ExpectedCondition<?> getPageLoadCondition();

	// ******************************* Method() waits till WebElement loads on
	// Page **************************
	protected synchronized void waitForPageToLoad(ExpectedCondition<?> pageLoadCondition) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, LOAD_TIMEOUT);
			wait.until(pageLoadCondition);
		} catch (Exception e) {
			softAssertionFail(
					"Exception occurred while identifying element on landing of the page with following message", e);
		}
	}
}