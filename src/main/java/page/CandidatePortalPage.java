package page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import baseshree.ControlActions;

public class CandidatePortalPage extends ControlActions{
	
	public CandidatePortalPage() {
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//div[@class='policy']//input")
	private WebElement agreeTermsAndCondcheckbox;
	
	@FindBy(xpath="//button[@id='startButton']")
	private WebElement startTestBTN;
	
	@FindBy(xpath="//div[@class='error-popup-title']/span")
	private WebElement errorPopUp;
	
	@FindBy(xpath="//div[@class='user-test-2-content-popup']//div[@class='body']//span[text()='Close']")
	private WebElement errorPopUpClose;
	
	public void clickOnTermsandCondCheckbox() {
		clickOnElement(agreeTermsAndCondcheckbox, true, false);
	}
	
	public void clickOnStartTest() {
		clickOnElement(startTestBTN, false, false);
	}
	
	public String getTextFromErrorPopUpAndClose() {
		String text=ControlActions.getText(errorPopUp, true);
		closeErrorPopUp();
		return text;
	}
	
	public void closeErrorPopUp() {
		clickOnElement(errorPopUpClose,false, false);
	}
	
	public AssessmentPage switchBackToAssessmentWindow() {
		switchToMainWindow();
		return new AssessmentPage();
	}
	
	public void refreshCandidatePortalPage() {
		refreshPage();
	}
	
	public boolean isPopupMessageDisplayed() {
		return isElementDisplayed(errorPopUp, 5);
	}
}
