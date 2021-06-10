package com.automationpractice.qa.framework;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.validator.UrlValidator;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.By;

import com.relevantcodes.extentreports.LogStatus;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class GeneralStepImplementations {
	XMLConfiguration genconfig;
	public static boolean dunit = false;
	RunCukesTest rct = new RunCukesTest();
	public static Scenario scenario_static;
	private Collection<String> tags=new ArrayList<String>();;
	public static String browsername;
	public static int retries = 0;
	
	
//	@RetryAcceptance
//    public static boolean retryCheck(Throwable e) {
//        // Does not allow re-run if error message contains "Configuration failed" phrase
//        return !e.getMessage().contains("Configuration failed");
//    }
	
	@After
	public void atTheEnd() {

		if (!dunit) {
			Runtime.getRuntime().addShutdownHook(new Thread() {
				public void run() {
					System.out.println("snake!");
					// DriverFactory.getDriver().close();
					DriverFactory.quit();
				}
			});
			System.out.println("badger");
			dunit = true;
		}
		DriverFactory.quit();
		rct.endtest();
		genconfig.clear();
	}

	@Before
	public void setUp(Scenario scenario) throws Exception {
		
		scenario_static = scenario;
		System.out.println("scenario name is : "+scenario.getSourceTagNames());
		tags=scenario.getSourceTagNames();
		if(tags.contains("@chrome")) {
			browsername = "chrome";
			System.out.println("---------------chrome");
			
		}else if(tags.contains("@firefox")) {
			browsername = "firefox";
			System.out.println("firefox");
		}else {
			System.out.println("default");
			browsername = "chrome";
		}
		try {
//			 System.out.println("Working Directory = " + System.getProperty("user.dir"));
			genconfig = new XMLConfiguration(System.getProperty("user.dir")+"/src/test/resources/configurations/object-config.xml");
			genconfig.setDelimiterParsingDisabled(false);
			genconfig.load("configurations/object-config.xml");
			
		}catch(ConfigurationException ce) {
			System.out.println(ce.getMessage());
		}
		//		genconfig.setDelimiterParsingDisabled(false);
//		genconfig.load("configurations/object-config.xml");
		
		rct.startreport(scenario_static);
	}
	
//	public void printToReports(String message) {
////		scenario_static.log(message);
//		RunCukesTest.addLogStatement(message, LogStatus.PASS);
//	}
	
	/**
	 * This method navigates to the url and print the url to the report
	 * 
	 * @return driver object
	 * @throws IOException
	 * @throws Exception

	 */
	
	


	@And("^I click on \"([^\"]*)\" link$")
	public void i_click_on_button(String buttonIdentifier) {
		int retrycount;
		
		for(retrycount=0;retrycount<=3;retrycount++) {
			String textonscreen = DriverFactory.getDriver().findElement(By.tagName("body")).getText();
//			Assert.assertTrue("The Element \"" + Identifier + "\" is not displayed on the page", titleTextfield1);
			if(textonscreen.contains("Resource Limit Is Reached")) {
				RunCukesTest.addLogStatement("Refresh page and try again ", LogStatus.INFO);
				DriverFactory.getDriver().navigate().refresh();
				
				ElementFactory.getElement(buttonIdentifier).click();
			}else {
				ElementFactory.getElement(buttonIdentifier).click();
			break;
			}	
		}
//		ElementFactory.getElement(buttonIdentifier).click();
	}

	@And("^I enter \"([^\"]*)\" into \"([^\"]*)\" textbox$")
	public void i_enter_value_into_textfield(String value, String identifier) {

		String text_enter;
		try {
			text_enter = genconfig.getString(value);
		} catch (Exception e) {
			text_enter = value;
		}
		if ((text_enter == null)) {
			text_enter = value;
		}

		ElementFactory.getElement(identifier).clear();
		ElementFactory.getElement(identifier).sendKeys(text_enter);		
		RunCukesTest.addLogStatement("Entered " + value + " into " + identifier,LogStatus.PASS);
	}
	
	@Then("^close browser window$")
	public void close_browser_window() {
		DriverFactory.getDriver().close();
	}
	
	@And("^I should see \"([^\"]*)\" on the page$")
	public void verify_element_presence(String textToVerify) {

		String textonscreen = DriverFactory.getDriver().findElement(By.tagName("body")).getText();
//		Assert.assertTrue("The Element \"" + Identifier + "\" is not displayed on the page", titleTextfield1);
		if(textonscreen.contains(textToVerify)) {
			RunCukesTest.addLogStatement(textToVerify+" found on the page ", LogStatus.PASS);
		}else {
			RunCukesTest.addLogStatement(textToVerify+" not found on the page", LogStatus.FAIL,DriverFactory.getDriver());
			
		}
	}

	@Then("^I wait for \"([^\"]*)\" seconds$")
	public static void i_wait_for_given_seconds(String seconds) {
		try {
			Thread.sleep(new Long(seconds) * 1000);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Then("^I validate username textbox with form having \"([^\"]*)\" class$")
	public void validateUsernameClass(String xpath) {
		String xpathtovalidate=genconfig.getString(xpath);
//		System.out.println(DriverFactory.getDriver().findElements(By.xpath(xpathtovalidate)).size());
		if(DriverFactory.getDriver().findElements(By.xpath(xpathtovalidate)).size() != 0) {
			RunCukesTest.addLogStatement("webpage contains valid class" ,LogStatus.PASS);
		}
		else {
			RunCukesTest.addLogStatement("webpage doesnt contains valid class" ,LogStatus.FAIL,DriverFactory.getDriver());
			
		}
	}

	@Given("^I Navigate to \"([^\"]*)\" in the browser$")
	public void I_naviagte_to_url(String url)  {
//		System.out.println("scenario name is : "+scenario.getName());
		String[] schemes = {"http","https"}; // DEFAULT schemes = "http", "https", "ftp"
		UrlValidator urlValidator = new UrlValidator(schemes);
		if (urlValidator.isValid(url)) {
			DriverFactory.getDriver().get(url);
			int retrycount;
			for(retrycount=0;retrycount<=3;retrycount++) {
				String textonscreen = DriverFactory.getDriver().findElement(By.tagName("body")).getText();
				
				if(textonscreen.contains("Resource Limit Is Reached")) {
					System.out.println(textonscreen);	
					RunCukesTest.addLogStatement("Refresh page and try again ", LogStatus.INFO);
					DriverFactory.getDriver().navigate().refresh();
					DriverFactory.getDriver().get(url);
				}else {
					
					break;
				}	
			}
			
			
			
			RunCukesTest.addLogStatement("Navigated to "+url,LogStatus.PASS);
		} else {
			RunCukesTest.addLogStatement("invalid url", LogStatus.FAIL,DriverFactory.getDriver());
		}
		
	}
	
	@Given("^I validate \"([^\"]*)\" error with alert danger$")
	public void validateAlertDanger(String field)  {

		if (field.equalsIgnoreCase("username")) {
			String oneerror=genconfig.getString("signin.name_error_message");
			String usernameerror1=genconfig.getString("signin.username_error_message1");
			if((DriverFactory.getDriver().findElements(By.xpath(oneerror)).size() != 0) && (DriverFactory.getDriver().findElements(By.xpath(usernameerror1)).size() != 0) ) {
				RunCukesTest.addLogStatement("webpage contains valid username errors" ,LogStatus.PASS);
			}
			else {
				RunCukesTest.addLogStatement("webpage doesnt contains valid username errors" ,LogStatus.FAIL,DriverFactory.getDriver());
				
			}	
		} else if(field.equalsIgnoreCase("emptypassword")){
			String oneerror=genconfig.getString("signin.name_error_message");
			String passworderror=genconfig.getString("signin.password_error_message");
			if((DriverFactory.getDriver().findElements(By.xpath(oneerror)).size() != 0) && (DriverFactory.getDriver().findElements(By.xpath(passworderror)).size() != 0) ) {
				RunCukesTest.addLogStatement("webpage contains valid password errors" ,LogStatus.PASS);
			}
			else {
				RunCukesTest.addLogStatement("webpage doesnt contains valid password errors "+ passworderror,LogStatus.FAIL,DriverFactory.getDriver());
				
			}
		
		}else if(field.equalsIgnoreCase("invalidpassword")){
			String oneerror=genconfig.getString("signin.name_error_message");
			String inpassworderror=genconfig.getString("signin.inpassword_error_message");
			if((DriverFactory.getDriver().findElements(By.xpath(oneerror)).size() != 0) && (DriverFactory.getDriver().findElements(By.xpath(inpassworderror)).size() != 0) ) {
				RunCukesTest.addLogStatement("webpage contains invalid password errors" ,LogStatus.PASS);
			}
			else {
				RunCukesTest.addLogStatement("webpage doesnt contains invalid password errors "+ inpassworderror,LogStatus.FAIL,DriverFactory.getDriver());
				
			}
		
		}
		else {
			String oneerror=genconfig.getString("signin.name_error_message");
			String autherror=genconfig.getString("signin.auth_error_message");
			if((DriverFactory.getDriver().findElements(By.xpath(oneerror)).size() != 0) && (DriverFactory.getDriver().findElements(By.xpath(autherror)).size() != 0) ) {
				RunCukesTest.addLogStatement("webpage contains valid auth errors" ,LogStatus.PASS);
			}
			else {
				RunCukesTest.addLogStatement("webpage doesnt contains valid auth errors" ,LogStatus.FAIL,DriverFactory.getDriver());
				
			}
		}
		
	}

}
