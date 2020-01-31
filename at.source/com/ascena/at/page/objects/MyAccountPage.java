package com.ascena.at.page.objects;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import com.ascena.at.locators.MyAccountLocators;
import com.ascena.at.locators.PDPLocators;
import com.ascena.automation.utilities.BasePage;
import com.ascena.automation.utilities.Constants;
import com.ascena.automation.utilities.DriverManager;

/**
 * Step Action implementation class for Cucumber Steps defined in Feature file
 */

public class MyAccountPage<T> extends BasePage {
	
	public MyAccountPage() {
		PageFactory.initElements(DriverManager.getDriver(), this);
	}

	@FindAll({	
		@FindBy(css = PDPLocators.factoryFlyMiniCheckoutHeader),
		@FindBy(css = PDPLocators.outletFlyMiniCheckoutHeader),
	}) public List<WebElement> flyCheckoutHeaderList;

	@FindAll({	
		@FindBy(css = MyAccountLocators.orderSectionHeading),
		@FindBy(css = MyAccountLocators.annOrderSectionHeading),
	}) public List<WebElement> pageLoadCondition;

	@FindAll({	
		@FindBy(css = MyAccountLocators.myAccountUserName),
		@FindBy(css = MyAccountLocators.annMyAccountUserName),
	}) public List<WebElement> myAccountUserName;

	@FindAll({	
		@FindBy(xpath = MyAccountLocators.addressBooklnk),
	}) public List<WebElement> addressBooklnk;

	@FindAll({	
		@FindBy(css = MyAccountLocators.addressBookH1),
		@FindBy(css = MyAccountLocators.annAddressBookH1),
	}) public List<WebElement> addressBookHeading;

	@FindAll({	
		@FindBy(xpath = MyAccountLocators.noSavedAddress),
	}) public List<WebElement> noSavedAddress;

	@FindAll({	
		@FindBy(css = MyAccountLocators.allRemoveBtn),
		@FindBy(css = MyAccountLocators.annallRemoveBtn),
	}) public List<WebElement> allRemoveBtn;

	@FindAll({	
		@FindBy(css = MyAccountLocators.removeBtn),
		@FindBy(xpath = MyAccountLocators.annRemoveBtn),
	}) public List<WebElement> removeBtn;

	@FindAll({	
		@FindBy(xpath = MyAccountLocators.removeConfirmBtn),
		@FindBy(css = MyAccountLocators.annRemoveConfirmBtn),
	}) public List<WebElement> removeConfirmBtn;

	@FindAll({	
		@FindBy(xpath = MyAccountLocators.paymentMethodLink),
	}) public List<WebElement> paymentMethodLink;

	@FindAll({	
		@FindBy(css = MyAccountLocators.paymentMethodHeading),
		@FindBy(css = MyAccountLocators.annPaymentMethodHeading),
	}) public List<WebElement> paymentMethodHeading;

	@FindAll({	
		@FindBy(css = MyAccountLocators.noSavedPayment),
		@FindBy(xpath = MyAccountLocators.annNoSavedPayment),
	}) public List<WebElement> noSavedPayment;

	@FindAll({	
		@FindBy(xpath = PDPLocators.shoppingBagIcon),
		@FindBy(css = PDPLocators.anntaylorShoppingBagIcon),
	}) public List<WebElement> shoppingCartIcon;

	@FindAll({	
		@FindBy(css = MyAccountLocators.shoppingCartRemoveButton),
		@FindBy(xpath = MyAccountLocators.annShoppingCartRemoveButton),
	}) public List<WebElement> shoppingCartRemoveButton;

	@FindAll({	
		@FindBy(css = MyAccountLocators.emptyCart),
		@FindBy(css = MyAccountLocators.annEmptyCart),
	}) public List<WebElement> emptyCart;

	@FindAll({	
		@FindBy(css = MyAccountLocators.miniCartCheckoutButton),
		@FindBy(css = MyAccountLocators.annMiniCartCheckoutButton),
	}) public List<WebElement> miniCartCheckoutButton;

	public synchronized void ecommAccountCleanUp() {
		try {
			accountAddressCleanUp();
			paymentMethodsCleanUp();
			removeCartItems();
		}catch(Exception e) {
			hardAssertionFail("Exception occured during the Account Cleanup with following message", e);
		}
	}

	public synchronized void accountAddressCleanUp() {
		try {
			clickOnElementInOptionalModal(flyCheckoutHeaderList, Constants.SHORTTIMEOUT, "Checkout Header Fly");
			clickOnElementFromList(addressBooklnk,"My Account - Address Book Link");
			while(isElementPresentBy(By.xpath(MyAccountLocators.noSavedAddress))!=true) {
				clickOnElementFromList(removeBtn, "AddressBook - Remove Add");
				waitForElementVisibleAndClickFromList(removeConfirmBtn, Constants.SHORTTIMEOUT, "Address - Remove Comfirm Button");
				invisibilityOfElementLocated(By.xpath(MyAccountLocators.removeConfirmBtn),"Address Remove Modal Invisibility");
			}
		}catch(Exception e) {
			hardAssertionFail("Exception occured during the Address Cleanup with following message", e);
		}
	}

	public synchronized boolean clickOnButtonWhenItsClickable() {
		boolean buttonClicked=false;
		try {
			List<WebElement> element = driver.findElements(By.xpath("//button[@id='btn_ok']"));

			while(element.size()!=0) {
				// if any action needed to perform to display button 
				element = driver.findElements(By.xpath("//button[@id='btn_ok']"));
				if(element.size()!=0) {
					wait = new FluentWait<WebDriver>((WebDriver) driver).withTimeout(10, TimeUnit.SECONDS).pollingEvery(1,
							TimeUnit.SECONDS);
					wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//button[@id='btn_ok']"))));
					buttonClicked=true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buttonClicked;
	}

	public synchronized void paymentMethodsCleanUp() {
		try {
			clickOnElementFromList(paymentMethodLink,"My Account - Payment Link");
			while(isElementPresentBy(By.cssSelector(MyAccountLocators.noSavedPayment))!=true) {	
				clickOnElementFromList(removeBtn, "Payment - Remove Card");
				waitForElementVisibleAndClickFromList(removeConfirmBtn, Constants.SHORTTIMEOUT, "Payment - Remove Confirm Button");
				invisibilityOfElementLocated(By.xpath(MyAccountLocators.removeConfirmBtn),"Payment Remove Modal Invisibility");
			}
		}catch(Exception e) {
			hardAssertionFail("Exception occured during the Payment Card's Cleanup with following message", e);
		}
	}

	public synchronized void removeCartItems() {
		try {
			Thread.sleep(2000);
			if((shoppingCartIcon.get(0).getText().contains("(0"))==false) {
				waitForMouseHoverActionFromList(shoppingCartIcon, Constants.SHORTTIMEOUT, "Shopping Bag Icon");
				waitForElementVisibleAndClickFromList(miniCartCheckoutButton, Constants.SHORTTIMEOUT, "Shopping Bag Icon");
				while(isElementPresentBy(By.cssSelector(MyAccountLocators.emptyCart))!=true) {	
					waitForElementsAndClick(shoppingCartRemoveButton, Constants.SHORTTIMEOUT, "Cart - Remove Item");
				}
			}
		}
		catch(Exception e) {
			hardAssertionFail("Exception occured during the Cart Cleanup with following message", e);
		}
	}

	String textValue() {
		String textValue = myAccountUserName.get(0).getText();
		return textValue;
	}

	@Override
	protected ExpectedCondition getPageLoadCondition() {
		return ExpectedConditions.visibilityOfAllElements(pageLoadCondition);
	}
}