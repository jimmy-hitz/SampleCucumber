package com.ascena.at.stepsdefinitions;

import com.ascena.at.page.objects.PageObjectsInterface;

import cucumber.api.java.en.Then;

/**
 * Step Definition implementation class for Cucumber Steps defined in Feature file
 */

public class OrderConfirmationPageSteps<T> implements PageObjectsInterface {

	@Then("^Verify order gets placed successfully and capture the Order ID in excel as CellNo as (.+)$")
	public void verify_order_gets_placed_successfully_and_capture_the_Order_ID_in_excel_as_CellNo_as_TestData(String cellRowNo) {
		orderConfirmationPage.verifyOrderPlacement(cellRowNo);
	}
	
}