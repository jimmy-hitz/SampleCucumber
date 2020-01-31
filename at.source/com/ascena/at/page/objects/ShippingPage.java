package com.ascena.at.page.objects;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import com.ascena.at.locators.ShippingPageLocators;
import com.ascena.automation.utilities.BasePage;
import com.ascena.automation.utilities.Constants;
import com.ascena.automation.utilities.DriverManager;
import com.ascena.automation.utilities.Utility;

/**
 * Step Action implementation class for Cucumber Steps defined in Feature file
 */

public class ShippingPage extends BasePage {

	public ShippingPage() {
		PageFactory.initElements(DriverManager.getDriver(), this);
	}
	
	String expectedShippingCity;

	@FindAll({	
		@FindBy(css = ShippingPageLocators.vertexUseThisAddress),
		@FindBy(css = ShippingPageLocators.annVertexUseThisAddress),
	}) public List<WebElement> shippingVertexList;

	@FindAll({	
		@FindBy(css = ShippingPageLocators.shippingPageLabel),
		@FindBy(css = ShippingPageLocators.annShippingPageLabel),
	}) public List<WebElement> pageLoadCondition;

	@FindAll({	
		@FindBy(css = ShippingPageLocators.shippingFirstName),
		@FindBy(css = ShippingPageLocators.annShippingFirstName),
	}) public List<WebElement> shippingFName;

	@FindAll({	
		@FindBy(css = ShippingPageLocators.shippingLastName),
		@FindBy(css = ShippingPageLocators.annShippingLastName),
	}) public List<WebElement> shippingLName;

	@FindAll({	
		@FindBy(css = ShippingPageLocators.shippingAddress1),
		@FindBy(css = ShippingPageLocators.annShippingAddress1),
	}) public List<WebElement> shippingAddress1;

	@FindAll({	
		@FindBy(css = ShippingPageLocators.shippingZipCode),
		@FindBy(css = ShippingPageLocators.annShippingZipCode),
	}) public List<WebElement> shippingZipCode;

	@FindAll({	
		@FindBy(css = ShippingPageLocators.shippingPhoneNo),
		@FindBy(css = ShippingPageLocators.annShippingPhoneNo),
	}) public List<WebElement> shippingPhoneNo;

	@FindAll({	
		@FindBy(css = ShippingPageLocators.annShippingEmailID),
	}) public List<WebElement> shippingEmailID;
	
	@FindAll({	
		@FindBy(css = ShippingPageLocators.shippingCity),
		@FindBy(css = ShippingPageLocators.annShippingCity),
	}) public List<WebElement> shippingCity;

	@FindAll({	
		@FindBy(xpath = ShippingPageLocators.shippingContinuePaymentButton),
		@FindBy(css = ShippingPageLocators.annShippingContinuePaymentButton),
	}) public List<WebElement> shippingSubmitBtn;

	@FindAll({	
		@FindBy(xpath = ShippingPageLocators.shippingMethodsCheckbox),
		@FindBy(css = ShippingPageLocators.annShippingMethodsCheckbox),
	}) public List<WebElement> shippingMethodsCheckbox;

	@FindAll({	
		@FindBy(xpath = ShippingPageLocators.shippingMethods),
		@FindBy(css = ShippingPageLocators.annShippingMethods),
	}) public List<WebElement> shippingMethods;

	@FindAll({	
		@FindBy(css = ShippingPageLocators.poBoxCheckbox),
		@FindBy(css = ShippingPageLocators.annPoBoxCheckbox),
	}) public List<WebElement> poBoxCheckbox;

