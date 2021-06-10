package com.automationpractice.qa.framework;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.plexus.util.FileUtils;
//import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import io.cucumber.junit.CucumberOptions;
import io.cucumber.plugin.event.PickleStepTestStep;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;
import io.cucumber.junit.Cucumber;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


//import com.github.mkolisnyk.cucumber.runner.ExtendedCucumber;
//import com.github.mkolisnyk.cucumber.runner.ExtendedCucumberOptions;
//import com.github.mkolisnyk.cucumber.runner.ExtendedTestNGRunner;


@RunWith(Cucumber.class)
//

//@RunWith(ExtendedCucumber.class)
////
//@ExtendedCucumberOptions(jsonReport = "target/cucumber.json",
//retryCount = 3, // Number of times retry should happen in case of failure
//detailedReport = true,
//detailedAggregatedReport = true,
//overviewReport = true,
//jsonUsageReport = "target/cucumber-usage.json",
//usageReport = true,
//toPDF = true,
//excludeCoverageTags = {"" },//Tags which need to excluded from coverage Report
//includeCoverageTags = {"@ULS01" },//Tags which need to included into coverage Report
//outputFolder = "target")


@CucumberOptions(features = { "src/test/resources/features" },

tags = "@ULS01",


		glue = { "com/automationpractice/qa/framework"}, plugin = {

				"junit:target/defualtreports/cucumber-results.xml", "junit:target/defualtreports/cucumber-results.xml",
				"pretty", "html:target/defualtreports/cucumber-html-report.html",
				"json:target/defualtreports/cucumber.json" },
		dryRun = false)




public class RunCukesTest {

	public static ExtentReports report;
	public static ExtentTest test;
	static SimpleDateFormat stf = new SimpleDateFormat("ddMyyyy");
	static Date dt = new Date();
	static String date = stf.format(dt);
	static Path path = Paths.get(System.getProperty("user.dir") + "\\reports\\" + date);

	static SimpleDateFormat rrstf = new SimpleDateFormat("Hmm");
	static Date rrdt = new Date();
	static String rrdate = rrstf.format(rrdt);
	static Path rpath = Paths.get(System.getProperty("user.dir") + "\\reports\\" + date + "\\" + rrdate);
//	static GeneralStepImplementations GSI=new GeneralStepImplementations();

	@BeforeClass
	public static void init() throws Throwable {
		
		
		Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe /T");

		Files.createDirectories(path);

		report = new ExtentReports(rpath + "\\Reports.html");
		System.out.println("Generating reports at : " + rpath + "\\Reports.html");
	}

	@AfterClass
	public static void teardown() throws IOException {
		report.flush();
		Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe /T");

	}

	public void startreport(Scenario scenario) {
		test = report.startTest(scenario.getName());
		System.out.println("inside sce"+scenario.getName());
	}

	public void endtest() {
		report.endTest(test);
	}

	public static void addLogStatement(String message, LogStatus status) {
		
		test.log(status, message);
	}

	public static void addLogStatement(String message, LogStatus status, String color) {
		// test.log(status, message);
		// color = "red";

		test.log(status, color, "<span style='color:" + color + "'>" + message + "</span>");
	}

	public static void addLogStatement(String message, LogStatus status, WebDriver driver) {
		TakesScreenshot ts = (TakesScreenshot) driver;
		try {
			test.log(status, message + test.addScreenCapture(getImagePath(ts)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getImagePath(TakesScreenshot ss) throws IOException {
		File source = ss.getScreenshotAs(OutputType.FILE);

		SimpleDateFormat ssstf = new SimpleDateFormat("ddmmyyyyhhmmss");
		Date ssdt = new Date();
		String ssdate = ssstf.format(ssdt);

		String dest = rpath + "\\Screenshots\\image" + ssdate + ".png";
		System.out.println("destination " + dest);
		File destination = new File(dest);
		FileUtils.copyFile(source, destination);
		return dest;
	}
}
