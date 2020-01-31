package com.ascena.at.page.objects;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.ascena.at.locators.HomePageLocators;
import com.ascena.at.locators.PDPLocators;
import com.ascena.automation.extent.listeners.ExtentTestManager;
import com.ascena.automation.utilities.BasePage;
import com.ascena.automation.utilities.Constants;
import com.ascena.automation.utilities.DriverManager;
import com.ascena.automation.utilities.Utility;

import io.cucumber.datatable.DataTable;

/**
 * Step Action implementation class for Cucumber Steps defined in Feature file
 */

public class HomePage extends BasePage{
	
	/**
	 * 
	 */
	public HomePage() {
		PageFactory.initElements(DriverManager.getDriver(), this);
	}

	@FindAll({	
		@FindBy(css = HomePageLocators.factoryBouncePopUp),
		@FindBy(xpath = HomePageLocators.outletBouncePopUp),
	}) public List<WebElement> bouncePopUp;

	@FindAll({	
		@FindBy(css = HomePageLocators.megaMenu),
		@FindBy(css= HomePageLocators.anntaylorImage),
	}) public List<WebElement> pageLoadCondition;

	@FindAll({	
		@FindBy(xpath = HomePageLocators.annMegaMenuSupCat),
		@FindBy(xpath = HomePageLocators.megaMenuSupCat),
	}) public List<WebElement> megaMenuSupCat;

	@FindAll({	
		@FindBy(css = HomePageLocators.annMegaMenuSubCat),
		@FindBy(xpath = HomePageLocators.megaMenuSubCat),
	}) public List<WebElement> megaMenuSubCat;

	@FindAll({	
		@FindBy(css = HomePageLocators.signInButton),
		@FindBy(css = HomePageLocators.annSignInButton),
	}) public List<WebElement> signInButton;
	
	@FindAll({	
		@FindBy(css = HomePageLocators.signInLink),
		@FindBy(xpath = HomePageLocators.annSignInLink),
	}) public List<WebElement> signInLink;
	
	@FindAll({	
		@FindBy(css = HomePageLocators.searchButton),
		@FindBy(css = HomePageLocators.annSearchButton),
	}) public List<WebElement> searchButton;
	
	@FindAll({	
		@FindBy(css = HomePageLocators.searchTextButton),
		@FindBy(css = HomePageLocators.annSearchTextButton),
	}) public List<WebElement> searchTextButton;
	
	@FindAll({	
		@FindBy(css = HomePageLocators.searchIcon),
		@FindBy(css = HomePageLocators.searchIconAnntaylor),
	}) public List<WebElement> searchIcon;
	
	@FindAll({	
		@FindBy(css = PDPLocators.addToBagButton),
		@FindBy(css = PDPLocators.anntaylorAddToBagButton),
	}) public List<WebElement> addToBagButton;
	
	@FindAll({	
		@FindBy(css = PDPLocators.colorSwatch),
		@FindBy(xpath = PDPLocators.anntaylorColorSwatch),
	}) public List<WebElement> colorSwatch;

	@FindAll({	
		@FindBy(css = PDPLocators.sizeSwatch),
		@FindBy(xpath = PDPLocators.anntaylorSizeSwatch),
	}) public List<WebElement> sizeSwatch;

	@FindAll({	
		@FindBy(css = PDPLocators.addToBagModal),
		@FindBy(css = PDPLocators.anntaylorAddtoBagModal),
	}) public List<WebElement> addToBagModal;
	
	@FindAll({	
		@FindBy(css = PDPLocators.factoryFlyMiniCheckoutHeader),
		@FindBy(css = PDPLocators.outletFlyMiniCheckoutHeader),
	}) public List<WebElement> checkoutCloseIcon;
	
	@FindAll({	
		@FindBy(xpath = PDPLocators.addToBagModalConShopButton),
		@FindBy(xpath = PDPLocators.anntaylorAddToBagModalConShopButton),
	}) public List<WebElement> addToBagModalContinueShoppingButton;
	
	@FindBy(xpath = HomePageLocators.searchSpan) 
	public WebElement searchSpan;
	
	@FindBy(css = HomePageLocators.selectCountry) 
	public WebElement selectCountry;
	
	@FindBy(css = HomePageLocators.countryText) 
	public WebElement countryText;
	
	@FindBy(css = HomePageLocators.countrySelectionButton)
	public WebElement countrySelectionButton;
	
	@FindBy(css = HomePageLocators.internationalContextModal) 
	public WebElement internationalContextModal;
	
	@FindBy(css = HomePageLocators.internationalContextImage) 
	public WebElement internationalContextImage;

