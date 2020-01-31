package com.ascena.at.page.objects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;

import com.ascena.at.locators.HomePageLocators;
import com.ascena.automation.utilities.BasePage;
import com.ascena.automation.utilities.Constants;
import com.ascena.automation.utilities.DriverManager;

/**
 * Step Action implementation class for Cucumber Steps defined in Feature file
 * @param <T>
 */

public class MegaMenu<T> extends BasePage {
	
	public MegaMenu() {
		PageFactory.initElements(DriverManager.getDriver(), this);
	}

	@FindAll({	
		@FindBy(css = HomePageLocators.megaMenu),
		@FindBy(css = HomePageLocators.anntaylorImage),
	}) public List<WebElement> megaMenu;
	
	@FindAll({	
		@FindBy(xpath = HomePageLocators.annMegaMenuSupCat),
		@FindBy(xpath = HomePageLocators.megaMenuSupCat),
	}) public List<WebElement> megaMenuSupCat;

	@FindAll({	
		@FindBy(css = HomePageLocators.annMegaMenuSubCat),
		@FindBy(xpath = HomePageLocators.megaMenuSubCat),
	}) public List<WebElement> megaMenuSubCat;
	
	public synchronized void navigateToPLPFromMegaMenu() {	
		try{
			waitForMouseHoverActionFromList(megaMenuSupCat, Constants.SHORTTIMEOUT, "Category : Clothing");
			clickWebElementJavaScriptExecutorFromList(megaMenuSubCat, "Sub Category -> Sweaters");
			waitForPageLoaded();
		}catch(Exception e) {
			hardAssertionFail("Exception occured during the PLP Navigation with following message", e);
		}
	}

	@Override
	protected ExpectedCondition<?> getPageLoadCondition() {
		return null;
	}
}