package com.ascena.at.locators;

/**
 * Step Locator implementation class for Cucumber Steps defined in Feature file
 */

public class OrderReviewPageLocators {

	public final static String orderReviePageHeading = "//h2[contains(.,'review & place order')]";
	public final static String annOrderReviePageHeading = "//h2[contains(.,'3.Review & Place Order')]";
	public final static String checkoutPlaceOrderButton = "button[data-slnm-id=reviewPagePlaceOrder]";
	public final static String annCheckoutPlaceOrderButton = "#order-confirmation-continue";
	public final static String billingCVVNo = "input[data-slnm-id=cvv]";
	public final static String annBillingCVVNo = "#billing-cvv";
	public final static String errorMessageCheckoutAnntaylor = ".errors.server-errors.review-errors.active>p";
	public final static String errorMessageCheckoutFactory = ".error>div";
	
}