	public synchronized void bounceXHandler() {
		if (Utility.sJSONReader("bounceXStatus").equalsIgnoreCase("ON")) {
			try {
				bounceXHandlerAbstraction(bouncePopUp, Constants.AVGTIMEOUT);
				ExtentTestManager.logInfo("Bounce X Pop-up displayed on site after waiting for 10 Sec.");
				addLogInfo("Bounce X Pop-up displayed on site after waiting for 10 Sec.");
				invisibilityOfElementLocated(By.xpath(HomePageLocators.factoryBouncePopUp),"Bounce X");
			}catch(Exception e) {
				addLogError("Something went wrong during the call of bounceXHandler() and following exception came :"+e.getMessage());
			}
		}
	}

	public synchronized void ecommCountrySelection() {

		String currentURL = driver.getCurrentUrl().toString().toLowerCase();
		currentURL = checkURL(currentURL);
		if(currentURL.contains("anntaylor") || currentURL.contains("loft")) {
			String link = internationalContextImage.getAttribute("alt");
			if((link.trim().equalsIgnoreCase("ship to IN"))) {
				waitForMouseHoverAction(selectCountry, Constants.AVGTIMEOUT, "Country Selection");
				//waitForElementVisibility(countryText, Constants.AVGTIMEOUT, "Country Name");
//				String countryName = countryText.getText();
//				if(!countryName.trim().equalsIgnoreCase("India")) {
//					ExtentTestManager.logWarning("Default Country found as : "+countryName+" and Expected Country was : "+"India");
//					addLogError("Default Country found as : "+countryName+" and Expected Country was : "+"India");
//				}
				clickOnElement(countrySelectionButton, "Select U.S.");
				invisibilityOfElementLocated(By.xpath(HomePageLocators.internationalContextModal),"International Context");	
			}		
		}
	}
	
	public synchronized String checkURL(String currentURL) {
		if(currentURL.contains("anntaylor") || currentURL.contains("loft")) {
			if(currentURL.contains("factory") || currentURL.contains("outlet")) {
				currentURL="";
			}else {
				if(currentURL.contains("anntaylor")) {
					currentURL="anntaylor";
				}else if(currentURL.contains("loft")) {
					currentURL="loft";
				}
			}
		}
		return currentURL;
	}

	//------------------------------ Method() To Launch Brand Site ------------------------------
	public synchronized HomePage launchBrandSite(String siteName) {
		try {
			Capabilities caps = ((RemoteWebDriver) DriverManager.getDriver()).getCapabilities();
			String osName = System.getProperty("os.name").substring(0, 1).toUpperCase() + System.getProperty("os.name").substring(1);
			String browserName = caps.getBrowserName().substring(0, 1).toUpperCase() + caps.getBrowserName().substring(1);
			String browserVersion = caps.getVersion();
			
			siteName = (Utility.getDataAsString(Utility.sJSONReader("SiteWBookName"), Utility.sJSONReader("SiteTData"), siteName, Utility.sJSONReader("SiteBFIX"))).trim();
			
			DriverManager.getDriver().get(siteName);
			//String stext = verifyPageUrl();
			//System.out.println(stext);
			//bounceXHandler();
			setLogMessage(siteName+" launched on "+browserName+" Version "+browserVersion+ " on "+osName);
			ExtentTestManager.logInfo("Site launched : "+siteName);
			//addLogInfo(siteName+" launched on "+browserName+" Version "+browserVersion+ " on "+osName);
		}catch(Exception e) {
			hardAssertionFail("Exception occured during the Site launch with following message", e);
		}
		return (HomePage) openPage(HomePage.class);
	}

	public PLPPage ecommPLPNavigation() {	
		try{
			waitForPageLoaded(); 
			waitForMouseHoverActionFromList(megaMenuSupCat, Constants.SHORTTIMEOUT, "Category -> Clothing");
			clickWebElementJavaScriptExecutorFromList(megaMenuSubCat, "Sub Category -> Sweaters");
		}catch(Exception e) {
			hardAssertionFail("Exception occured during the PLP Navagation with following message", e);
		}
		return (PLPPage) openPage(PLPPage.class);
	}

	public SignInPage ecommSignInPageNavigation() {
		try {
			Thread.sleep(2000);
			waitForMouseHoverActionFromList(signInButton, Constants.SHORTTIMEOUT, "Sign In (Header)");
			waitForElementVisibilityAndClickFromList(signInLink, Constants.SHORTTIMEOUT, "Sign In Link");
		}catch(Exception e) {	
			hardAssertionFail("Exception occured during the SignIn Page Navagation with following message", e);
		}
		return (SignInPage) openPage(SignInPage.class);
	}

