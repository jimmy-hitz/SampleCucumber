package com.ascena.at.page.objects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.ascena.at.locators.OrderReviewPageLocators;
import com.ascena.automation.extent.listeners.ExtentTestManager;
import com.ascena.automation.utilities.BasePage;
import com.ascena.automation.utilities.Constants;
import com.ascena.automation.utilities.DriverManager;
import com.ascena.automation.utilities.Utility;

/**
 * Step Action implementation class for Cucumber Steps defined in Feature file
 */

public class OrderReviewPage extends BasePage {
	
	public OrderReviewPage() {
		PageFactory.initElements(DriverManager.getDriver(), this);
	}

	@FindAll({	
		@FindBy(xpath = OrderReviewPageLocators.orderReviePageHeading),
		@FindBy(xpath = OrderReviewPageLocators.annOrderReviePageHeading),
	}) public List<WebElement> pageLoadCondition;

	@FindAll({	
		@FindBy(css = OrderReviewPageLocators.checkoutPlaceOrderButton),
		@FindBy(css = OrderReviewPageLocators.annCheckoutPlaceOrderButton),
	}) public List<WebElement> checkoutPlaceOrderButton;

	@FindAll({	
		@FindBy(css = OrderReviewPageLocators.billingCVVNo),
		@FindBy(css = OrderReviewPageLocators.annBillingCVVNo),
	}) public List<WebElement> billingCVVNo;
	
	@FindAll({	
		@FindBy(css = OrderReviewPageLocators.errorMessageCheckoutAnntaylor),
		@FindBy(css = OrderReviewPageLocators.errorMessageCheckoutFactory),
	}) public List<WebElement> errorMessageText;

	public synchronized OrderConfirmationPage ecommRegisteredOrderPlacement(String sCVV) {
		try {		
			String cardCVVNo = Utility.getDataAsString(Utility.sJSONReader("BillingWBookName"), Utility.sJSONReader("BillingTData"), sCVV, Utility.sJSONReader("BillingCVV"));
			if(!(cardCVVNo.equalsIgnoreCase("NA"))) {
				scrollIntoViewElementFromList(billingCVVNo, Constants.SHORTTIMEOUT);
				typeElementValueFromList(billingCVVNo, cardCVVNo,"CVV No");
			}
		}catch(Exception e) {	
			hardAssertionFail("Exception occured during the Reg User CVV processing with following message", e);
		}
		return (OrderConfirmationPage) openPage(OrderConfirmationPage.class);
	}
	
	public synchronized OrderConfirmationPage ecommOrderPlacementNewLayout() {
		try {
			scrollIntoViewElementFromList(checkoutPlaceOrderButton, Constants.SHORTTIMEOUT);
			clickOnElementFromList(checkoutPlaceOrderButton, "Place Order");
		}catch(Exception e) {	
			hardAssertionFail("Exception occured during the Reg User CVV processing with following message", e);
		}
		return (OrderConfirmationPage) openPage(OrderConfirmationPage.class);
	}

	public synchronized OrderReviewPage ecommOrderPlacement() {
		try {
			clickOnElementFromList(checkoutPlaceOrderButton, "Place Order");
		}catch(Exception e) {
			hardAssertionFail("Exception occured while clicking on place Order button with following message", e);
		}
		return (OrderReviewPage) openPage(OrderReviewPage.class);
	}
	
	public synchronized void verifyErrorMessage(String cellRowNo) {
		try {
			scrollIntoViewElementFromList(checkoutPlaceOrderButton, Constants.SHORTTIMEOUT);
			clickOnElementFromList(checkoutPlaceOrderButton, "Place Order");

			Thread.sleep(2000);
			String getErrorMessage=null;
			for(int i=0; i<errorMessageText.size();i++) {
				getErrorMessage = errorMessageText.get(i).getText();
				if(getErrorMessage!=null) {
					Utility.passOnTestDataToExcel(cellRowNo, 7, getErrorMessage);
					System.out.println(getErrorMessage);
					break;
				}
			}
			ExtentTestManager.addScreenShotsOnBAMSErrorMessage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected ExpectedCondition getPageLoadCondition() {
		return ExpectedConditions.visibilityOfAllElements(pageLoadCondition);
	}
}