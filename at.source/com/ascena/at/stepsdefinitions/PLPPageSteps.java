package com.ascena.at.stepsdefinitions;

import com.ascena.at.page.objects.PageObjectsInterface;

import cucumber.api.java.en.When;

/**
 * Step Definition implementation class for Cucumber Steps defined in Feature file
 */

public class PLPPageSteps implements PageObjectsInterface {

	@When("^User wants to explore second product on PLP$")
	public void user_wants_to_explore_second_product_on_PLP() {
		plpPage.ecommProductSelectionOnPLP();
	}
}