package com.ascena.at.page.objects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.ascena.at.locators.PLPLocators;
import com.ascena.automation.utilities.BasePage;
import com.ascena.automation.utilities.Constants;
import com.ascena.automation.utilities.DriverManager;

/**
 * Step Action implementation class for Cucumber Steps defined in Feature file
 */

public class PLPPage extends BasePage {
	
	public PLPPage() {
		PageFactory.initElements(DriverManager.getDriver(), this);
	}

	@FindAll({	
		@FindBy(css = PLPLocators.filterByLabel),
		@FindBy(xpath = PLPLocators.anntaylorSortBy),
	}) public List<WebElement> pageLoadCondition;
	
	@FindAll({	
		@FindBy(css = PLPLocators.productsList),
		@FindBy(css = PLPLocators.anntaylorPLPProducts),
	}) public List<WebElement> productsList;
	
	@FindBy(xpath = PLPLocators.totalItemCountLabel) public WebElement totalItemCountLabel;

	public PDPPage ecommProductSelectionOnPLP() {
		try {
			waitForElementToBeClickableFromList(productsList, Constants.AVGTIMEOUT, "PLP Product");
			clickOnElementFromList(productsList, "First Product on PLP Page");
		}catch(Exception e) {
			hardAssertionFail("Exception occured while moving to PDPD with following message", e);
		}
		return (PDPPage) openPage(PDPPage.class);
	}

	@Override
	protected ExpectedCondition getPageLoadCondition() {
		return ExpectedConditions.visibilityOfAllElements(pageLoadCondition);
	}
}