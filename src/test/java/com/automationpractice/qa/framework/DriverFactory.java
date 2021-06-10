package com.automationpractice.qa.framework;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {

	private static WebDriver driver;
	private static Map<String, Object> preferences = new HashMap<String, Object>();
	static boolean setUpIsDone;
	public static String browserType=null;
	/**
	 * This method creates and returns a driver object 
	 * @return 
	 * 
	 * @throws IOException
	 * @throws Exception

	 */


	static  WebDriver getDriver() {
		if (driver == null || (driver != null && driver.toString().contains("null"))) {
		try {

				browserType =GeneralStepImplementations.browsername; 
					System.out.println("------------------------"+browserType);

				if (browserType != null && browserType.equalsIgnoreCase("chrome")) {

							System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
							preferences.clear();
							ChromeOptions options = new ChromeOptions();
							options.addArguments("--disable-features=RendererCodeIntegrity");	
							options.setPageLoadStrategy(PageLoadStrategy.NONE);	
							options.setExperimentalOption("prefs", preferences);
							//options.addArguments("--incognito");
							driver = new ChromeDriver(options);					
							driver.manage().deleteAllCookies();
							driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);			
							driver.manage().timeouts().pageLoadTimeout(300, TimeUnit.SECONDS);
							driver.manage().window().maximize();
						} else if (browserType.equalsIgnoreCase("firefox")) {
							quit();
							System.setProperty("webdriver.gecko.driver","geckodriver.exe");
									driver = new FirefoxDriver(); 
									driver.manage().window().maximize();
									driver.manage().deleteAllCookies();
									driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
									driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
							
				} else {
					throw new RuntimeException("Exception occured in creating driver object");
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("Exception occured in creating driver object");
			}
		}
	    		return driver;
	}

	public static void quit() {
		if (!(driver == null || (driver != null && driver.toString().contains("null")))) {
	//		driver.close();
			driver.quit();
			
			driver = null;
		}
	}
}