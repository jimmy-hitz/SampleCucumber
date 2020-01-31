package com.ascena.at.page.objects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.ascena.at.locators.ShoppingBagLocators;
import com.ascena.automation.utilities.BasePage;
import com.ascena.automation.utilities.Constants;
import com.ascena.automation.utilities.DriverManager;
import com.ascena.automation.utilities.Utility;

/**
 * Step Action implementation class for Cucumber Steps defined in Feature file
 */

public class ShoppingBagPage extends BasePage {
	
	public ShoppingBagPage() {
		PageFactory.initElements(DriverManager.getDriver(), this);
	}

	@FindAll({	
		@FindBy(css = ShoppingBagLocators.cartOrderTotal),
		@FindBy(css = ShoppingBagLocators.annCartOrderTotal),
	}) public List<WebElement> pageLoadCondition;
	
	@FindAll({	
		@FindBy(css = ShoppingBagLocators.proceedToCheckout),
		@FindBy(css = ShoppingBagLocators.annProceedToCheckout),
	}) public List<WebElement> proceedToCheckout;
	
	@FindAll({	
		@FindBy(css = ShoppingBagLocators.guestCheckoutEmailID),
		@FindBy(css = ShoppingBagLocators.annGuestCheckoutEmailID),
	}) public List<WebElement> guestCheckoutEmailID;
	
	@FindAll({	
		@FindBy(css = ShoppingBagLocators.checkoutSubmitButton),
		@FindBy(css = ShoppingBagLocators.annCheckoutSubmitButton),
	}) public List<WebElement> checkoutSubmitButton;
	
	@FindAll({	
		@FindBy(css = ShoppingBagLocators.h1InsideCheckoutModal),
		@FindBy(css = ShoppingBagLocators.annH1InsideCheckoutModal),
	}) public List<WebElement> h1InsideCheckoutModal;
	
	@FindAll({	
		@FindBy(css = ShoppingBagLocators.regUserEmailID),
		@FindBy(css = ShoppingBagLocators.annRegUserEmailID),
	}) public List<WebElement> regUserEmailID;
	
	@FindAll({	
		@FindBy(css = ShoppingBagLocators.regUserPwd),
		@FindBy(css = ShoppingBagLocators.annRegUserPwd),
	}) public List<WebElement> regUserPwd;

	@FindAll({	
		@FindBy(css = ShoppingBagLocators.regUserSignButton),
		@FindBy(css = ShoppingBagLocators.annRegUserSignButton),
	}) public List<WebElement> regUserSignButton;
	
	public synchronized ShippingPage ecommGuestProceedToCheckout(String checkoutUser, String userEmailID) {
		try {
			scrollIntoViewElementFromList(proceedToCheckout, Constants.SHORTTIMEOUT);
			clickOnElementFromList(proceedToCheckout,"Proceed To Checkout");
			waitForModalLoadingFromList(h1InsideCheckoutModal, Constants.AVGTIMEOUT, "Checkout Modal");
			typeElementValueFromList(guestCheckoutEmailID, Utility.getDataAsString(Utility.sJSONReader("ShipAddWBookName"), Utility.sJSONReader("ShipAddTData"), userEmailID, Utility.sJSONReader("ShipEmailID")),"Checkout - Guest Email ID");
			clickOnElementFromList(checkoutSubmitButton,"Checkout Submit Button");
		}catch(Exception e) {
			hardAssertionFail("Exception occured while continuing as guest user with following message", e);
		}
		return (ShippingPage) openPage(ShippingPage.class);
	}
	
	public synchronized ShippingPage ecommGuestProceedToCheckoutNewLayout(String userEmailID) {
		try {
			scrollIntoViewElementFromList(proceedToCheckout, Constants.SHORTTIMEOUT);
			clickOnElementFromList(proceedToCheckout,"Proceed To Checkout");
			waitForModalLoadingFromList(h1InsideCheckoutModal, Constants.AVGTIMEOUT, "Checkout Modal");
			typeElementValueFromList(guestCheckoutEmailID, Utility.getDataAsString(Utility.sJSONReader("ShipAddWBookName"), Utility.sJSONReader("ShipAddTData"), userEmailID, Utility.sJSONReader("ShipEmailID")),"Checkout - Guest Email ID");
			clickOnElementFromList(checkoutSubmitButton,"Checkout Submit Button");
		}catch(Exception e) {
			hardAssertionFail("Exception occured while continuing as guest user with following message", e);
		}
		return (ShippingPage) openPage(ShippingPage.class);
	}

	public synchronized ShippingPage ecommRegisteredUserProceedToCheckout() {
		try {
			scrollIntoViewElementFromList(proceedToCheckout, Constants.SHORTTIMEOUT);
			clickOnElementFromList(proceedToCheckout,"Proceed To Checkout");
		}catch(Exception e) {
			hardAssertionFail("Exception occured while continuing as reg user with following message", e);
		}
		return (ShippingPage) openPage(ShippingPage.class);
	}

	@Override
	protected ExpectedCondition getPageLoadCondition() {
		return ExpectedConditions.visibilityOfAllElements(pageLoadCondition);
	}
}