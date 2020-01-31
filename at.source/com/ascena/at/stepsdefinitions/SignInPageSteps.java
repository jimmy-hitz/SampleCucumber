package com.ascena.at.stepsdefinitions;

import com.ascena.at.page.objects.PageObjectsInterface;

import cucumber.api.java.en.Given;

/**
 * Step Definition implementation class for Cucumber Steps defined in Feature file
 */

public class SignInPageSteps implements PageObjectsInterface {

	@Given("^User Sign into account using EmailID as (.+) & Password as (.+)$")
	public void user_Sign_into_account_using_EmailID_as_Password_as(String emailId, String password) {
		signInPage.ecommAccountSignIn(emailId, password);
		myAccountPage.ecommAccountCleanUp();
	}
}