package com.ascena.lb.page.objects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import com.ascena.automation.extent.listeners.ExtentTestManager;
import com.ascena.automation.utilities.BasePage2;
import com.ascena.automation.utilities.InitialDataSetup;
import com.ascena.automation.utilities.JSONReader;
import com.ascena.automation.utilities.Utility;

public class HomePage extends BasePage2 {

	@FindBy(css = "section#home-hero")
	private WebElement hero;

	@FindBy(xpath = "//div[contains(@class,'logo')]/a")
	private WebElement logo;

	@FindBy(css = "span[class*='header-country']")
	private WebElement country;

	@FindBy(css = "input#Ntt")
	private WebElement searchBox;

	@FindBy(css = "div.rfk_msg_results")
	private WebElement msgResult;

	@FindBy(css = "div[class*='IN English']")
	private WebElement countryModal;

	@FindBy(xpath = "//a[contains(text(),'Click here')]")
	private WebElement clickHere;

	@FindBy(xpath = "//a[contains(text(),'Ship to US')]")
	private WebElement shipToUS;

	@FindBy(css = "div#globo-box")
	private WebElement promoTop;

	@FindBy(css = "div.home-box")
	private WebElement heroBox;

	@FindBy(css = "div#drawer-toggle")
	private WebElement offerToggle;

	@FindBy(css = "button[aria-label^='Close the modal']")
	private WebElement closeModal;

	@FindBy(xpath = "(//div[@id='globo-box']//span[contains(.,'details')])[1]")
	private WebElement promoDetails;

	@FindBy(xpath = "//div[@id='offer-details']/h3")
	private List<WebElement> promoHeaders;

	@FindBy(xpath = "//div[@class='promo-item drawer']")
	private List<WebElement> promoDrawers;

	@FindBy(xpath = "//div[@id='offer-details']")
	private WebElement promoModal;

	@FindBy(xpath = "//li[contains(@class,'rfk_title')]//div[contains(.,'Did You Mean')]/../../..//a")
	private List<WebElement> didYouMeanList;

	@FindBy(xpath = "//ul[contains(@class,'mar-nav')][contains(@role,'navigation')]/li[contains(@class,'xs-one-whole')]/a")
	private List<WebElement> l1List;

	private String l1First = "//a[contains(@class,'asc-nav-parent menu asc-nav-category')][contains(.,'";

	private String l1Second = "')]";

	public HomePage() {
		PageFactory.initElements(driver, this);
	}

	@Override
	protected ExpectedCondition<?> getPageLoadCondition() {
		return null;
	}

	/**
	 * @author aha
	 * @throws Exception
	 * @purpose Invokes the browser and verifies the US country selection
	 */
	public synchronized HomePage invokeBrowserAndVerifyCountry() throws Exception {

		try {
			invokeBrowser();

			if (verifyElementDisplayedForSeconds(countryModal, 10, "Country Selection Modal")) {
				clickWebElementJavaScriptExecutor(clickHere, "Click Here");
				waitForElementVisibilityAndClick(shipToUS, 5, "Ship to US");
			}

			scrollIntoViewElement(country, 5, "Footer Country");
			ExtentTestManager.logInfo("Scrolled down to footer section");

			Assert.assertTrue("US COUNTRY IS NOT SELECTED", country.getText().trim().equalsIgnoreCase("United States"));
			ExtentTestManager.logInfo("UNITED STATES COUNTRY ALREADY SELECTED");
			
			scrollIntoViewElement(logo, 5, "logo");
			
		} catch (Exception e) {
			hardAssertionFail("An error occured while selecting country", e);
		}
		return this;
	}

	public synchronized HomePage clickOnLogoAndVerifyNavigation() throws Exception {

		try {
			clickWebElementJavaScriptExecutor(logo, logo.getAttribute("href").split("[.]")[1] + " brand logo");

			Assert.assertTrue("Unable navigate to home page on click of brand logo",
					driver.getCurrentUrl().equalsIgnoreCase(JSONReader.getURL(InitialDataSetup.getEnv())));

		} catch (Exception | AssertionError e) {
			hardAssertionFail("Unable navigate to home page on click of brand logo", e);
		}
		return this;
	}

