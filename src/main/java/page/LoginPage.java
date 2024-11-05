package page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import baseshree.ControlActions;

public class LoginPage extends ControlActions{
	
	@FindBy(xpath="//input[@placeholder='Enter email']")
	private WebElement emailIdfield;
	
	@FindBy(xpath="//input[@placeholder='Enter password']")
	private WebElement passwordField;
	
	@FindBy(xpath=" //button[contains(text(),'Login')] ")
	private WebElement loginButton;
	
	@FindBy(xpath="//div[@id='root']/div[2]//div[@role='alert'][1]/div")
	private WebElement popUpElement;
	
	public LoginPage(){
		PageFactory.initElements(driver, this);
	}
	
	public boolean isEmailFieldDisplayed(){
		waitForVisibilityOfElement(emailIdfield);
		return emailIdfield.isDisplayed();
	}
	
	public boolean ispasswordFieldDisplayed(){
		return passwordField.isDisplayed();
	}
	
	public boolean isloginButtonDisplayed(){
		return loginButton.isDisplayed();
	}
	
	public void enterEmail(String email) {
		waitForVisibilityOfElement(emailIdfield);
		emailIdfield.sendKeys(email);
	}
	
	public void enterPassword(String password) {
		passwordField.sendKeys(password);
	}
	
	public void clickOnLoginButton() {
		loginButton.click();
	}
	
	public void loginWithValidCred(String email,String password) {
		enterEmail(email);
		enterPassword(password);
		clickOnLoginButton();
	}
	
	public String getPopUpText(){
		waitForVisibilityOfElement(popUpElement);
		return popUpElement.getText();
	}
}
