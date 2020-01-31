package com.ascena.lb.stepsdefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class HomePageStepDef extends PageObjects {

	@Given("user is on homepage")
	public void user_is_on_homepage() throws Exception {
		homePage.invokeBrowserAndVerifyCountry();
	}

	@Then("user validates successful navigation to home page")
	public void user_validates_successful_navigation_to_home_page() throws Exception {
		homePage.clickOnLogoAndVerifyNavigation();
	}

	@Then("user validates promotions and its redirections")
	public void user_validates_promotions_and_its_redirections() throws Exception {
		homePage.verifyPromotions();
	}

	@When("user enters (.*) into search box and clicks on enter to search")
	public void user_enters_tops_into_search_box_and_clicks_on_enter_to_search(String keyword) {
		homePage.verifySearch(keyword);
	}

	@Then("user verifies L1 categories")
	public void user_verifies_L_categories() throws ClassNotFoundException {
		homePage.verifyL1Category();
	}

	@When("use navigatess to specific (.*) plp page")
	public void use_navigatess_to_specific_TestData_plp_page(String plpName) {
		homePage.navigateToPlp(plpName);
	}
}
