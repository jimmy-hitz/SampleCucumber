package com.ascena.at.stepsdefinitions;

import com.ascena.at.page.objects.PageObjectsInterface;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Step Definition implementation class for Cucumber Steps defined in Feature file
 */

public class PDPPageSteps implements PageObjectsInterface {

	@When("^User makes product selection on PDP$")
	public void user_makes_product_selection_on_PDP() {
		pdpPage.ecommSKUSelectionOnPDP();	
	}
		
	@Then("^Clicking on Cart icon shall take user to Shopping Bag$")
	public void clicking_on_Cart_icon_shall_take_user_to_Shopping_Bag() throws Throwable {
		pdpPage.ecommShoppingCartNavigation();
	}
}