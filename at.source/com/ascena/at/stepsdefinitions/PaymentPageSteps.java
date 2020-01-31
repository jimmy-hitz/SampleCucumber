package com.ascena.at.stepsdefinitions;

import com.ascena.at.page.objects.PageObjectsInterface;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Step Definition implementation class for Cucumber Steps defined in Feature file
 */

public class PaymentPageSteps implements PageObjectsInterface {

	@Then("^Submitting CCardNo as (.+) Month as (.+) and Year as (.+) shall take user to Order Review Page$")
	public void submitting_CCardNo_as_Month_as_and_Year_as_shall_take_user_to_Order_Review_Page(String sCardNo, String sCardMonth, String sCardYear) {
		paymentPage.ecommRegisteredPaymentSubmission(sCardNo, sCardMonth, sCardYear);
	}

	@When("^Submitting CCardNo as (.+) Month as (.+) Year as (.+) and CVV as (.+) shall take user to Order Review Page$")
	public void submitting_CCardNo_as_Month_as_Year_as_and_CVV_as_shall_take_user_to_Order_Review_Page(String sCardNo, String sCardMonth, String sCardYear, String sCVV) throws Throwable {
		paymentPage.ecommGuestPaymentSubmission(sCardNo, sCardMonth, sCardYear, sCVV);
	}
}