	public synchronized HomePage verifyPromotions() throws Exception {

		List<String> handles = null;

		try {

			ExtentTestManager.logInfo("<b>" + "<font color=" + "\"yellow\""
					+ ">Validating hero modal url by opening in another tab</font>" + "</b>");
			
			new Actions(driver).keyDown(Keys.CONTROL).click(heroBox).keyUp(Keys.CONTROL).build().perform();
			ExtentTestManager.logInfo("Opened hero modal on another tab");

			handles = new ArrayList<>(driver.getWindowHandles());

			driver.switchTo().window(handles.get(1));
			ExtentTestManager.logInfo("Switched to child window");

			ExtentTestManager.logInfo(driver.getCurrentUrl()+" is the hero Modal URL");
			driver.close();

			driver.switchTo().window(handles.get(0));
			ExtentTestManager.logInfo("Switched to parent window again");

			ExtentTestManager.logInfo("<b>" + "<font color=" + "\"yellow\""
					+ ">Validating promotion display on globo-box</font>" + "</b>");

			softAssert.softAssertTrue(verifyElementDisplayedForSeconds(promoTop, 3, "promo message"),
					"Promotions messages not displayed on top of header");

			clickWebElementJavaScriptExecutor(promoDetails, "Promotion Details link");

			softAssert.softAssertTrue(verifyElementDisplayedForSeconds(promoModal, 3, "promo modal"),
					"Promotion modal not displayed");

			ExtentTestManager.logInfo("Below are the promotions messages found on the modal:");

			promoHeaders.forEach(element -> {
				ExtentTestManager.logInfo(element.getText().trim().toUpperCase());
			});

			clickWebElementJavaScriptExecutor(closeModal, "Close Modal");

			ExtentTestManager
					.logInfo("<b>" + "<font color=" + "\"yellow\"" + ">Validating today's offers</font>" + "</b>");

			clickWebElementJavaScriptExecutor(offerToggle, "Offer Toggle");

		} catch (Exception | AssertionError e) {
			hardAssertionFail("An error occured while validating promotions", e);
		}
		return this;
	}

	public HomePage verifySearch(String keyword) {

		try {

			typeElementValue(searchBox, keyword, "Search box");
			waitForElementVisibility(msgResult, 5, "Results Heading");
			softAssert.softAssertTrue(
					msgResult.getText().trim().equalsIgnoreCase("Top Results for " + "\"" + keyword + "\""),
					"Search results heading is not displayed");
			
			System.out.println(msgResult.getText().trim());
			System.out.println("Top Results for " + "\"" + keyword + "\"");

			String originalWindow = driver.getWindowHandle();
			
			System.out.println(didYouMeanList.size());

			didYouMeanList.forEach(element -> {

				String text = element.getText().trim().toUpperCase();
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				new Actions(driver).keyDown(Keys.CONTROL).click(element).keyUp(Keys.CONTROL).build().perform();
				driver.switchTo().window(new ArrayList<String>(driver.getWindowHandles()).get(1));
				softAssert.softAssertTrue(driver.getCurrentUrl().contains("preview_search"),
						text + " link redirection not displayed");
				driver.close();
				driver.switchTo().window(originalWindow);

			});

			clickEnterOnElement(searchBox, "Search Box");
			softAssert.softAssertTrue(driver.getCurrentUrl().contains("Ntt=" + keyword),
					" Search page redirection not as expected");
		} catch (Exception | AssertionError e) {
			hardAssertionFail("An error occured while validating search", e);
		}

		return this;

	}

	public HomePage verifyL1Category() {
		try {
			List<String> l1 = new ArrayList<String>() {
				{
					add("WHAT'S NEW");
					add("CLOTHING");
					add("LIVI ACTIVE");
					add("SHOES & ACCESSORIES");
					add("SWIM");
					add("CACIQUE INTIMATES");
					add("SALE");
				}
			};
			waitForElementVisibilityFromList(l1List, 3, "All L1 Categories");
			Iterator<String> itr = l1.iterator();
			System.out.println(l1);

			for (WebElement string : l1List) {
				System.out.println(string.getText());
			}

			l1List.iterator().forEachRemaining(

					element -> {

						System.out.println(element.getText().trim());

						softAssert.softAssertTrue(element.getText().trim().equalsIgnoreCase(itr.next()),
								element.getText().trim() + " is not displayed");

					});
		} catch (Exception | AssertionError e) {
			hardAssertionFail("An error occured while validating L1 Category", e);
		}
		return this;
	}

	public HomePage navigateToPlp(String plpName) {
		try {

			String testdata = Utility.getDataAsStr("LBTestData", plpName, "LBPlpName");

			System.out.println(testdata);

			Thread.sleep(5000);

			waitForMouseHoverAction(driver.findElement(By.xpath(l1First + testdata.split(":")[0] + l1Second)), 3,
					"L1 category:" + testdata.split(":")[0]);

		} catch (Exception | AssertionError e) {
			hardAssertionFail("An error occured while Navigating to plp", e);
		}
		return this;
	}

}