	public PDPPage ecommProductSearch(DataTable table) {
		try {
			String styleId;
			List<Map<String, String>> data = table.asMaps(String.class,String.class);
			for(Map<String, String> list : data) {
				styleId = Utility.getDataAsString("ProductInfo", "TestData", list.get("Style_ID"), "StyleID");
				Thread.sleep(2000);
				waitForElementVisibilityAndClickFromList(searchButton, Constants.AVGTIMEOUT, "Search Button");
				typeElementValueFromList(searchTextButton, styleId,"Search Style ID");
				clickEnterOnElementFromList(searchIcon, "Hit Enter on Search Button");
			}
		}catch(Exception e) {
			hardAssertionFail("Exception occured during the Product Search with following message : ", e);
		}
		return (PDPPage) openPage(PDPPage.class);
	}

	public synchronized PDPPage ecommAddMultipleProducts(String siteName, DataTable table) {
		try {
		String environment = Utility.getDataAsStringByRow("SiteURL",1,"Environment");
		String excelWorkBookName = returnExcelWBookName(environment,siteName);
		
		String styleId = null, color = null, size = null;
			List<Map<String, String>> data = table.asMaps(String.class,String.class);

			for(Map<String, String> list : data) {
				styleId = Utility.getDataAsString(excelWorkBookName, "TestData", list.get("Style_ID"), "StyleID");
				color = Utility.getDataAsString(excelWorkBookName, "TestData", list.get("Product_Color"), "Color");
				size = Utility.getDataAsString(excelWorkBookName, "TestData", list.get("Product_Size"), "Size");
				Thread.sleep(2000);
				waitForElementVisibilityAndClickFromList(searchButton, Constants.AVGTIMEOUT, "Search Button");
				typeElementValueFromList(searchTextButton, styleId,"Search Style ID");
				clickEnterOnElementFromList(searchIcon, "Hit Enter on Search Button");
				Thread.sleep(5000);
				//waitForMouseHoverActionFromList(addToBagButton, 5, "Add To Bag Button");
				waitForVisibilityOfElementsLocated(colorSwatch, 2, "PDP SKU");

				outercolorloop:
					for (int i=0; i<=colorSwatch.size(); i++) {
						String sColor = colorSwatch.get(i).getAttribute("aria-label");
						sColor = sColor.split(":")[1].trim();
						if(sColor.equalsIgnoreCase(color.trim())){
							if(colorSwatch.get(i).isSelected()) {
								break outercolorloop;	
							}else {
								clickOnElement(colorSwatch.get(i), "Product's Color");
								break outercolorloop;
							}
						}else {
						}
					}

				outersizeloop:
					for (int i=0; i<=sizeSwatch.size(); i++) {
						String sSize = sizeSwatch.get(i).getAttribute("aria-label");
						sSize = sSize.split("Size")[1].trim();
						if(sSize.equalsIgnoreCase(size.trim())){
							if(sizeSwatch.get(i).isSelected()) {
								break outersizeloop;	
							}else {
								clickOnElement(sizeSwatch.get(i), "Product's Color");
								break outersizeloop;
							}
						}else {
						}
					}
					clickOnElementFromList(addToBagButton, "Add To Bag Btn");
					clickInsideModalFromList(addToBagModal, Constants.AVGTIMEOUT, "Add To Bag Modal");
					waitForMouseHoverActionFromList(addToBagButton, 5, "Add To Bag Button");
					//clickInsideModalFromList(checkoutCloseIcon, Constants.AVGTIMEOUT, "Checkout Header Fly");
			}

		}catch(Exception e) {
			hardAssertionFail("Exception occured during adding multiple products from Search with following message", e);
		}
		return (PDPPage) openPage(PDPPage.class);
	}

	public static String returnBrandName(String siteName) {
		switch(siteName.toUpperCase()) {
		case "SITE1":
			siteName="AT";
			break;
		case "SITE2":
			siteName="Loft";
			break;
		case "SITE3":
			siteName="L&G";
			break;
		case "SITE4":
			siteName="Factory";
			break;
		case "SITE5":
			siteName="Outlet";
			break;
		}		
		return siteName;
	}
	
	public static String returnExcelWBookName(String environment,String siteName) {
		siteName = returnBrandName(siteName);
		String sheetName = environment+"_"+siteName+"_ProductInfo";
		return sheetName;
	}
	
	@Override
	protected ExpectedCondition<?> getPageLoadCondition() {
		return ExpectedConditions.visibilityOfAllElements(pageLoadCondition);
	}

}