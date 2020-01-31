package com.ascena.at.stepsdefinitions;

import com.ascena.at.page.objects.PageObjectsInterface;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;

/**
 * Step Definition implementation class for Cucumber Steps defined in Feature file
 */

public class HomePageSteps implements PageObjectsInterface{

	@When("^User is on Brand Home Page (.+)$")
	public void user_is_on_Brand_Home_Page(String siteName) throws InterruptedException {
		homePage.launchBrandSite(siteName);
		homePage.ecommCountrySelection();
	}

	@Then("^Clicking on Sign In link shall take user to Sign In Page$")
	public void clicking_on_Sign_In_link_shall_take_user_to_Sign_In_Page() {
		homePage.ecommSignInPageNavigation();
	}

	@Given("^User searches for a styleId for (.+) and makes product selection on the basis of given color and size$")
	public void user_searches_for_a_styleid_for_and_makes_product_selection_on_the_basis_of_given_color_and_size(String siteName, DataTable table) {
		homePage.ecommAddMultipleProducts(siteName,table);
	}
}