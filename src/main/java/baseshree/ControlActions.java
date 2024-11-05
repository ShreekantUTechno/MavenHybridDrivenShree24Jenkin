package baseshree;

import java.io.IOException;
import java.time.Duration;
import java.util.Set;
import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import path.Constant;

public class ControlActions {
	protected static WebDriver driver;
	private static JavascriptExecutor js;
	private static WebDriverWait wait;
	private static String mainWindowHandleId;
	
	public static void start(String url){
		String browsertype=System.getProperty("browser");
		System.out.println("STEP 1.1 - Launch browser");
		
		switch(browsertype.toLowerCase()) {
		case "chrome":
			driver = new ChromeDriver();
			break;
		case "edge":
			driver = new EdgeDriver();
			break;
		case "firefox":
			driver = new FirefoxDriver();
			break;
		default :
			throw new RuntimeException("browser not supported : "+ browsertype);
		}
		
		System.out.println("STEP 1.2 - Load url");
		driver.get(url);
		wait = new WebDriverWait(driver, Duration.ofSeconds(Constant.WAIT));
		js =(JavascriptExecutor)driver; 
		driver.manage().window().maximize();
		mainWindowHandleId = driver.getWindowHandle();
	}
	
	public static WebElement getElement(String locatorType, String locatorValue, boolean isWaitRequired){
		WebElement e = null;
					
		switch(locatorType.toUpperCase()){
			case "XPATH":
				if(isWaitRequired){
					e = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorValue)));
				}else{
					e = driver.findElement(By.xpath(locatorValue));
				}
				break;
				
			case "ID":
				if(isWaitRequired){
					e = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorValue)));
				}else{
					e = driver.findElement(By.id(locatorValue));
				}
				break;
			
			case "NAME" : 
				if(isWaitRequired){
					e = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locatorValue)));
				}else{
					e = driver.findElement(By.name(locatorValue));
				}
				break;
				
			case "LINKTEXT" : 
				if(isWaitRequired){
					e = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(locatorValue)));
				}else{
					e = driver.findElement(By.linkText(locatorValue));
				}
				break;	
				
			default : 
				//throw new InvalidLocatorException("Valid locators are ID, NAME, XPATH ...., but you have passed " + locatorType  + " which is not supported");
		}
		return e;
	}
	
	protected boolean isElementDisplayed(WebElement e, boolean isWaitRequired) {
		try {
			if(isWaitRequired)
				waitForVisibilityOfElement(e);
			return e.isDisplayed();
		}catch(NoSuchElementException | TimeoutException te) {
			return false;
		}
	}
	
	protected boolean isElementDisplayed(WebElement e, int timeout) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			wait.until(ExpectedConditions.visibilityOf(e));
			return e.isDisplayed();
		}catch(NoSuchElementException | TimeoutException te) {
			return false;
		}
	}
	
	protected void waitForInvisibilityOfElement(WebElement e) {
		wait.until(ExpectedConditions.invisibilityOf(e));
	}
	
	protected static WebElement waitForVisibilityOfElement(WebElement e) {
		return wait.until(ExpectedConditions.visibilityOf(e));
	}
	
	protected static void clickUsingJavaScriptExecutor(WebElement e) {
		js.executeScript("arguments[0].click()", e);
	}
	
	protected static String getTextUsingJavascript(WebElement e,boolean isWaitReq) {
		if(isWaitReq) {
			waitForVisibilityOfElement(e);
		}
		return (String)js.executeScript("return arguments[0].innerText;", e);
	}
	
	public static void takeScreenshot(String screenShotName) {
		TakesScreenshot scr =(TakesScreenshot)driver;
		File scrFile= scr.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File(".//screenshots/"+screenShotName+".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected  static void clickOnElement(WebElement e,boolean isWaitReq,boolean isscrollreq) {
		if(isWaitReq) {
			waitForVisibilityOfElement(e);
			e.click();
		}else if(isscrollreq){
			waitForVisibilityOfElement(e);
			js.executeScript("arguments[0].scrollIntoView(true)",e);
		}else{
			e.click();
		}
	}
	
	protected void enterText(WebElement e,String text,boolean isWaitReq,boolean isScrollReq) {
		if(isScrollReq) {
			js.executeScript("arguments[0].scrollIntoView(true)",e);
			e.sendKeys(text);
		}else if(isWaitReq){
			waitForVisibilityOfElement(e);
			e.sendKeys(text);
		}else {
			e.sendKeys(text);
		}
	}
	
	protected void enterTextUsingJavascriptExecutor(WebElement e,String text,boolean isWiatReq) {
		if(isWiatReq) {
			waitForVisibilityOfElement(e);
			js.executeScript("arguments[0].value='"+text+"'", e);
		}else {
			js.executeScript("arguments[0].value='"+text+"'", e);
		}
	}
	
	protected static String getText(WebElement e, boolean isWaitReq) {
		if(isWaitReq) {
			waitForVisibilityOfElement(e);
			return e.getText();
		}
		return e.getText();	
	}
	
	protected static boolean isElementSelected(WebElement e,boolean isWaitReq) {
		if(isWaitReq) {
			return e.isSelected();
		}
		return e.isSelected();
	}
	
	protected void switchToWindow(String title) {
		Set<String> allWindows=driver.getWindowHandles();
		for(String windowID:allWindows){
			driver.switchTo().window(windowID);
			if(title.equals(driver.getTitle())) {
				break;
			}
		}
	}
	
	protected void switchToMainWindow() {
		driver.switchTo().window(mainWindowHandleId);
	}
	
	protected void closeAllTabExceptMainWindow() {
		Set<String>allWindow=driver.getWindowHandles();
		for(String windowId:allWindow){
			driver.switchTo().window(windowId);
			if(!windowId.equals(mainWindowHandleId)) {
				driver.close();
			}
		}
		switchToMainWindow();
	}
	
	protected void closeReqWindow(String title) {
		Set<String>allWindow=driver.getWindowHandles();
		for(String windowId:allWindow){
			driver.switchTo().window(windowId);
			if(!windowId.equals(mainWindowHandleId)) {
				driver.close();
			}
		}
		switchToMainWindow();
	}
	
	protected String getAttribute(String locatorType,String locatorValue,boolean isWaitReq,String attribute) {
		WebElement e=getElement(locatorType, locatorValue, isWaitReq);
		return e.getAttribute(attribute);
	}
	
	protected void refreshPage() {
		driver.navigate().refresh();
		
	}
	
	protected boolean isElementDisplayed(String locatorType, String locatorValue, int timeout) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			WebElement e=getElement(locatorType,locatorValue , false);
			wait.until(ExpectedConditions.visibilityOf(e));
			return e.isDisplayed();
		}catch(NoSuchElementException | TimeoutException te) {
			return false;
		}
		finally {
			
		}
	}
	
	public static void quitBrowser() {
		driver.quit();
	}
	
	protected static String getAtrributeValue(WebElement e,String value) {
		return e.getAttribute(value);
	}
}
