package testrunners;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.ascena.automation.extent.listeners.ExtentManager;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import cucumber.api.testng.AbstractTestNGCucumberTests;


@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:com.ascena.at.features", glue = { "com.ascena.at.stepsdefinitions",
		"com.ascena.automation.utilities" }, plugin = { "pretty", "html:target/cucumber-html-reports",
				"json:target/cucumber.json",
				"junit:target/cucumber.xml", }, tags = { "" }, junit = {
						"--step-notifications" }, strict = false, dryRun = false, monochrome = true)

public class RunCukeTestAT extends AbstractTestNGCucumberTests{
	
	@BeforeClass
	public static void testSetup(){
		//System.out.println(ExtentManager.testSuiteName = "AT");
	}
}