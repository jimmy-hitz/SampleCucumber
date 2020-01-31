package com.ascena.at.page.objects;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.ascena.at.locators.PDPLocators;
import com.ascena.automation.utilities.BasePage;
import com.ascena.automation.utilities.Constants;
import com.ascena.automation.utilities.DriverManager;
import com.ascena.automation.utilities.Utility;

import io.cucumber.datatable.DataTable;

/**
 * Step Action implementation class for Cucumber Steps defined in Feature file
 */

public class PDPPage extends BasePage {
	
	public PDPPage() {
		PageFactory.initElements(DriverManager.getDriver(), this);
	}

	@FindAll({	
		@FindBy(css = PDPLocators.addToBagButton),
		@FindBy(css = PDPLocators.anntaylorAddToBagButton),
	}) public List<WebElement> pageLoadCondition;

	@FindAll({	
		@FindBy(css = PDPLocators.factoryFlyMiniCheckoutHeader),
		@FindBy(css = PDPLocators.outletFlyMiniCheckoutHeader),
	}) public List<WebElement> flyCheckoutHeaderList;

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
		@FindBy(xpath = PDPLocators.addToBagModalConShopButton),
		@FindBy(xpath = PDPLocators.anntaylorAddToBagModalConShopButton),
	}) public List<WebElement> addToBagModalContinueShoppingButton;

	@FindAll({	
		@FindBy(xpath = PDPLocators.shoppingBagIcon),
		@FindBy(css = PDPLocators.anntaylorShoppingBagIcon),
	}) public List<WebElement> shoppingBagIcon;

	public synchronized ShoppingBagPage ecommSKUSelectionOnPDP() {
		try {
			waitForMouseHoverActionFromList(pageLoadCondition, Constants.SHORTTIMEOUT, "Add To Bag Button");
			waitForVisibilityOfElementsLocated(colorSwatch, 2, "PDP SKU");
			outerloop:
				for (int i=0; i<=colorSwatch.size(); i++) {
					if(colorSwatch.size()==1 && colorSwatch.get(i).isEnabled()) {
						for (int j=0; j<=sizeSwatch.size(); j++) {
							if(sizeSwatch.size()==1 && sizeSwatch.get(j).isEnabled()) {
								break outerloop;
							}else if (sizeSwatch.size()>1 && sizeSwatch.get(j).isEnabled()) {
								clickOnElement(sizeSwatch.get(j), "Product's Color & Size");
								break outerloop;
							}
						}
					}else if (colorSwatch.size()>1 && colorSwatch.get(i).isEnabled()) {
						for (int j=0; j<=sizeSwatch.size(); j++) {
							if(sizeSwatch.size()==1 && sizeSwatch.get(j).isEnabled()) {
								break outerloop;
							}else if (sizeSwatch.size()>1 && sizeSwatch.get(j).isEnabled()) {
								clickOnElement(sizeSwatch.get(j), "Product's Color & Size");
								break outerloop;
							}
						}
					}
				}
			clickOnElementFromList(pageLoadCondition, "Add To Bag Btn");
			Thread.sleep(2000);
			clickInsideModalFromList(addToBagModalContinueShoppingButton, Constants.AVGTIMEOUT, "Continue to Checkout Button");
			//clickInsideModalFromList(addToBagModal, Constants.AVGTIMEOUT, "Add To Bag Modal");
			//clickOnElementInOptionalModal(flyCheckoutHeaderList, Constants.AVGTIMEOUT, "Checkout Header Fly");
		}catch(Exception e) {
			hardAssertionFail("Exception occured during product selection with following message", e);
		}
		return (ShoppingBagPage) openPage(ShoppingBagPage.class);
	}

	public synchronized ShoppingBagPage ecommShoppingCartNavigation() {
		try {
			Thread.sleep(2000);
			clickOnElementFromList(shoppingBagIcon, "Shopping Bag Icon");
		}catch(Exception e) {
			hardAssertionFail("Exception occured while moving to cart with following message", e);
		}
		return (ShoppingBagPage) openPage(ShoppingBagPage.class);
	}	

	public synchronized void closeAddToBagModal() {	
		boolean addToBagOverlay=false;
		long starttime = System.currentTimeMillis();
		long endtime = starttime + Long.parseLong(Utility.sJSONReader("addToBagOverlay"));
		outerloop:
			while(System.currentTimeMillis() < endtime)
			{		
				try{
					int elementCount = addToBagModal.size();
						for(int i=0; i<elementCount; i++) {
							try {
								if(elementCount==0) {
									addToBagOverlay = addToBagModal.get(i).isDisplayed();
									if (addToBagOverlay== true) {
										clickOnElementFromList(addToBagModal, "Add To Bag Modal Close Button");
										break outerloop;
									}else if(elementCount>0) {
										addToBagOverlay = addToBagModal.get(i).isDisplayed();
										if (addToBagOverlay== true) {
											clickOnElementFromList(addToBagModal, "Add To Bag Modal Close Button");
											break outerloop;
										}
									}
								}
							}catch(Exception e) {
							}
						}
				}catch(Exception e) {
					hardAssertionFail("Exception occured while closing Add To Bag Modal with following message", e);
				}
			}
	}

	public synchronized void ecommSKUSelectionOnPDPExcel(DataTable table) {
		String color = null, size = null;
		try {
			List<Map<String, String>> data = table.asMaps(String.class,String.class);

			for(Map<String, String> list : data) {
				System.out.println("Size of list is : "+data.size());
				color = Utility.getDataAsString("ProductInfo", "TestData", list.get("Product_Color"), "Color");
				size = Utility.getDataAsString("ProductInfo", "TestData", list.get("Product_Size"), "Size");

				waitForMouseHoverActionFromList(pageLoadCondition, Constants.SHORTTIMEOUT, "Add To Bag Button");
				waitForVisibilityOfElementsLocated(colorSwatch, 2, "PDP SKU");

				outercolorloop:
					for (int i=0; i<=colorSwatch.size(); i++) {
						String sColor = colorSwatch.get(i).getAttribute("aria-label");
						sColor = sColor.split(":")[1].trim();
						if(sColor.equalsIgnoreCase(color.trim())){
							if(colorSwatch.get(i).isSelected()) {
								System.out.println("Yes color match, expected color was : "+color+" actual color was : "+sColor+" and was already selected");
								break outercolorloop;	
							}else {
								System.out.println("Yes color match, expected color was : "+color+" actual color was : "+sColor+" and was not already selected");
								clickOnElement(colorSwatch.get(i), "Product's Color");
								break outercolorloop;
							}
						}else {
							System.out.println("No color did match, expected color was : "+color+" actual color was : "+sColor);
						}
					}

				outersizeloop:
					for (int i=0; i<=sizeSwatch.size(); i++) {
						String sSize = sizeSwatch.get(i).getAttribute("aria-label");
						sSize = sSize.split("Size")[1].trim();
						if(sSize.equalsIgnoreCase(size.trim())){
							if(sizeSwatch.get(i).isSelected()) {
								System.out.println("Yes color match, expected color was : "+color+" actual color was : "+sSize+" and was already selected");
								break outersizeloop;	
							}else {
								System.out.println("Yes color match, expected color was : "+color+" actual color was : "+sSize+" and was not already selected");
								clickOnElement(sizeSwatch.get(i), "Product's Color");
								break outersizeloop;
							}
						}else {
							System.out.println("No color did match, expected color was : "+color+" actual color was : "+sSize);
						}
					}
					clickOnElementFromList(pageLoadCondition, "Add To Bag Btn");
					clickInsideModalFromList(addToBagModal, Constants.AVGTIMEOUT, "Add To Bag Modal");
					clickOnElementInOptionalModal(flyCheckoutHeaderList, Constants.AVGTIMEOUT, "Checkout Header Fly");
			}

		}catch(Exception e) {
			hardAssertionFail("Exception occured during product selection with following message", e);
		}
	}
	
