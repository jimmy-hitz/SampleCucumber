package com.ascena.at.stepsdefinitions;

import com.ascena.at.page.objects.PageObjectsInterface;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Step Definition implementation class for Cucumber Steps defined in Feature file
 */

public class ShoppingBagSteps implements PageObjectsInterface {

	@When("^Proceeding to checkout as (.+) with emailId (.+) shall take user to Shipping Page$")
	public void proceeding_to_checkout_as_with_emailId_shall_take_user_to_Shipping_Page(String checkoutUser, String userEmailID) {
		shoppingBagPage.ecommGuestProceedToCheckout(checkoutUser, userEmailID);
	}
	
	@Then("Clicking on Proceed To Checkout button shall take user to Shipping Page")
	public void clicking_on_Proceed_To_Checkout_button_shall_take_user_to_Shipping_Page() {
		shoppingBagPage.ecommRegisteredUserProceedToCheckout();
	}
	
	@Then("^User clicks on Proceed To Checkout Button$")
	public void user_clicks_on_Proceed_To_Checkout_Button() {
		shoppingBagPage.ecommRegisteredUserProceedToCheckout();
	}
}