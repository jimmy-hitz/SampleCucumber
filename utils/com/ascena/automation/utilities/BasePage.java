package com.ascena.automation.utilities;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Duration;
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
import com.ascena.automation.extent.listeners.ExtentTestManager;
import cucumber.api.Scenario;

public abstract class BasePage{

	protected static Wait<WebDriver> wait = null;
	protected WebDriver driver;
	protected int LOAD_TIMEOUT = 10;
	protected int AJAX_ELEMENT_TIMEOUT = 12;
	long startTime;
	public static Scenario message = null;

	public BasePage() {
		this.driver = DriverManager.getDriver();
	}

	/**
	 * Verify that the page loaded completely.
	 * @param <T>
	 * @param Class<T> is only one parameter used to pass the  generic class reference
	 * @return the generic class instance
	 */
	public <T> Object openPage(Class<T> clazz) {
		Object page = null;
		try {
			page = PageFactory.initElements(driver, clazz);
			PageFactory.initElements(new AjaxElementLocatorFactory(driver, AJAX_ELEMENT_TIMEOUT), page);
			waitForPageToLoad(((BasePage) page).getPageLoadCondition());
		} catch (NoSuchElementException e) {
			System.out.println(String.format("This is not the %s page", clazz.getSimpleName()));
			throw new IllegalStateException(String.format("This is not the %s page", clazz.getSimpleName()));
		}
		return page;
	}
	
	//**************************************** Abstract Method() *********************************************
	protected abstract ExpectedCondition<?> getPageLoadCondition();

	//******************************* Method() waits till WebElement loads on Page **************************
	protected synchronized void waitForPageToLoad(ExpectedCondition<?> pageLoadCondition) {
		try {
			WebDriverWait wait = new WebDriverWait(driver,LOAD_TIMEOUT);
			wait.until(pageLoadCondition);
		}catch(Exception e) {
			softAssertionFail("Exception occurred while identifying element on landing of the page with following message", e);
		}
	}

	/** Method to click on an element
	@param WebElement : element reference (id, name, class, xpath, css)
	@param String : element name
	 */
	protected synchronized void clickOnElement(WebElement element, String elementName) {
		try {
			element.click();
			addMessageToExtentReportAndLogger("Clicking on element : "+elementName);
		}catch(Exception e) {
			hardAssertionFail("Exception occurred while clicking on "+elementName+" with following message", e);
		}
	}

	//****************************** Method() To Click On List<WebElement> ***********************************
	protected synchronized void clickOnElementFromList(List<WebElement> elements, String elementName) {
		try {
			clickElementFromListAbstraction(elements,elementName);
			addMessageToExtentReportAndLogger("Clicking on element : "+elementName);
		}catch(Exception e) {
			hardAssertionFail("Exception occurred while clicking on "+elementName+" with following message", e);
		}
	}

	//**************************** Method() Waits For WebElement To Be Visible ******************************
	protected synchronized boolean waitForElementVisibility(WebElement element, int timeOut, String elementName) {
		boolean flagStatus=false;
		try {
			wait = new FluentWait<WebDriver>((WebDriver) driver).withTimeout(Duration.ofSeconds(timeOut)).pollingEvery(Duration.ofSeconds(1));
			flagStatus = wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
			addMessageToExtentReportAndLogger("Visibility of " + elementName +" was found true");
		}catch(Exception e) {
			hardAssertionFail("Exception came while trying to wait & identify element "+elementName+" with following message ", e);
		}
		return flagStatus;
	}

	//**************************** Method() Waits For List<WebElement> To Be Visible ***************************
	protected synchronized boolean waitForElementVisibilityFromList(List<WebElement> elements, int time, String elementName) {
		boolean flagStatus=false;
		try {
			wait = new FluentWait<WebDriver>((WebDriver) driver).withTimeout(Duration.ofSeconds(time)).pollingEvery(Duration.ofSeconds(1));
			wait.until(ExpectedConditions.visibilityOfAllElements(elements));
			addMessageToExtentReportAndLogger("Visibility of " + elementName +" was found true");
		}catch(Exception e) {
			hardAssertionFail("Exception came while trying to wait & identify element "+elementName+" with following message ", e);
		}
		return flagStatus;
	}

	//************************************ Method() To Wait & Click On WebElement ***********************************
	protected synchronized void waitForElementVisibilityAndClick(WebElement element, int timeOut, String elementName) {
		try {
			wait = new FluentWait<WebDriver>((WebDriver) driver).withTimeout(Duration.ofSeconds(timeOut)).pollingEvery(Duration.ofSeconds(1));
			wait.until(ExpectedConditions.visibilityOf(element));
			element.click();
			addMessageToExtentReportAndLogger("Clicking on element : "+elementName);
		}catch(Exception e) {
			hardAssertionFail("Exception occurred while clicking on "+elementName+" with following message", e);
		}
	}

