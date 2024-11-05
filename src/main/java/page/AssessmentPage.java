package page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import baseshree.ControlActions;

public class AssessmentPage extends ControlActions{
	@FindBy(xpath="//div[@class='MuiListItemText-root css-1tsvksn']/span[contains(text(),'published')]")
	private WebElement publishedElement;
	
	@FindBy(xpath="//div[@class='MuiListItemText-root css-1tsvksn']/span[contains(text(),'completed')]")
	private WebElement completedElement;
	
	@FindBy(xpath="//div[@class='MuiListItemText-root css-1tsvksn']/span[contains(text(),'draft')]")
	private WebElement draftElement;
	
	@FindBy(xpath="//label[contains(text(),'Assessments ')]")
	private WebElement assessmentTab;
	
	@FindBy(xpath="//span[text()='Take screen snapshots on tab switch']/following-sibling::label")
	private WebElement webCamSlider;
	
	@FindBy(xpath = "//div[@class='Toastify__toast-body']/div[2]")
	private WebElement popUp;
	
	@FindBy(xpath="//button[@class='Toastify__close-button Toastify__close-button--light']")
	private WebElement popUpCloseButton;
	
	
	@FindBy(xpath="//button[@class='test-preview-button']")
	private WebElement testReviewBtn;

	private String checkboxSVG="//span[contains(text(),'%s')]/*[name()='svg']";
	private String webCamSliderString="//span[text()='%s']/following-sibling::label";
	
	public AssessmentPage(){
		PageFactory.initElements(driver,this);
	}
	
	public int getCountOfpublishedAssessment(){
		waitForVisibilityOfElement(publishedElement);
		return Integer.parseInt(publishedElement.getText().split("\\(")[1].replace(")", ""));
	}
	
	public int getCountOfCompltedAssessment(){
		return Integer.parseInt(completedElement.getText().split("\\(")[1].replace(")", ""));
	}
	
	public int getCountOfDraftAssessment(){
		return Integer.parseInt(draftElement.getText().split("\\(")[1].replace(")", ""));
	}
	
	public int getCountOfAssessmentTab(){
		return Integer.parseInt(assessmentTab.getText().split("\\(")[1].replace(")", ""));
	}
	
	public void clickOnTestReqTest(String testText) {
		WebElement test=getElement("xpath", "//div[@class='dashboard-card-container']//span[text()='"+testText+"']", true);
		ControlActions.clickUsingJavaScriptExecutor(test);
	}
	
	public enum ProctoringSettings {
		SHUFFLEQUESTIONS("Shuffle Questions for each candidate"),
		WEBCAMSNAPSHOTS("Take Snapshots via webcam every 30 second"),
		SCREENSHOTTABSWITCH("Take screen snapshots on tab switch"),
		FULLSCREEN("Turn on fullscreen while test"),
		TURNOFFCOPYPASTE("Turn Off copy paste from external sources"),
		LOGOUTONLEAVINGINTERFAcE("Logout on leaving a test interface");
		
		private final String text;
		
		private ProctoringSettings(String text){
			this.text=text;
		}
		
		public String getSettingOptions(){
			return text;
		}
	}
	
	public boolean switchToggleON(ProctoringSettings option) {
		String locator=String.format(checkboxSVG, option.getSettingOptions());
		String webCamSliderlocator=String.format(webCamSliderString, option.getSettingOptions());
		String hightAtrriValue=getAttribute("xpath", locator, true, "height");
		WebElement toggle=getElement("xpath", webCamSliderlocator, false);
		if(hightAtrriValue.equals("16")) {
			clickOnElement(toggle,true,false);
			return true;
		}
		return false;
	}
	
	public boolean switchToggleOFF(ProctoringSettings option) {
		String locator=String.format(checkboxSVG, option.getSettingOptions());
		String webCamSliderlocator=String.format(webCamSliderString, option.getSettingOptions());
		String hightAtrriValue=getAttribute("xpath", locator, false, "height");
		WebElement toggle=getElement("xpath", webCamSliderlocator, false);
		if(hightAtrriValue.equals("17")) {
			clickOnElement(toggle,true,false);
			return true;
		}
		return false;
	}
	
	public String getPopUpTextAndClose(){
		String text=ControlActions.getTextUsingJavascript(popUp,true);
		closePopup();
		//ControlActions.clickOnElement(popUpCloseButton, false, false);
		return text;
	}
	
	public void closePopup() {
		try {
			boolean flag = isElementDisplayed(popUp, 30);
			if(flag)
				clickOnElement(popUpCloseButton, false, false);
		}catch(Exception e) {
			System.err.println("WARNING ---- Pop up was not there");
		}
	}

	public void clickOnTestReviewBtn() {
		ControlActions.clickOnElement(testReviewBtn, false, false);
	}
	
	public CandidatePortalPage switchToCandidatePortalWindow() {
		switchToWindow("Excellio Candidate Assesment Portal");
		return new CandidatePortalPage();
	}
	
	public void closeCandidatePortalWindow() {
		closeReqWindow("Excellio Candidate Assesment Portal");
	}
}