	public synchronized PaymentPage ecommShippingAddSubmission(String sFName, String sLName, String sAddressL1, String sZipCode, String sPNumber) {
		try {
			String ShippingMethodText=null,ShippingCheckBoxStatus=null,expectedShippingMethod=null;
			String checkPOBox = Utility.getDataAsString(Utility.sJSONReader("ShipAddWBookName"), Utility.sJSONReader("ShipAddTData"), sAddressL1, Utility.sJSONReader("ShipPOBox"));

			typeElementValueFromList(shippingFName,Utility.getDataAsString(Utility.sJSONReader("ShipAddWBookName"), Utility.sJSONReader("ShipAddTData"), sFName, Utility.sJSONReader("ShipAddFName")),"Shipping First Name");
			typeElementValueFromList(shippingLName,Utility.getDataAsString(Utility.sJSONReader("ShipAddWBookName"), Utility.sJSONReader("ShipAddTData"), sLName, Utility.sJSONReader("ShipAddLName")),"Shipping Last Name");
			if(checkPOBox.equalsIgnoreCase("Yes")) {
				clickOnElementFromList(poBoxCheckbox, "PO Box Checkbox");
			}
			typeElementValueFromList(shippingAddress1,Utility.getDataAsString(Utility.sJSONReader("ShipAddWBookName"), Utility.sJSONReader("ShipAddTData"), sAddressL1, Utility.sJSONReader("ShipAddressLine1")),"Shipping Address1");
			typeElementValueFromList(shippingZipCode,Utility.getDataAsString(Utility.sJSONReader("ShipAddWBookName"), Utility.sJSONReader("ShipAddTData"), sZipCode, Utility.sJSONReader("ShipAddZCode")),"Shipping ZipCode");
			typeElementValueFromList(shippingPhoneNo,Utility.getDataAsString(Utility.sJSONReader("ShipAddWBookName"), Utility.sJSONReader("ShipAddTData"), sPNumber, Utility.sJSONReader("ShipAddPNumber")),"Shipping PhoneNo");

			expectedShippingMethod = Utility.getDataAsString(Utility.sJSONReader("ShipAddWBookName"), Utility.sJSONReader("ShipAddTData"), "TestData7", Utility.sJSONReader("ShipAddMethod"));
			expectedShippingCity = Utility.getDataAsString(Utility.sJSONReader("ShipAddWBookName"), Utility.sJSONReader("ShipAddTData"), "TestData7", Utility.sJSONReader("ShipAddCity"));
			verifyElementValue(shippingCity,"City Field Value");

			outerloop:
				for(int i=0; i<shippingMethods.size(); i++) {
					ShippingMethodText = (shippingMethods.get(i).getText()).split("-")[0];
					ShippingMethodText = ShippingMethodText.replaceAll("\\s","");

					if(ShippingMethodText.equalsIgnoreCase(expectedShippingMethod.trim())) {
						ShippingCheckBoxStatus = shippingMethodsCheckbox.get(i).getAttribute("class").toString();
						if((ShippingCheckBoxStatus).contains("-checked")) {
							break outerloop;
						}else if ((ShippingCheckBoxStatus).contains("-unchecked")) {
							clickOnElement(shippingMethodsCheckbox.get(i),"Shipping Method : "+ShippingMethodText);
						}else {
							System.out.println("Shipping method was neither selected or got identified and hence else was executed");
						}
					}
				}
			scrollIntoViewElementFromList(shippingSubmitBtn, Constants.SHORTTIMEOUT);
			clickOnElementFromList(shippingSubmitBtn,"Submit Button");
			clickOnElementInOptionalModal(shippingVertexList, Constants.AVGTIMEOUT, "(Vertex)");
			Thread.sleep(3000);
		}catch(Exception e) {		
			hardAssertionFail("Exception occured while filling shipping page information with following message", e);
		}
		return (PaymentPage) openPage(PaymentPage.class);
	}