	//******************************** Method() To Wait & Click On List<WebElement> *****************************
	protected synchronized void waitForElementVisibilityAndClickFromList(List<WebElement> elements, int timeOut, String elementName) {
		try {
			clickElementFromListAbstraction(elements, elementName);
			addMessageToExtentReportAndLogger("Clicking on element : "+elementName);
		}catch(Exception e) {
			hardAssertionFail("Exception occurred while clicking on "+elementName+" with following message", e);
		}
	}

	//********************************** Method() To Wait & Click On WebElement *********************************
	protected synchronized void waitForElementClickableAndClick(WebElement element, int timeOut, String elementName) {
		try {
			wait = new FluentWait<WebDriver>((WebDriver) driver).withTimeout(Duration.ofSeconds(timeOut)).pollingEvery(Duration.ofSeconds(1));
			wait.until(ExpectedConditions.elementToBeClickable(element));
			element.click();
			addMessageToExtentReportAndLogger("Clicking on element : "+elementName);
		}catch(Exception e) {
			hardAssertionFail("Exception occurred while clicking on "+elementName+" with following message", e);
		}
	}

	//****************************** Method() To Click On List<WebElement> ***********************************
	protected synchronized void waitForElementsAndClick(List<WebElement> element, int timeOut, String elementName) {
		try {
			wait = new FluentWait<WebDriver>((WebDriver) driver).withTimeout(Duration.ofSeconds(timeOut)).pollingEvery(Duration.ofSeconds(1));
			wait.until(ExpectedConditions.visibilityOfAllElements(element));
			element.get(0).click();
			addMessageToExtentReportAndLogger("Clicking on element : "+elementName);
		}catch(Exception e) {
			hardAssertionFail("Exception occurred while clicking on "+elementName+" with following message", e);
		}
	}

	//****************************** Method() To Enter the Value In WebElement ******************************
	protected synchronized void typeElementValue(WebElement element, String value, String elementName) {
		try {
			element.sendKeys(value);
			addMessageToExtentReportAndLogger("Typing in : "+elementName+" entered the value as : "+value);
		}catch(Exception e) {
			hardAssertionFail("Exception occurred while Typing in : "+elementName+" value as : "+value+" with following message", e);
		}
	}

	//****************************** Method() To Enter the Value In List<WebElement> ******************************
	protected synchronized void typeElementValueFromList(List<WebElement> elements, String value, String elementName) {
		try {
			listOfWebElementSendKeysAbstraction(elements, value);
			addMessageToExtentReportAndLogger("Typing in : "+elementName+" entered the value as : "+value);
		}catch(Exception e) {
			hardAssertionFail("Exception occurred while Typing in : "+elementName+" value as : "+value+" with following message", e);
		}
	}

	//****************************** Method() To Enter the Value In WebElement ******************************
	protected synchronized void clickEnterOnElement(WebElement element, String elementName) {
		try {
			wait = new FluentWait<WebDriver>((WebDriver) driver).withTimeout(Duration.ofSeconds(10)).pollingEvery(Duration.ofSeconds(1));
			wait.until(ExpectedConditions.elementToBeClickable(element));
			element.sendKeys(Keys.ENTER);
			addMessageToExtentReportAndLogger("Pressing Enter on : "+elementName);
		}catch(Exception e) {
			softAssertionFail("Exception occurred while pressing enter on : "+elementName+" with following message : "+e.getMessage(),e);
		}
	}

	//****************************** Method() To Press Enter on List<WebElement> ******************************
	protected synchronized void clickEnterOnElementFromList(List<WebElement> elements, String elementName) {
		try {
			clickEnterOnElementInListAbstraction(elements, elementName);
			addMessageToExtentReportAndLogger("Pressing Enter on : "+elementName);
		}catch(Exception e) {
			softAssertionFail("Exception occurred while pressing Enter on : "+elementName+" with following message : "+e.getMessage(),e);
		}
	}

	//**************************************** Abstraction Methods() ****************************************
	protected synchronized void bounceXHandlerAbstraction(List<WebElement> elements, int timeOut) {
		int elementSize=elements.size(),i=0;
		if(elementSize!=0) {
			while(i<elementSize) {
				try {
					elements.get(i).click();	
					break;
				}catch(Exception e) {
					softAssertionWarning("Bounce X Pop-Up did not appear on site after waiting for "+Long.parseLong(Utility.sJSONReader("bounceXTimeOut"))/1000+" Sec's.",e);
					i++;
					if(i==2) {
						break;
					}
				}
			}		
		}else {
			try {
				elements.get(0).click();	
			}catch(Exception e) {
				softAssertionWarning("Bounce X Pop-Up did not appear on site after waiting for "+Long.parseLong(Utility.sJSONReader("bounceXTimeOut"))/1000+" Sec's.",e);
			}
		}		
	}

	protected synchronized void clickEnterOnElementInListAbstraction(List<WebElement> elements, String elementName) {
		int elementSize=elements.size(),i=0;

		if(elementSize!=0) {
			while(i<elementSize) {
				try {
					elements.get(i).sendKeys(Keys.ENTER);	
					break;
				}catch(Exception e) {
					softAssertionWarning("Exception occurred while pressing Enter key in 1st iteration on : "+elementName+" with following message : "+e.getMessage(),e);
					i++;
					if(i==2) {
						hardAssertionFail("Exception occurred while pressing Enter key in 2nd iteration on : "+elementName+" with following message : "+e.getMessage(),e);
						break;
					}
				}
			}		
		}else {
			try {
				elements.get(0).sendKeys(Keys.ENTER);	
			}catch(Exception e) {
				hardAssertionFail("Exception occurred while pressing Enter key on : "+elementName+" with following message : "+e.getMessage(),e);
			}
		}
	}

