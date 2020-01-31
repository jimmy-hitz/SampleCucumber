package com.ascena.at.locators;

/**
 * Step Locator implementation class for Cucumber Steps defined in Feature file
 */

public class PaymentPageLocators {

	public final static String billingCreditCard = "input#cardNumber";
	public final static String annBillingCreditCard = "#cardNumber";
	public final static String billingCCMonth = "select#exp_month";
	public final static String annBillingCCMonth = "#exp_month-span";
	public final static String billingCCYear = "select#exp_year";
	public final static String annBillingCCYear = "#exp_year-span";
	public final static String billingCVV = "input#cvvCode";
	public final static String annBillingCVV = "#cvvCode";
	public final static String billingReviewOrderButton = "input[aria-label='Review Order']";
	public final static String annBillingReviewOrderButton = "#billing-btn-revieworder";
	public final static String billingPageHeading = "//h2[contains(.,' Shipping & Handling')]";
	public final static String annBillingPageHeading = ".shipping-information>h2";
	public final static String paymentIFrameName = "sparkred-iframe";
	
}