package com.ascena.at.stepsdefinitions;

import com.ascena.at.page.objects.PageObjectsInterface;

import cucumber.api.java.en.When;

/**
 * Step Definition implementation class for Cucumber Steps defined in Feature file
 */

public class MegaMenuSteps implements PageObjectsInterface{

	@When("^Clicking on Super & Sub Category shall take user to PLP Page$")
	public void clicking_on_Super_Sub_Category_shall_take_user_to_PLP_Page(){
		homePage.ecommPLPNavigation();
	}
}