//	public synchronized void bounceXClientOverlayPDP() {	
//		boolean bounceXDisplay=false;
//		long starttime = System.currentTimeMillis();
//		long endtime = starttime + Long.parseLong(Utility.sJSONReader("bounceXClientOverlayTimeOut"));
//		if (Utility.sJSONReader("bounceXClientOverlayPDP").equalsIgnoreCase("ON")) {
//			while(System.currentTimeMillis() < endtime)
//			{		
//				try{
//					bounceXDisplay = bounceClientOverlay.isDisplayed();
//					if (bounceXDisplay== true) {
//						clickOnElement(bounceClientOverlay, "Bounce X Client Overlay on PDP");
//						break;
//					}
//				}catch(Exception e) {
//					hardAssertionFail("Exception occured while closing PDP Overlay with following message", e);
//				}
//			}
//		}
//		if (bounceXDisplay == false) {
//			addLogError("Client Overlay Bounce x Pop-Up did not appear on site after waiting for "+Long.parseLong(Utility.sJSONReader("bounceXClientOverlayTimeOut"))/1000+" Sec's.");
//		}
//	}

	@Override
	protected ExpectedCondition<?> getPageLoadCondition() {
		return ExpectedConditions.visibilityOfAllElements(pageLoadCondition);
	}
}