package page;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import baseshree.ControlActions;

public class CreateQuetionPage extends ControlActions{
	
	@FindBy(xpath="//label[text()='CODE']")
	private WebElement codeQuestionElement;
	
	@FindBy(xpath="//div[@class='code-mcq-container']//label[text()='MCQ']")
	private WebElement McqQuestionElement;
	
	@FindBy(xpath="//label[text()='Save']")
	private WebElement saveButton;
	
	@FindBy(xpath="//div[@id='root']/div[2]//div[@role='alert']/div[2]")
	private WebElement popUpElement;
	
	@FindBy(xpath="//button[@class='Toastify__close-button Toastify__close-button--light']")
	private WebElement popUpClose;
	
	@FindBy(xpath="//input[@placeholder='Your question title']")
	private WebElement quetionTitleElement;
	
	@FindBy(xpath="//input[@placeholder='Score']")
	private WebElement scoreTextElement;
	
	@FindBy(xpath="//div[@class='add-problem-input-container']//div[@class='se-container']/div[4]//p")
	private WebElement problemElement;
	
	@FindBy(xpath="//div[@tabindex='1']//div[@class='se-wrapper-inner se-wrapper-wysiwyg sun-editor-editable']/p")
	private WebElement option1;
	
	@FindBy(xpath="//div[@tabindex='2']//div[@class='se-wrapper-inner se-wrapper-wysiwyg sun-editor-editable']/p")
	private WebElement option2;
	
	@FindBy(xpath="//div[@tabindex='1']//div[@class='check-box']")
	private WebElement checkBox1;
	
	@FindBy(xpath="//div[@tabindex='2']//div[@class='check-box']")
	private WebElement checkBox2;
	
	@FindBy(xpath="//input[@placeholder='Search skill here...']")
	private WebElement skillsSearchBox;
	
	@FindBy(xpath="//input[@placeholder='Search topic here...']")
	private WebElement topicSearchBox;
	
	public CreateQuetionPage(){
		PageFactory.initElements(driver,this);
	}
	
	public void clickOnCodeOption(){
		ControlActions.clickOnElement(codeQuestionElement,true,false);
	}
	
	public void clickOnMcqOption(){
		ControlActions.clickOnElement(McqQuestionElement,true,false);
	}
	
	public void clickOnSaveButton(){
		ControlActions.clickOnElement(saveButton, true,true);
	}
	
	public String getPopUpTextAndClose() {
		ControlActions.waitForVisibilityOfElement(popUpElement);
		String text=popUpElement.getText();
		try {
			closePopUp();
		}catch(StaleElementReferenceException seExceptions) {
			WebElement popUpClose= ControlActions.getElement
					("xpath", "//button[@class='Toastify__close-button Toastify__close-button--light']", true);
			clickUsingJavaScriptExecutor(popUpClose);
		}
		return text;
	}
	
	public void closePopUp(){
		clickUsingJavaScriptExecutor(popUpClose);
	}
	
	public void enterTitle(String title) {
		enterText(quetionTitleElement,title,false,false);
	}
	
	public void enterScore(String score) {
		enterText(scoreTextElement,score,true,false);
		//enterTextUsingJavascriptExecutor(scoreTextElement,score,false);
	}
	
	public void enterProblemQuestion(String question){
		enterText(problemElement,question,false,false);
	}
	
	public void enterTextOptions(String option1Text,String option2Text) {
		enterText(option1,option1Text,true,false);
		enterText(option2,option2Text,true,false);
	}
	
	public void clickOnCorrectAnswer(int option){
		if(option==1) {
			clickOnElement(checkBox1,false,false);
		}else{
			clickOnElement(checkBox1,false,false);
		}
	}
	
	public void selectQuestionLevel(DifficultyLevel status){
		WebElement level=getElement("xpath"," //div[@class='difficulty-level-container']//button[text()='"+status.getText+"']",false);
		clickOnElement(level,false,false);
	}
	
	public enum DifficultyLevel {
		EASY("Easy"),
		MEDIUM("Medium"),
		HARD("Hard");
		
		private final String getText;
		
		private DifficultyLevel(String Text) {
			getText=Text;
		}
		
		public String getDifficultyLevel() {
			return getText;
		}
	}
	
	public void enterSkillsAndSelect(String skill) throws InterruptedException {
		enterText(skillsSearchBox,skill,false,false);
		Thread.sleep(1000);
		selectSkills(skill);
	}
	
	public void enterTopic(String topic){
		enterText(topicSearchBox,topic,false,false);
	}
	
	public void selectSkills(String skill){
		WebElement selectSkill=getElement("xpath","//div[@class='skill-item']/span[text()='"+skill+"']",true);
		selectSkill.click();
	}
	
	public void selectTopic(String topic) throws InterruptedException{
		WebElement selectTopic=getElement("xpath","//div[@class='add-topics-list-item']//p[text()='"+topic+"']",true);
		//Thread.sleep(1000);
		selectTopic.click();
	}
	
	public String getScoreFieldValue() {
		return ControlActions.getAtrributeValue(scoreTextElement,"value");
	}
}
