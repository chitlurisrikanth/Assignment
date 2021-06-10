package com.automationpractice.qa.framework;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.configuration.XMLConfiguration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.automationpractice.qa.framework.RunCukesTest;
import com.relevantcodes.extentreports.LogStatus;

//import org.openqa.selenium.support.ui.WebDriverWait;

public class ElementFactory {
	private ElementFactory() {
	}

	/**
	 * This method creates and returns a Selenium WebElement based on the identifier
	 * provided.
	 * 
	 * @param identifier (identifier is calculated based on
	 *                   ObjectIdentityConfig.properties)
	 * @return WebElement
	 * @throws IOException
	 * @throws Exception
	 * @throws NoSuchMethodException
	 */
	

	public static WebElement getElement(String identifier) {
		WebElement element = null;
		XMLConfiguration config = null;
		try {
			
			config = new XMLConfiguration("configurations/object-config.xml");
			Method method = By.class.getMethod(config.getString(identifier + "[@locator]"), String.class);
			By by = (By) method.invoke(By.class, config.getString(identifier));
			element = DriverFactory.getDriver().findElement(by);
		} catch (Exception e) {
			RunCukesTest.addLogStatement(identifier + " is not found on the page", LogStatus.FAIL,
					DriverFactory.getDriver());
			System.err.println("Object Not found. Please verify if "+identifier+ " is configered object-config.xml");
			e.printStackTrace();
		} finally{
			config.clear();
		}
		return element;
	}
}
