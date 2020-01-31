package com.ascena.lb.stepsdefinitions;

import org.openqa.selenium.WebDriver;
import com.ascena.automation.utilities.DriverManager;
import com.ascena.lb.page.objects.HomePage;

public class PageObjects {

	WebDriver driver = DriverManager.getDriver();
	HomePage homePage = new HomePage();
}
