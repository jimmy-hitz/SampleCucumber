package com.ascena.at.locators;

/**
 * Step Locator implementation class for Cucumber Steps defined in Feature file
 */

public class PDPLocators {

	public final static String productInfo = "//h2[contains(.,'PRODUCT INFORMATION')]";
	public final static String colorSwatch ="button[data-slnm-id=color-swatches]";
	public final static String sizeSwatch = "button[data-slnm-id=sizeSwatches]"; 
	public final static String addToBagButton = "button[data-slnm-id=addToBag]";
	public final static String bounceClientOverlay = "a.bx-close.bx-close-link.bx-close-inside";
	public final static String addToBagModal = "div.modal-header-container button[aria-label='close modal']";
	public final static String addToBagModalConShopButton = "//button[contains(.,'Checkout Now')]";
	public final static String anntaylorAddToBagModalConShopButton = "//a[contains(.,'Checkout Now')]";
	public final static String factoryFlyMiniCheckoutHeader = "div#bx-creative-932153 > div > a";
	public final static String outletFlyMiniCheckoutHeader = "div#bx-creative-917700 > div > a";
	public final static String shoppingBagIcon = "//span[contains(.,'BAG')]";
	public final static String anntaylorShoppingBagIcon = "a#my-bag-icon";
	public final static String anntaylorAddToBagButton = "input#pdp-add-to-bag";
	public final static String anntaylorColorSwatch = "//fieldset[@class='colors']//a[@role='radio']";
	public final static String anntaylorSizeSwatch = "//fieldset[@class='sizes']//a[@role='radio']";
	public final static String anntaylorAddtoBagModal = "div.overlay-header > a";
	
}