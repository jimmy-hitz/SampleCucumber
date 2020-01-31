package com.ascena.at.page.objects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.ascena.at.locators.PaymentPageLocators;
import com.ascena.automation.utilities.BasePage;
import com.ascena.automation.utilities.Constants;
import com.ascena.automation.utilities.DriverManager;
import com.ascena.automation.utilities.Utility;

/**
 * Step Action implementation class for Cucumber Steps defined in Feature file
 */

public class PaymentPage extends BasePage {
	
	public PaymentPage() {
		PageFactory.initElements(DriverManager.getDriver(), this);
	}

	@FindBy(name = PaymentPageLocators.paymentIFrameName) public WebElement paymentIFrameName;
	@FindAll({	
		@FindBy(xpath = PaymentPageLocators.billingPageHeading),
		@FindBy(css = PaymentPageLocators.annBillingPageHeading),
	}) public List<WebElement> pageLoadCondition;
	
	@FindAll({	
		@FindBy(css = PaymentPageLocators.billingCreditCard),
		@FindBy(css = PaymentPageLocators.annBillingCreditCard),
	}) public List<WebElement> billingCreditCard;

	@FindAll({	
		@FindBy(css = PaymentPageLocators.billingCCMonth),
		@FindBy(css = PaymentPageLocators.annBillingCCMonth),
	}) public List<WebElement> billingCCMonth;
	
	@FindAll({	
		@FindBy(css = PaymentPageLocators.billingCCYear),
		@FindBy(css = PaymentPageLocators.annBillingCCYear),
	}) public List<WebElement> billingCCYear;
	
	@FindAll({	
		@FindBy(css = PaymentPageLocators.billingCVV),
		@FindBy(css = PaymentPageLocators.annBillingCVV),
	}) public List<WebElement> billingCVV;
	
	@FindAll({	
		@FindBy(css = PaymentPageLocators.billingReviewOrderButton),
		@FindBy(css = PaymentPageLocators.annBillingReviewOrderButton),
	}) public List<WebElement> billingReviewOrderButton;
	
	
	public synchronized OrderReviewPage ecommGuestPaymentSubmission(String sCardNo, String sCardMonth, String sCardYear, String sCVV) {
		try {
			swtichToIframe(driver, paymentIFrameName);
			scrollIntoViewElementFromList(billingCreditCard, Constants.SHORTTIMEOUT);
			String cCardNo = Utility.getDataAsString(Utility.sJSONReader("BillingWBookName"), Utility.sJSONReader("BillingTData"), sCardNo, Utility.sJSONReader("BillingCCNo"));
			typeElementValueFromList(billingCreditCard, cCardNo,"Card No");
			String cardMonth = Utility.getDataAsString(Utility.sJSONReader("BillingWBookName"), Utility.sJSONReader("BillingTData"), sCardMonth, Utility.sJSONReader("BillingCCMonth"));
			if(!(cardMonth.equalsIgnoreCase("NA"))) {
				selectElementFromList(billingCCMonth, cardMonth, "Card Month");	
			}
			String cardYear = Utility.getDataAsString(Utility.sJSONReader("BillingWBookName"), Utility.sJSONReader("BillingTData"), sCardYear, Utility.sJSONReader("BillingCCYear"));
			if(!(cardYear.equalsIgnoreCase("NA"))) {
				selectElementFromList(billingCCYear, cardYear, "Card Year");
			}

			String cardCVVNo = Utility.getDataAsString(Utility.sJSONReader("BillingWBookName"), Utility.sJSONReader("BillingTData"), sCVV, Utility.sJSONReader("BillingCVV"));
			if(!(cardCVVNo.equalsIgnoreCase("NA"))) {
				typeElementValueFromList(billingCVV, cardCVVNo,"CVV No");
			}
			scrollIntoViewElementFromList(billingReviewOrderButton, Constants.SHORTTIMEOUT);
			clickOnElementFromList(billingReviewOrderButton,"Review Your Order Button");
			switchToDefaultContent(driver);
		}catch(Exception e) {	
			hardAssertionFail("Exception occured while submitting guest userpayment details with following message", e);
		}
		return (OrderReviewPage) openPage(OrderReviewPage.class);
	}

	public synchronized OrderReviewPage ecommRegisteredPaymentSubmission(String sCardNo, String sCardMonth, String sCardYear) {
		try {
			swtichToIframe(driver, paymentIFrameName);
			scrollIntoViewElementFromList(billingCreditCard, Constants.SHORTTIMEOUT);
			typeElementValueFromList(billingCreditCard, Utility.getDataAsString(Utility.sJSONReader("BillingWBookName"), Utility.sJSONReader("BillingTData"), sCardNo, Utility.sJSONReader("BillingCCNo")),"Card No");
			String cardMonth = Utility.getDataAsString(Utility.sJSONReader("BillingWBookName"), Utility.sJSONReader("BillingTData"), sCardMonth, Utility.sJSONReader("BillingCCMonth"));
			if(!(cardMonth.equalsIgnoreCase("NA"))) {
				selectElementFromList(billingCCMonth, cardMonth, "Card Month");	
			}
			String cardYear = Utility.getDataAsString(Utility.sJSONReader("BillingWBookName"), Utility.sJSONReader("BillingTData"), sCardYear, Utility.sJSONReader("BillingCCYear"));
			if(!(cardYear.equalsIgnoreCase("NA"))) {
				selectElementFromList(billingCCYear, cardYear, "Card Year");
			}

			scrollIntoViewElementFromList(billingReviewOrderButton, Constants.SHORTTIMEOUT);
			clickOnElementFromList(billingReviewOrderButton,"Review Your Order Button");
			switchToDefaultContent(driver);
		}catch(Exception e) {	
			hardAssertionFail("Exception occured while submitting reg userpayment details with following message", e);
		}
		return (OrderReviewPage) openPage(OrderReviewPage.class);
	}

	@Override
	protected ExpectedCondition getPageLoadCondition() {
		return ExpectedConditions.visibilityOfAllElements(pageLoadCondition);
	}
}