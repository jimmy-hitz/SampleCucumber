package com.ascena.at.stepsdefinitions;

import com.ascena.at.page.objects.PageObjectsInterface;

import cucumber.api.java.en.When;

/**
 * Step Definition implementation class for Cucumber Steps defined in Feature file
 */

public class ShippingPageSteps implements PageObjectsInterface {

	
	@When("^Entering FN as (.+) LN as (.+) Add as (.+) ZCode as (.+) PNo as (.+) shall take user to payment page$")
	public void submitting_FN_as_LN_as_Add_as_ZCode_as_PNo_as_shall_take_user_to_payment_page(String sFName, String sLName, String sAddressL1, String sZipCode, String sPNumber) {
		shippingPage.ecommShippingAddSubmission(sFName, sLName, sAddressL1, sZipCode, sPNumber);
	}
	
	@When("^Submitting FN as (.+) LN as (.+) Add as (.+) ZCode as (.+) PNo as (.+) EmailID as (.+) shall take user to payment page$")
	public void submitting_FN_as_TestData_LN_as_TestData_Add_as_TestData_ZCode_as_TestData_PNo_as_TestData_EmailID_as_TestData_shall_take_user_to_payment_page(String sFName, String sLName, String sAddressL1, String sZipCode, String sPNumber, String emailID) {
		shippingPage.ecommShippingAddSubmissionNewLayout(sFName, sLName, sAddressL1, sZipCode, sPNumber, emailID);
	}
}