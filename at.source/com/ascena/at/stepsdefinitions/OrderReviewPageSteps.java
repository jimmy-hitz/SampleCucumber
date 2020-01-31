package com.ascena.at.stepsdefinitions;

import com.ascena.at.page.objects.PageObjectsInterface;

import cucumber.api.java.en.Then;

/**
 * Step Definition implementation class for Cucumber Steps defined in Feature file
 */

public class OrderReviewPageSteps implements PageObjectsInterface {

	
	@Then("^Submitting CVV as (.+) shall lead in successful order placement$")
	public void submitting_CVV_as_shall_lead_in_successful_order_placement(String sCVVNo) throws Throwable {
		orderReviewPage.ecommRegisteredOrderPlacement(sCVVNo);
	}
	
	@Then("^Verify Order gets placed successfully$")
	public void verify_Order_gets_placed_successfully() {
		orderReviewPage.ecommOrderPlacement();
	}
	
	@Then("^Click on the place order button$")
	public void click_on_the_place_order_button() {
		orderReviewPage.ecommOrderPlacementNewLayout();
	}
	
	@Then("^Verify order should not get placed and error should be captured in excel as CellNo as (.+)$")
	public void verify_order_should_not_get_placed_and_error_should_be_captured_in_excel_as_CellNo_as_TestData(String cellRowNo) {
		orderReviewPage.verifyErrorMessage(cellRowNo);
	}
	
}