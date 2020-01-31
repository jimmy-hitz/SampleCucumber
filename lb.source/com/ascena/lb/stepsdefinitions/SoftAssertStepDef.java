package com.ascena.lb.stepsdefinitions;

import com.ascena.automation.utilities.BasePage2;

import cucumber.api.java.en.Then;

public class SoftAssertStepDef {

	@Then("user writes the results")
	public void user_writes_the_results() throws Exception {
		BasePage2.softAssert.assertAll();
	}

}
