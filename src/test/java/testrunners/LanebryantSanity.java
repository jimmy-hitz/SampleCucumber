package testrunners;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.ascena.automation.extent.listeners.ExtentManager;
import com.ascena.automation.utilities.Hooks;
import com.ascena.automation.utilities.InitialDataSetup;
import com.ascena.automation.utilities.Utility;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:com.ascena.lb.features", glue = { "com.ascena.lb.stepsdefinitions",
		"com.ascena.automation.utilities" }, plugin = { "pretty", "html:target/cucumber-html-reports",
				"json:target/cucumber.json",
				"junit:target/cucumber.xml" }, tags = { "@sanity" }, strict = false, dryRun = false, monochrome = true)
public class LanebryantSanity {

	@BeforeClass
	public static void beforeClass() throws Exception {

		InitialDataSetup.setSuiteName("LanebryantSanity");
		InitialDataSetup.setBrand("LB");
		InitialDataSetup.setEnv("PROD");
		InitialDataSetup.setBrowser("chrome");
		Utility.setExcelWSheet("lanebryant");
	}

	@AfterClass
	public static void afterClass() {
		ExtentManager.getReporter().flush();
		Hooks.zipExtentReport();
	}
}