	public synchronized PaymentPage ecommShippingAddSubmissionNewLayout(String sFName, String sLName, String sAddressL1, String sZipCode, String sPNumber, String emailID) {
		try {
			String ShippingMethodText=null,ShippingCheckBoxStatus=null,expectedShippingMethod=null;
			String checkPOBox = Utility.getDataAsString(Utility.sJSONReader("ShipAddWBookName"), Utility.sJSONReader("ShipAddTData"), sAddressL1, Utility.sJSONReader("ShipPOBox"));

			typeElementValueFromList(shippingFName,Utility.getDataAsString(Utility.sJSONReader("ShipAddWBookName"), Utility.sJSONReader("ShipAddTData"), sFName, Utility.sJSONReader("ShipAddFName")),"Shipping First Name");
			typeElementValueFromList(shippingLName,Utility.getDataAsString(Utility.sJSONReader("ShipAddWBookName"), Utility.sJSONReader("ShipAddTData"), sLName, Utility.sJSONReader("ShipAddLName")),"Shipping Last Name");
			if(checkPOBox.equalsIgnoreCase("Yes")) {
				clickOnElementFromList(poBoxCheckbox, "PO Box Checkbox");
			}
			typeElementValueFromList(shippingAddress1,Utility.getDataAsString(Utility.sJSONReader("ShipAddWBookName"), Utility.sJSONReader("ShipAddTData"), sAddressL1, Utility.sJSONReader("ShipAddressLine1")),"Shipping Address1");
			typeElementValueFromList(shippingZipCode,Utility.getDataAsString(Utility.sJSONReader("ShipAddWBookName"), Utility.sJSONReader("ShipAddTData"), sZipCode, Utility.sJSONReader("ShipAddZCode")),"Shipping ZipCode");
			typeElementValueFromList(shippingPhoneNo,Utility.getDataAsString(Utility.sJSONReader("ShipAddWBookName"), Utility.sJSONReader("ShipAddTData"), sPNumber, Utility.sJSONReader("ShipAddPNumber")),"Shipping PhoneNo");
			typeElementValueFromList(shippingEmailID,Utility.getDataAsString(Utility.sJSONReader("ShipAddWBookName"), Utility.sJSONReader("ShipAddTData"), emailID, Utility.sJSONReader("ShipAddEmailID")),"Shipping Email ID");

			expectedShippingMethod = Utility.getDataAsString(Utility.sJSONReader("ShipAddWBookName"), Utility.sJSONReader("ShipAddTData"), "TestData7", Utility.sJSONReader("ShipAddMethod"));
			expectedShippingCity = Utility.getDataAsString(Utility.sJSONReader("ShipAddWBookName"), Utility.sJSONReader("ShipAddTData"), "TestData7", Utility.sJSONReader("ShipAddCity"));
			verifyElementValue(shippingCity,"City Field Value");

			outerloop:
				for(int i=0; i<shippingMethods.size(); i++) {
					ShippingMethodText = (shippingMethods.get(i).getText()).split("-")[0];
					ShippingMethodText = ShippingMethodText.replaceAll("\\s","");

					if(ShippingMethodText.equalsIgnoreCase(expectedShippingMethod.trim())) {
						ShippingCheckBoxStatus = shippingMethodsCheckbox.get(i).getAttribute("class").toString();
						if((ShippingCheckBoxStatus).contains("-checked")) {
							break outerloop;
						}else if ((ShippingCheckBoxStatus).contains("-unchecked")) {
							clickOnElement(shippingMethodsCheckbox.get(i),"Shipping Method : "+ShippingMethodText);
						}else {
							System.out.println("Shipping method was neither selected or got identified and hence else was executed");
						}
					}
				}
			scrollIntoViewElementFromList(shippingSubmitBtn, Constants.SHORTTIMEOUT);
			clickOnElementFromList(shippingSubmitBtn,"Submit Button");
			clickOnElementInOptionalModal(shippingVertexList, Constants.AVGTIMEOUT, "(Vertex)");
		}catch(Exception e) {		
			hardAssertionFail("Exception occured while filling shipping page information with following message", e);
		}
		return (PaymentPage) openPage(PaymentPage.class);
	}
	
	public synchronized void verifyElementValue(List<WebElement> elements, String elementName) {	
		String textValue=null;int elementCount=0;
		long starttime = System.currentTimeMillis();
		long endtime = starttime + Long.parseLong(Utility.sJSONReader("genericTimeOut"));
		outerloop:
			while(System.currentTimeMillis() < endtime)
			{		
				try{
					elementCount = elements.size();
					for(int i=0; i<elementCount; i++) {
						try {
							if(elements.size()==0) {
								wait = new FluentWait<WebDriver>(driver).withTimeout(Constants.SHORTTIMEOUT, TimeUnit.SECONDS).pollingEvery(1,
										TimeUnit.SECONDS);
								wait.until(ExpectedConditions.visibilityOfAllElements(elements));
								textValue = elements.get(i).getAttribute("value").toString().trim();
								if(!textValue.isEmpty()) {
									if((textValue).equalsIgnoreCase(expectedShippingCity)){
										break outerloop;
									}else {
										softAssertion("Expected City : "+expectedShippingCity+" did not match Actual City : "+textValue);
										break outerloop;
									}
								}
							}else{
								textValue = elements.get(i).getAttribute("value").toString().trim();
								if(!textValue.isEmpty()) {
									if((textValue).equalsIgnoreCase(expectedShippingCity)){
										break outerloop;
									}else {
										softAssertion("Expected City : "+expectedShippingCity+" did not match Actual City : "+textValue);
										break outerloop;
									}
								}
							}
						}catch(Exception e) {
							if(i==0) {
								hardAssertionFail("Exception occured while verifying shipping city with following message", e);
							}
							if(i==1) {
								break outerloop;
							}
						}
					}
				}catch(Exception e) {
				}
			}
	}

	@Override
	protected ExpectedCondition getPageLoadCondition() {
		return ExpectedConditions.visibilityOfAllElements(pageLoadCondition);
	}
}