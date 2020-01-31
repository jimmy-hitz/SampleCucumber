package com.ascena.at.locators;

/**
 * Step Locator implementation class for Cucumber Steps defined in Feature file
 */

public class PLPLocators {

	public final static String filterByLabel = "span[data-slnm-id=filterByLabel]";
	public final static String productsList = "img[data-slnm-id*=ProductGridImage]";
	public final static String totalItemCountLabel = "//span[contains(.,'TOTAL ITEMS')]";
	public final static String anntaylorSortBy = "//span[contains(.,'Sort By')]";
	public final static String anntaylorPLPProducts = "div.product-wrap";
	
}