	//******************************* Method() Clicks WebElement Inside Modal *************************
	protected synchronized void clickOnElementInOptionalModal(List<WebElement> elements, int timeOut, String elementName) {	
		int elementSize=elements.size(), i=0;
		if(elementSize!=0) {
			while(i<elementSize) {
				try {
					elements.get(i).click();	
					break;
				}catch(Exception e) {
					softAssertionWarning("Exception occurred while waiting to click on element with following message", e);
					i++;
					if(i==2) {
						hardAssertionFail("Exception occurred while waiting to click on element with following message", e);
						break;
					}
				}
			}		
		}else {
			try {
				elements.get(0).click();
			}catch(Exception e) {
				hardAssertionFail("Exception occurred while waiting to click on element with following message", e);
			}
		}		
	}

	protected synchronized void listOfWebElementToBeClickableAbstraction(List<WebElement> elements, int timeOut) {
		int elementSize=elements.size(), i=0;
		if(elementSize!=0) {
			while(i<elementSize) {
				try {
					wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut)).pollingEvery(Duration.ofSeconds(1));
					wait.until(ExpectedConditions.elementToBeClickable(elements.get(i)));
					break;
				}catch(Exception e) {
					softAssertionWarning("Exception occurred while waiting to click on element with following message", e);
					i++;
					if(i==2) {
						hardAssertionFail("Exception occurred while waiting to click on element with following message", e);
						break;
					}
				}
			}		
		}else {
			try {
				wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut)).pollingEvery(Duration.ofSeconds(1));
				wait.until(ExpectedConditions.elementToBeClickable(elements.get(0)));
			}catch(Exception e) {
				hardAssertionFail("Exception occurred while waiting to click on element with following message", e);
			}
		}		
	}

	protected synchronized void listOfWebElementSendKeysAbstraction(List<WebElement> elements, String value) {
		int elementSize=elements.size(), i=0;
		if(elementSize!=0) {
			while(i<elementSize) {
				try {
					elements.get(i).sendKeys(value);	
					break;
				}catch(Exception e) {
					softAssertionWarning("Exception occurred while waiting to click on element with following message", e);
					i++;
					if(i==2) {
						hardAssertionFail("Exception occurred while waiting to click on element with following message", e);
						break;
					}
				}
			}		
		}else {
			try {
				elements.get(0).sendKeys(value);	
			}catch(Exception e) {
				hardAssertionFail("Exception occurred while waiting to click on element with following message", e);
			}
		}		
	}

	//******************************* Method() To Select WebElement Drop-down *******************************
	protected synchronized void selectElement(WebElement element, String value, String elementName) {
		try {
			wait = new FluentWait<WebDriver>((WebDriver) driver).withTimeout(Duration.ofSeconds(20)).pollingEvery(Duration.ofSeconds(1));
			wait.until(ExpectedConditions.elementToBeClickable(element));
			Select oSelectMonth = new Select(element);
			oSelectMonth.selectByIndex(4);
			addMessageToExtentReportAndLogger("Selecting the : "+elementName+" where value as : "+value);
		}catch(Exception e) {
			hardAssertionFail("Exception occurred while selecting : "+elementName+" value as : "+value+" with following message", e);
		}
	}

	//******************************* Method() To Select WebElement Drop-down *******************************
	protected synchronized void selectElementFromList(List<WebElement> elements, String value, String elementName) {
		try {
			selectElementsAbstraction(elements, elementName, value);
			addMessageToExtentReportAndLogger("Selecting the : "+elementName+" where value as : "+value);
		}catch(Exception e) {
			hardAssertionFail("Exception occurred while Selecting : "+elementName+" value as : "+value+" with following message :", e);
		}
	}

	//******************************* Method() Clicks WebElement Inside Modal *************************
	protected synchronized void selectElementsAbstraction(List<WebElement> elements, String elementName, String value) {	
		int elementSize=elements.size(),i=0;

		if(elementSize!=0) {
			while(i<elementSize) {
				try {
					Select oSelectMonth = new Select(elements.get(i));
					oSelectMonth.selectByIndex(4);
					break;
				}catch(Exception e) {
					softAssertionWarning("Exception occurred while Selecting : "+elementName+" value as : "+value+" with following message", e);
					i++;
					if(i==2) {
						hardAssertionFail("Exception occurred while pressing Enter key in 2nd iteration on : "+elementName+" with following message : ",e);
						break;
					}
				}
			}		
		}else {
			try {
				Select oSelectMonth = new Select(elements.get(i));
				oSelectMonth.selectByIndex(4);	
			}catch(Exception e) {
				hardAssertionFail("Exception occurred while pressing Enter key on : "+elementName+" with following message : ",e);
			}
		}
	}

	//******************************* Method() To Select WebElement Drop-down *******************************
	protected synchronized void invisibilityOfElementLocated(By element, String elementName) {
		try {
			wait = new FluentWait<WebDriver>((WebDriver) driver).withTimeout(Duration.ofSeconds(20)).pollingEvery(Duration.ofSeconds(1));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(element));
			addMessageToExtentReportAndLogger("Element "+elementName+" got invisible");
		}catch(Exception e) {
			softAssertionFail("Exception came while trying to wait & identify element "+elementName+" with following message ",e);
		}
	}

	//****************************** Method() To Check If WebElement Is Present ******************************
	protected synchronized boolean isElementPresent(WebElement element) {
		boolean elementCheck = false;
		try {
			wait = new FluentWait<WebDriver>((WebDriver) driver).withTimeout(Duration.ofSeconds(10)).pollingEvery(Duration.ofSeconds(1));
			elementCheck = wait.until(ExpectedConditions.invisibilityOf(element));
			addMessageToExtentReportAndLogger(element + ", Is Element present : True");
			return elementCheck;
		}catch(Exception e) {
			softAssertionFail("Exception occurred while waiting for element(address) with following message :", e);
			return elementCheck;
		}
	}

	//****************************** Method() To Check If WebElement Is Present ******************************
	protected synchronized boolean isElementPresentBy(By by) {
		try {
			driver.findElement(by);
			return true;
		}catch(Exception e) {
			softAssertionFail("Exception occurred while waiting for element(address) with following message :", e);
			return false;
		}
	}

	//************************** Method() Waits Until WebElement Is Not Click-able **************************
	protected synchronized WebElement waitForElementToBeClickable(WebElement element, int timeOut, String elementName) {
		try {
			wait = new FluentWait<WebDriver>((WebDriver) driver).withTimeout(Duration.ofSeconds(timeOut)).pollingEvery(Duration.ofSeconds(1));
			element = wait.until(ExpectedConditions.elementToBeClickable(element));
			addMessageToExtentReportAndLogger(elementName+" found & is Clickable was : True");
		}catch(Exception e) {
			softAssertionFail("Exception came while trying to wait & identify element "+elementName+" with following message :",e);
		}
		return element;
	}

	//************************** Method() Waits Until WebElement Is Not Click-able **************************
	protected synchronized void waitForElementToBeClickableFromList(List<WebElement> elements, int timeOut, String elementName) {
		try {
			listOfWebElementToBeClickableAbstraction(elements, timeOut);
			addMessageToExtentReportAndLogger(elementName+" found & is Clickable was : True");
		}catch(Exception e) {
			softAssertionFail("Exception came while trying to wait & identify element "+elementName+" with following message :",e);
		}
	}

	//************************** Method() Waits For WebElement To Be Visible & Click ************************ 
	protected synchronized WebElement waitForElementVisibleAndClick(WebElement element, int timeout) {
		try {
			wait = new FluentWait<WebDriver>((WebDriver) driver).withTimeout(Duration.ofSeconds(timeout)).pollingEvery(Duration.ofSeconds(1));
			element = wait.until(ExpectedConditions.visibilityOf(element));
			element.click();
			addMessageToExtentReportAndLogger("Element was found and perform clicked");
		}catch(Exception e) {
			softAssertionFail("Exception occurred while waiting to click on element with following message", e);
		}
		return element;
	}

	//************************** Method() Waits For WebElement To Be Visible & Click ************************ 
	protected synchronized void waitForElementVisibleAndClickFromList(List<WebElement> elements, int timeout, String elementName) {
		try {
			clickElementFromListAbstraction(elements, elementName);
			addMessageToExtentReportAndLogger("Element was found and perform clicked");
		}catch(Exception e) {
			hardAssertionFail("Exception occurred while waiting to click on element with following message", e);
		}
	}

	protected synchronized void clickElementFromListAbstraction(List<WebElement> elements, String elementName) {
		int elementSize=elements.size(),i=0;

		if(elementSize!=0) {
			while(i<elementSize) {
				try {
					elements.get(i).click();	
					break;
				}catch(Exception e) {
					softAssertionWarning("Exception occurred while waiting to click on element "+elementName+" with following message", e);
					i++;
					if(i==2) {
						hardAssertionFail("Exception occurred while waiting to click on element "+elementName+" with following message", e);
						break;
					}
				}
			}		
		}else {
			try {
				elements.get(0).click();	
			}catch(Exception e) {
				hardAssertionFail("Exception occurred while waiting to click on element "+elementName+" with following message", e);
			}
		}		
	}

	//***************************  Method() Waits For List<WebElement> To Be Visible ***************************
	protected synchronized List<WebElement> waitForVisibilityOfElementsLocated(List <WebElement> element, int timeOut, String elementName) {
		try {
			wait = new FluentWait<WebDriver>((WebDriver) driver).withTimeout(Duration.ofSeconds(timeOut)).pollingEvery(Duration.ofSeconds(1));
			wait.until(ExpectedConditions.visibilityOfAllElements(element));
			addMessageToExtentReportAndLogger(elementName + " found & is Clickable was : True");
		}catch(Exception e) {
			softAssertionFail("Exception came while trying to wait & identify element "+elementName+" with following message :",e);
		}
		return element;
	}

	//********************* Method() Performs Mouse Hover Action On WebElement *********************
	protected synchronized void waitForMouseHoverAction(WebElement element, int timeOut, String elementName) {
		try {
			wait = new FluentWait<WebDriver>((WebDriver) driver).withTimeout(Duration.ofSeconds(timeOut)).pollingEvery(Duration.ofSeconds(1));
			wait.until(ExpectedConditions.visibilityOf(element));
			Actions actions = new Actions(driver);
			actions.moveToElement(element).build().perform();
			addMessageToExtentReportAndLogger("Mouse hover performed on Element : " + elementName);
		} catch (Exception e) {
			hardAssertionFail("Exception occured while performing mouse hover on " + elementName + " with following message", e);
		}
	}

	//********************* Method() Performs Mouse Hover Action On WebElement *********************
	protected synchronized void waitForMouseHoverActionFromList(List<WebElement> elements, int timeOut, String elementName) {
		try {
			listOfMouseHoverWebElementAbstraction(elements, timeOut, elementName);
			addMessageToExtentReportAndLogger("Mouse hover performed on Element : " + elementName);
		} catch (Exception e) {
			hardAssertionFail("Exception occured while performing mouse hover on " + elementName + " with following message", e);
		}
	}

	//********************* Method() Performs Mouse Hover & Click Action On WebElement *********************
	protected synchronized void waitForMouseHoverAndClick(WebElement HoverElement, String elementName) throws Exception {
		try {
			Actions actions = new Actions(driver);
			actions.moveToElement(HoverElement);
			actions.click().build().perform();
			addMessageToExtentReportAndLogger("Mouse hover performed on Element : " + elementName);
		} catch (Exception e) {
			hardAssertionFail("Exception occured while performing mouse hover on " + elementName + " with following message", e);
		}
	}

	//************************* Method() Clicks WebElement Using JavaScript *************************
	protected synchronized void clickWebElementJavaScriptExecutor(WebElement element, String elementName)
			throws Exception {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			Thread.sleep(2000);
			js.executeScript("arguments[0].click()", element);
			addMessageToExtentReportAndLogger("Clicking on element : "+elementName);
		} catch (Exception e) {
			hardAssertionFail("Exception occured while clicking  on element "+elementName+" via javascript with following message", e);
		}
	}

	//************************* Method() Clicks WebElement Using JavaScript *************************
	protected synchronized void clickWebElementJavaScriptExecutorFromList(List<WebElement> elements, String elementName) {
		try {
			clickJavaScriptListOfWebElementAbstraction(elements);
			setLogMessage("Clicking on element : "+elementName);
			addMessageToExtentReportAndLogger("Clicking on element : "+elementName);
		} catch (Exception e) {
			hardAssertionFail("Exception occured while clicking  on element "+elementName+" via javascript with following message", e);
		}
	}

	protected synchronized void clickJavaScriptListOfWebElementAbstraction(List<WebElement> elements) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			Thread.sleep(2000);

			int elementSize=elements.size(),i=0;

			if(elementSize!=0) {
				while(i<elementSize) {
					try {
						js.executeScript("arguments[0].click()", elements.get(i));	
						break;
					}catch(Exception e) {
						softAssertionWarning("Exception occurred while waiting to click on element with following message", e);
						i++;
						if(i==2) {
							hardAssertionFail("Exception occurred while waiting to click on element with following message", e);
							break;
						}
					}
				}		
			}else {
				try {
					js.executeScript("arguments[0].click()", elements.get(0));	
				}catch(Exception e) {
					hardAssertionFail("Exception occurred while waiting to click on element with following message", e);
				}
			}
		}catch(Exception e) {
		}
	}

	//********************* Method() Waits Until Page Does Not Load Using JavaScript *********************
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
			addMessageToExtentReportAndLogger("Page gets loaded successfully.");
		} catch (Exception e) {
			softAssertionFail("Error occured while page load using javascript with following message :",e);
		}
	}

	//***************************** Method() To Wait For Sub-Category ****************************	
	protected synchronized void dynamicElementWaitHandling(WebElement element) {
		double startTime = startTime();
		boolean elementDisplay=false;
		long starttime = System.currentTimeMillis();
		long endtime = starttime + Long.parseLong(Utility.sJSONReader("bounceXTimeOut"));
		while(System.currentTimeMillis() < endtime)
		{		
			try{
				elementDisplay = element.isDisplayed();
				if (elementDisplay== true) {
					String endTime = endTime(startTime);
					ExtentTestManager.logInfo("Sub-Category Menu was found displayed after waiting for "+endTime+" Sec.");
					addLogInfo("Sub-Category Menu was found displayed after waiting for "+endTime+" Sec.");
					waitForPageLoaded();
					break;
				}
			}catch(Exception e) {
				softAssertionFail("Exception came while trying to wait & identify sub-category menu :",e);
			}
		}
	}

	//********************************* Method() Scrolls To View Element *********************************
	protected synchronized void scrollIntoViewElement(WebElement element, int timeOut, String elementName) {
		try {
			wait = new FluentWait<WebDriver>((WebDriver) driver).withTimeout(Duration.ofSeconds(timeOut)).pollingEvery(Duration.ofSeconds(1));
			wait.until(ExpectedConditions.visibilityOf(element));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			addMessageToExtentReportAndLogger(elementName +"got scrolled successfully");
		}catch(Exception e) {
		}
	}

	//********************************* Method() Scrolls To View Element *********************************
	protected synchronized void scrollIntoViewElementFromList(List<WebElement> elements, int timeOut) {
		try {
			scrollIntoViewElementsAbstraction(elements, timeOut);
			addMessageToExtentReportAndLogger("Element got scrolled successfully");
		}catch(Exception e) {
		}
	}

	protected synchronized void scrollIntoViewElementsAbstraction(List<WebElement> elements, int timeOut) {
		int i=0;
		while(i<=1) {
			try {
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", elements.get(i));
				break;
			}catch(Exception e) {
				i++;
			}
		}			
	}

	//********************************* Method() Mouse Over To View Element *********************************
	protected synchronized void onMouseOverToViewElement(WebElement element, int timeOut, String elementName) {
		try {
			wait = new FluentWait<WebDriver>((WebDriver) driver).withTimeout(Duration.ofSeconds(timeOut)).pollingEvery(Duration.ofSeconds(1));
			wait.until(ExpectedConditions.visibilityOf(element));
			((JavascriptExecutor) driver).executeScript("arguments[0].onmouseover();", element);
			addMessageToExtentReportAndLogger("Mouse hover done successfully on "+elementName);
		}catch(Exception e) {

		}
	}

	//************************** Method() Wait Until WebElement Is Not Click-able *************************
	protected synchronized Boolean waitUntilElementIsVisibleAndClickable(WebElement element, int timeOut, String elementName){
		Boolean flag = false;
		try{
			wait = new FluentWait<WebDriver>((WebDriver) driver).withTimeout(Duration.ofSeconds(timeOut)).pollingEvery(Duration.ofSeconds(1));
			element = wait.until(ExpectedConditions.elementToBeClickable(element));
			String style = element.getAttribute("style");
			if(style.contains("hidden")){
				addLogInfo("Element is hidden");
			}
			else if(style.contains("visible")){
				addLogInfo("Element is visible and clickable");
				flag = true;
			}else{
				addLogInfo("Element is not displayed");
			}
		} catch(Exception e){
			softAssertionFail("Exception came while trying to wait & identify element with following message :",e);
		}
		return flag;
	}

	//*************************** Method() to Click on Anchor tag<a> Using Href *************************** 
	protected synchronized void clickLinkByHref(WebDriver driver, String href) {
		try {
			List<WebElement> anchors = driver.findElements(By.tagName("a"));
			Iterator<WebElement> i = anchors.iterator();
			while(i.hasNext()) {
				WebElement anchor = i.next();
				if(anchor.getAttribute("href").contains(href)) {
					anchor.click();
					addMessageToExtentReportAndLogger("Successfully clicked on element");
					break;
				}
			}
		}catch(Exception e) {
			softAssertionFail("Exception came while trying to wait & identify element with following message :",e);
		}
	}

	//***************************** Method() Clicks List<WebElement> Inside Modal ***********************
	protected synchronized void clickInsideModalWithWebElements(WebElement element, int timeOut, String elementName) {	
		try{
			wait = new FluentWait<WebDriver>((WebDriver) driver).withTimeout(Duration.ofSeconds(timeOut)).pollingEvery(Duration.ofSeconds(1));
			wait.until(ExpectedConditions.elementToBeClickable(element));
			clickOnElement(element, elementName);
		}catch(Exception e) {
			softAssertionFail("Exception came while trying to wait & identify element with following message :",e);
		}
	}

	//******************************* Method() Clicks WebElement Inside Modal *************************
	protected synchronized void clickInsideModal(WebElement element, int timeOut, String elementName) {	
		try{
			wait = new FluentWait<WebDriver>((WebDriver) driver).withTimeout(Duration.ofSeconds(timeOut)).pollingEvery(Duration.ofSeconds(1));
			wait.until(ExpectedConditions.visibilityOf(element));
			clickOnElement(element, elementName);
		}catch(Exception e) {
			softAssertionFail("Exception occurred while trying to locate " + elementName + " with following message", e);
		}
	}

	//******************************* Method() Clicks WebElement Inside Modal *************************
	protected synchronized void clickInsideModalFromList(List<WebElement> elements, int timeOut, String elementName) {	
		try{
			clickInsideModalListOfWebElementAbstraction(elements, timeOut,elementName);
		}catch(Exception e) {
			softAssertionFail("Exception occurred while trying to locate " + elementName + " with following message", e);
		}
	}

	protected synchronized void waitForModalLoading(WebElement element, int timeOut, String elementName) {	
		try{
			wait = new FluentWait<WebDriver>((WebDriver) driver).withTimeout(Duration.ofSeconds(timeOut)).pollingEvery(Duration.ofSeconds(1));
			wait.until(ExpectedConditions.visibilityOf(element));
			addMessageToExtentReportAndLogger(elementName+" got triggered succesfully");
		}catch(Exception e) {
			softAssertionFail("Exception occurred while trying to locate " + elementName + " with following message :",e);
		}
	}

	protected synchronized void waitForModalLoadingFromList(List<WebElement> elements, int timeOut, String elementName) {	
		try{
			waitForModalLoadingOnElementsAbstraction(elements, timeOut);
			addMessageToExtentReportAndLogger(elementName+" got triggered succesfully");
		}catch(Exception e) {
			softAssertionFail("Exception occurred while trying to locate " + elementName + " with following message :",e);
		}
	}

	protected synchronized void waitForModalLoadingOnElementsAbstraction(List<WebElement> elements, int timeOut) {
		int elementCount = elements.size();
		for(int i=0; i<elementCount; i++) {
			try {
				if(elements.size()==0) {
					wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut)).pollingEvery(Duration.ofSeconds(1));
					wait.until(ExpectedConditions.visibilityOfAllElements(elements));
					break;
				}else {
					break;
				}
			}catch(Exception e) {
				if(i==1) {
					break;
				}
			}
		}
	}

	//********************************** Method() Performs Wait & Click on Element ****************************
	protected synchronized boolean waitForElementAndClick(WebDriver driver, String elementXpath) {
		boolean elementPresent = false;
		try{                    
			wait = new FluentWait<WebDriver>((WebDriver) driver).withTimeout(Duration.ofSeconds(20)).pollingEvery(Duration.ofSeconds(1));
			elementPresent = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(elementXpath))).isDisplayed();
			addMessageToExtentReportAndLogger("Element is present [T/F]..? " +elementPresent);
		}        
		catch(Exception e){
			softAssertionFail("Exception occurred while trying to wait & identify " + elementXpath + " Modal with following message :",e);
		}          
		return elementPresent;   
	}

	//********************************** Method() For Capturing Log ****************************************
	protected synchronized void addLogInfo(String message){
		String browser=Utility.sJSONReader("browser");
		browser = browser.substring(0, 1).toUpperCase() + browser.substring(1);
		System.out.println("Thread ID : "+getThreadValue(DriverManager.dr.get())+" | Browser : "+browser+" | "+message);
	}

	//********************************** Method() For Capturing Log ****************************************
	protected synchronized void addLogError(String message){
		String browser=Utility.sJSONReader("browser");
		browser = browser.substring(0, 1).toUpperCase() + browser.substring(1);
		System.out.println("Thread ID : "+getThreadValue(DriverManager.dr.get())+" | Browser : "+browser+" | "+message);
	}

	//************************************ Method() Returns the Thread ID **********************************
	protected synchronized String getThreadValue(Object value){	
		String text = value.toString();
		String[] nextText = text.split(" ");
		String text2 = nextText[nextText.length-1].replace("(", "").replace(")", "");
		return text2;
	}

	//************************************** Method() Switches to iFrame **************************************
	protected synchronized void swtichToIframe(WebDriver driver, WebElement element){
		try {
			waitForElementVisibility(element, Constants.LONGTIMEOUT, "Sparkred-iFrame");
			driver.switchTo().frame(element);
			Thread.sleep(2000);
			addMessageToExtentReportAndLogger("Switched within iFrame successfully");
		} catch (Exception e) {
			softAssertionFail("Exception occurred while trying to wait & switch to iFrame with following message :",e);
		}
	} 

	//************************************** Method() Switches to iFrame **************************************
	protected synchronized void switchToDefaultContent(WebDriver driver) throws Exception {
		try {
			driver.switchTo().defaultContent();
			addMessageToExtentReportAndLogger("Switched outside iFrame successfully");
		} catch (Exception e) {
			softAssertionFail("Exception occurred while trying to wait & switch to iFrame with following message :",e);
		}
	}

	protected synchronized void listOfMouseHoverWebElementAbstraction(List<WebElement> elements, int timeOut, String elementName) {
		int elementSize=elements.size(),i=0;

		if(elementSize!=0) {
			while(i<elementSize) {
				try {
					Actions actions = new Actions(driver);
					actions.moveToElement(elements.get(i)).build().perform();	
					break;
				}catch(Exception e) {
					softAssertionWarning("Exception occured while performing mouse hover in 1st iteration on " + elementName + " with following message", e);
					i++;
					if(i==2) {
						hardAssertionFail("Exception occured while performing mouse hover in 2nd iteration on " + elementName + " with following message", e);
						break;
					}
				}
			}		
		}else {
			try {
				Actions actions = new Actions(driver);
				actions.moveToElement(elements.get(0)).build().perform();	
			}catch(Exception e) {
				hardAssertionFail("Exception occured while performing mouse hover on " + elementName + " with following message", e);
			}
		}
	}

	protected synchronized void clickInsideModalListOfWebElementAbstraction(List<WebElement> elements, int timeOut, String elementName) {
		int elementSize=elements.size(),i=0;

		if(elementSize!=0) {
			while(i<elementSize) {
				try {
					elements.get(i).click();	
					break;
				}catch(Exception e) {
					softAssertionWarning("Exception occured while performing mouse hover in 1st iteration on " + elementName + " with following message", e);
					i++;
					if(i==2) {
						hardAssertionFail("Exception occured while performing mouse hover in 2nd iteration on " + elementName + " with following message", e);
						break;
					}
				}
			}		
		}else {
			try {
				elements.get(0).click();	
			}catch(Exception e) {
				hardAssertionFail("Exception occured while performing mouse hover on " + elementName + " with following message", e);
			}
		}
	}

	//********************* Method() Filters XPath *********************
	protected synchronized String removeUnwantedCharacters(String element) {
		String parts[] = element.split("->");
		return parts[1].substring(0, parts[1].length() - 1).trim();
	}

	//********************* Method() Returns Start Time *********************
	protected synchronized static double startTime() {
		double starttime = System.currentTimeMillis();
		return starttime;
	}

	//********************* Method() Returns Time Taken During The Wait For An Element *********************
	protected synchronized static String endTime(double starttime) {
		double lasttime = System.currentTimeMillis();
		double diff = (lasttime-starttime)/1000;	
		String meanTime = String.format("%.02f", diff);
		return meanTime;
	}

	protected synchronized String fullStackTrace(Exception e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		String fullStackTrace = sw.toString();
		return fullStackTrace;
	}

	protected synchronized static void softAssertion(String message) {
		try {
			Assert.assertTrue(message, false);
		}catch(Throwable t) {
		}
	}

	protected synchronized static void hardAssertion(String message) {
		Assert.assertTrue(message, false);	
	}

	protected synchronized void softAssertionWarning(String message, Exception e) {
		try {
			ExtentTestManager.logWarning(fullStackTrace(e));
			addLogError(message+" : "+returnLocator(fullStackTrace(e)));
			softAssertion(message+" : "+returnLocator(fullStackTrace(e)));	
		}catch(Exception ex) {
			ExtentTestManager.logInfo("Exception occured during the processing of softAssertionWarning()");
		}
	}

	protected synchronized void softAssertionFail(String message, Exception e) {
		try {
			ExtentTestManager.logFailed(fullStackTrace(e));
			addLogError(message+" : "+returnLocator(fullStackTrace(e)));
			softAssertion(message+" : "+returnLocator(fullStackTrace(e)));	
		}catch(Exception ex) {
			ExtentTestManager.logInfo("Exception occured during the processing of softAssertionFail()");
		}
	}

	protected synchronized void addMessageToExtentReportAndLogger(String message) {
		try {
			ExtentTestManager.logInfo(message);
			addLogInfo(message);
		}catch(Exception ex) {
		}
	}

	protected synchronized void hardAssertionFail(String message, Exception e) {
		ExtentTestManager.logFailed(fullStackTrace(e));
		setLogMessage(fullStackTrace(e));
		addLogError(message+" : "+returnLocator(fullStackTrace(e)));
		hardAssertion(message+" : "+returnLocator(fullStackTrace(e)));	
	}

	public synchronized static String returnLocator(String text) {
		int startPos = text.indexOf("Caused by");
		int endPos = text.indexOf("}", startPos);
		if(startPos >=0 && endPos >=0) {
			text = text.substring(startPos, endPos);
		}
		return text;
	}

	public synchronized static void setLogMessage(String text) {
		message.write("<details>" + "<summary>" + "<b>" + "<font color=" + "red>" + "Exception Occured : Click on the link to see message"
				+ "</font>" + "</b >" + "</summary>" + "<br>" + "<h6>" + "<b>" + BasePage.returnLocator(text) +  "</b>"+ "</h6>" + "</br>" + text.replaceAll(",", "<br>")+"</details>"+" \n");
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

	
	/**
	 * Verify that current page URL matches the expected URL.
	 *
	 * @return the CheckPaymentPage class instance.
	 */
	public String verifyPageUrl() {
		(new WebDriverWait(driver, LOAD_TIMEOUT)).until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver d) {
				return d.getCurrentUrl().contains("factory.anntaylor");
			}
		});
		return "";
	}

	//	  /**
	//	   * An expectation for checking if the given text is present in the specified element.
	//	   *
	//	   * @param element the WebElement
	//	   * @param text    to be present in the element
	//	   * @return true once the element contains the given text
	//	   */
	//	  public static ExpectedCondition<Boolean> textToBePresentInElement(final WebElement element,
	//	                                                                    final String text) {
	//
	//	    return new ExpectedCondition<Boolean>() {
	//	      @Override
	//	      public Boolean apply(WebDriver driver) {
	//	        try {
	//	          String elementText = element.getText();
	//	          return elementText.contains(text);
	//	        } catch (StaleElementReferenceException e) {
	//	          return null;
	//	        }
	//	      }	
}