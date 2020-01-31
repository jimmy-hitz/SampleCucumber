package com.ascena.at.locators;

/**
 * Step Locator implementation class for Cucumber Steps defined in Feature file
 */

public class HomePageLocators {

	public final static String factoryBouncePopUp = "div[id*=bx-creative] > div > a";
	public final static String outletBouncePopUp = "//button[contains(.,'NO THANKS, I PREFER TO PAY FULL PRICE')]";
	public final static String megaMenu = "[data-slnm-id=navbar]";
	public final static String megaMenuSupCat = "//*[@data-slnm-id='navbar']//div[contains(text(),'Clothing')]";
	public final static String annMegaMenuSupCat = "//nav[@id='main-nav']//a[@id='cata00002']/strong";
	public final static String annMegaMenuSubCat = "a#cata00002-1-cata00008";
	public final static String megaMenuSubCat = "//*[@data-slnm-id='navbar']//span[contains(text(),'Sweaters')]";
	public final static String subMegaMenuDiv = "div[data-slnm-id=megaMenu]";
	public final static String signInButton = "button[data-slnm-id=userAccount]";
	public final static String annSignInButton = "a[aria-label='Sign in menu']";
	public final static String signInLink = "a[data-slnm-id=signIn]";
	public final static String annSignInLink = "//div[@class='sign-in hasSub']/div[@class='sub']/a[text()='Sign In']";
	public final static String searchButton = "button[data-slnm-id=searchButton]";
	public final static String annSearchButton = "#search-toggle";
	public final static String searchTextButton = "input[data-slnm-id=searchBar]";
	public final static String annSearchTextButton = ".typeahead.tt-input";
	public final static String searchIcon = "div.input-tick-wrapper > input";
	public final static String searchIconAnntaylor = ".typeahead.tt-input";
	public final static String searchSpan = "//span[contains(.,'What can we help you find?')]";
	public final static String selectCountry = "div#country-selector > a";
	public final static String countryText = "span#dd-label-1";
	public final static String countrySelectionButton = ".btn-back";
	public final static String internationalContextModal = "div#country-selector > div:nth-of-type(2)";
	public final static String anntaylorImage = "img[alt='Ann Taylor']";
	public final static String internationalContextImage = "a.international-shipping>img";
}