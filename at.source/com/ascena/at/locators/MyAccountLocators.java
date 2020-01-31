package com.ascena.at.locators;

/**
 * Step Locator implementation class for Cucumber Steps defined in Feature file
 */

public class MyAccountLocators {

	public final static String orderSectionHeading = "h2[data-slnm-id=orderSectionHeading]";
	public final static String annOrderSectionHeading = ".orders-returns.component.searched.empty>header>h2";
	public final static String myAccountUserName = "#main_hp > div > h1";
	public final static String annMyAccountUserName = ".my-account.dashboard>h1";
	public final static String contentSlot = "a[data-click=close]";
	public final static String addressBooklnk = "//a[contains(.,'Address Book')]";
	public final static String addressBookH1 = "h1[data-slnm-id=sectionHeading]";
	public final static String annAddressBookH1 = ".address-book.component>header>h2";
	public final static String noSavedAddress = "//*[text()='You have no saved addresses.']";
	public final static String allRemoveBtn = "button[data-slnm-id*=removeCard]";
	public final static String annallRemoveBtn = "button.remove";
	public final static String removeBtn = "button[data-slnm-id=removeCardBtn0]";
	public final static String annRemoveBtn = "//button[@class='remove' and @data-value='0']";
	public final static String removeConfirmBtn = "//button[contains(text(), 'Confirm')]";
	public final static String annRemoveConfirmBtn = ".remove-confirm";
	
	
	public final static String removeAddNotification = "div[arialabel=\"Address removed successfully\"]";
	public final static String removePaymentNotification = "//div[@arialabel='Credit Card removed successfully']";
	public final static String paymentMethodLink = "//a[contains(.,'Payment Methods')]";
	public final static String paymentMethodHeading = "h1[data-slnm-id=sectionHeading]";
	public final static String annPaymentMethodHeading = ".payment-methods.component>header>h2";
	public final static String paymentMethodNotification = "div[arialabel=Credit Card removed successfully]";
	public final static String noSavedPayment = "p[data-slnm-id=noSavedCardText]";
	public final static String annNoSavedPayment = "//p[contains(.,'You have no saved cards.')]";
	public final static String shoppingCartIcon = "//i[contains(@class,'icon bag')]";
	public final static String shoppingCartRemoveButton = "button[data-slnm-id=itemRemoveButton]";
	public final static String annShoppingCartRemoveButton = "//a[contains(.,'remove')]";
	public final static String shoppingCartIconWithoutItem = "//span[contains(.,'BAG (0)')]";
	public final static String removeModalHeading = "h1[data-slnm-id=modalWrapperTitle]";
	public final static String miniCartCheckoutButton = "a[data-slnm-id=checkoutMiniCart]";
	public final static String annMiniCartCheckoutButton = ".checkout";
	public final static String emptyCart = "a[data-slnm-id=emptyShoppingBagPageLink]";
	public final static String annEmptyCart = ".my-bag.empty>a";
}