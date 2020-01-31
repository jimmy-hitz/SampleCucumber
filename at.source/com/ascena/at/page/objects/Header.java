package com.ascena.at.page.objects;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;

import com.ascena.automation.utilities.BasePage;
import com.ascena.automation.utilities.DriverManager;

/**
 * Step Action implementation class for Cucumber Steps defined in Feature file
 */

public class Header extends BasePage {
	
	public Header() {
		PageFactory.initElements(DriverManager.getDriver(), this);
	}

	@Override
	protected ExpectedCondition<?> getPageLoadCondition() {
		return null;
	}
}