package com.ascena.at.locators;

/**
 * Step Locator implementation class for Cucumber Steps defined in Feature file
 */

public class ShippingPageLocators {

	public final static String shippingPageLabel = "h3[data-slnm-id=shipToLabel]";
	public final static String annShippingPageLabel = ".address-sugesstion>legend";
	public final static String shippingFirstName = "input[data-slnm-id=shippingPageFirstName]";
	public final static String annShippingFirstName = "#checkout-field-firstname";
	public final static String shippingLastName = "input[data-slnm-id=shippingPageLastName]";
	public final static String annShippingLastName = "#checkout-field-lastname";
	public final static String shippingAddress1 = "input[data-slnm-id=shippingPageAddress1]";
	public final static String annShippingAddress1 = "#checkout-field-address1";
	public final static String shippingZipCode = "input[data-slnm-id=shippingPagePostalCode]";
	public final static String annShippingZipCode = "#checkout-field-zipcode";
	public final static String shippingPhoneNo = "input[data-slnm-id=shippingPagePhoneNumber]";
	public final static String annShippingPhoneNo = "#billing-phonenumber";
	public final static String annShippingEmailID = "#billing-email";
	public final static String shippingCity = "input[data-slnm-id=shippingPageCity]";
	public final static String annShippingCity = "#locality";
	public final static String shippingContinuePaymentButton = "//button[contains(.,'Continue To Payment')]";
	public final static String annShippingContinuePaymentButton = "#shipping-continue";
	public final static String shippingMethodsCheckbox = "//label[starts-with(@data-slnm-id,'shippingMethodCheckbox')]//span[@data-slnm-id='checkbox']";
	public final static String annShippingMethodsCheckbox = ".custom-radio";
	public final static String shippingMethods = "//label[starts-with(@data-slnm-id,'shippingMethodCheckbox')]//span/span/strong";
	public final static String annShippingMethods = "label[role=radio] > strong";
	public final static String vertexUseThisAddress = "button[data-slnm-id=shippingPageUseSuggestedAddress]";
	public final static String annVertexUseThisAddress = ".recommended-confirm";
	public final static String poBoxCheckbox = "label > span[data-slnm-id=shippingPagePOBox]";
	public final static String annPoBoxCheckbox = "#pobox";
}