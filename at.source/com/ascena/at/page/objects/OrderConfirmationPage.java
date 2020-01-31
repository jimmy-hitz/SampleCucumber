package com.ascena.at.page.objects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.ascena.at.locators.OrderConfirmationPageLocators;
import com.ascena.automation.utilities.BasePage;
import com.ascena.automation.utilities.DriverManager;
import com.ascena.automation.utilities.Utility;

/**
 * Step Action implementation class for Cucumber Steps defined in Feature file
 */

public class OrderConfirmationPage<T> extends BasePage {
	
	public OrderConfirmationPage() {
		PageFactory.initElements(DriverManager.getDriver(), this);
	}
	
	@FindAll({	
		@FindBy(css = OrderConfirmationPageLocators.orderConfirmationHeading),
	}) public List<WebElement> pageLoadCondition;
	
	@FindAll({	
		@FindBy(css = OrderConfirmationPageLocators.orderIDRef),
	}) public List<WebElement> orderIDRef;
	
	@Override
	protected ExpectedCondition<?> getPageLoadCondition() {
		return ExpectedConditions.visibilityOfAllElements(pageLoadCondition);
	}
	
	public synchronized void verifyOrderPlacement(String cellRowNo) {
		String orderID=null;
		for(int i=0;i<orderIDRef.size();i++) {
			orderID = orderIDRef.get(i).getText();
			if(orderID!=null) {
				Utility.passOnTestDataToExcel(cellRowNo, 6, orderID);
				break;
			}
		}
	}
}