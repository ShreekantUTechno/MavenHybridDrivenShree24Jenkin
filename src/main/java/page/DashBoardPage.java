package page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import baseshree.ControlActions;

public class DashBoardPage extends ControlActions{
	
	@FindBy(xpath="//span[contains(text(),'Dashboard')]")
	private WebElement dashBoardMenuElement;
	
	@FindBy(xpath="//span[contains(text(),'Total Assessments')]/b")
	private WebElement totalAssessmentCount;
	
	@FindBy(xpath="//span[contains(text(),'Recent Assessments (')]")
	private WebElement recentAssessmentTab;
	
	public DashBoardPage(){
		PageFactory.initElements(driver,this);
	}
	
	public boolean isDashBoardDisplayed() {
		waitForVisibilityOfElement(dashBoardMenuElement);
		return dashBoardMenuElement.isDisplayed();
	}
	
	public void navigateToMenu(MenuOptions options) {
		WebElement cardOptions=getElement("xpath","//span[text()='"+options.getOptions()+"']",true);
		cardOptions.click();		
	}
	
	public int getCountOfTotalAssessmentTab(){
		waitForVisibilityOfElement(totalAssessmentCount);
		return Integer.parseInt(totalAssessmentCount.getText());
	}
	
	public int getCountOfRecentAssessmentTab(){
		return Integer.parseInt(recentAssessmentTab.getText().split("\\(")[1].replace(")", ""));
	}
	
	public enum MenuOptions{
		DASHBOARD("Dashboard"),
		ASSESSMENTS("Assessments"),
		LIBRARY("Library"),
		CANDIDATES("Candidates"),
		INTERVIEWS("Interviews");
		
		private final String menuText;
		
		private MenuOptions(String menuText) {
			this.menuText=menuText;
		}
		
		String getOptions() {
			return menuText;
		}
	}
}
