package com.ascena.at.page.objects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.ascena.at.locators.PDPLocators;
import com.ascena.at.locators.SignInPageLocators;
import com.ascena.automation.utilities.BasePage;
import com.ascena.automation.utilities.DriverManager;
import com.ascena.automation.utilities.Utility;

/**
 * Step Action implementation class for Cucumber Steps defined in Feature file
 */

public class SignInPage extends BasePage {
	
	/**
	 * 
	 */
	public SignInPage() {
		PageFactory.initElements(DriverManager.getDriver(), this);
	}

	@FindAll({	
		@FindBy(css = PDPLocators.factoryFlyMiniCheckoutHeader),
		@FindBy(css = PDPLocators.outletFlyMiniCheckoutHeader),
	}) public List<WebElement> flyCheckoutHeaderList;
	
	@FindAll({	
		@FindBy(css = SignInPageLocators.signInEmailID),
		@FindBy(css = SignInPageLocators.annSignInEmailID),
	}) public List<WebElement> pageLoadCondition;
	
	@FindAll({	
		@FindBy(css = SignInPageLocators.signInPwd),
		@FindBy(css = SignInPageLocators.annSignInPwd),
	}) public List<WebElement> signInPwd;
	
	@FindAll({	
		@FindBy(css = SignInPageLocators.logInButton),
		@FindBy(css = SignInPageLocators.annLogInButton),
	}) public List<WebElement> logInButton;
	
	public MyAccountPage ecommAccountSignIn(String emailId, String password) {
		try {
			typeElementValueFromList(pageLoadCondition, Utility.getDataAsString(Utility.sJSONReader("ShipAddWBookName"), Utility.sJSONReader("ShipAddTData"), emailId, Utility.sJSONReader("ShipEmailID")), "Account Email ID");
			typeElementValueFromList(signInPwd, Utility.getDataAsString(Utility.sJSONReader("ShipAddWBookName"), Utility.sJSONReader("ShipAddTData"), password, Utility.sJSONReader("ShipUserPwd")), "Account Password");
			clickOnElementFromList(logInButton, "Log In Button");
		}catch(Exception e) {
			hardAssertionFail("Exception occured while logging into account as reg user with following message", e);
		}
		return (MyAccountPage) openPage(MyAccountPage.class);
	}
	
	@Override
	protected ExpectedCondition getPageLoadCondition() {
		return ExpectedConditions.visibilityOfAllElements(pageLoadCondition);
	}
}