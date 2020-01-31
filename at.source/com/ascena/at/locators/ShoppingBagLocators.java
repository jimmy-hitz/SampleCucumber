package com.ascena.at.locators;

/**
 * Step Locator implementation class for Cucumber Steps defined in Feature file
 */

public class ShoppingBagLocators {

	public final static String cartOrderTotal = "h3[data-slnm-id*=order-total]";
	public final static String annCartOrderTotal = ".order-summary.component>h2";
	public final static String proceedToCheckout = "button[data-slnm-id=proceedToCheckout]";
	public final static String annProceedToCheckout = "#checkout-proceed";
	public final static String h1InsideCheckoutModal = "h2[data-slnm-id=signInHeading]";
	public final static String annH1InsideCheckoutModal = ".overlay-header>h1";
	public final static String guestCheckoutEmailID = "input[data-slnm-id=checkoutSignInEmailInput]";
	public final static String annGuestCheckoutEmailID = "#modal-email-guest";
	public final static String checkoutSubmitButton = "button[data-slnm-id=checkoutSignInFormSubmit]";
	public final static String annCheckoutSubmitButton = "#modal-continue-button";
	public final static String regUserEmailID = "input[data-slnm-id=loginEmail]";
	public final static String annRegUserEmailID = "#modal-email-registered";
	public final static String regUserPwd = "input[data-slnm-id=loginPassword]";
	public final static String annRegUserPwd = "#modal-password-field";
	public final static String regUserSignButton = "button[data-slnm-id=loginSubmit]";
	public final static String annRegUserSignButton = "div.slot #modal-signin-button";
}