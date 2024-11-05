package page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import baseshree.ControlActions;

public class LibraryPage extends ControlActions{
	@FindBy(xpath="//p[text()='Create Question']")
	private WebElement createQuetion;
	
	@FindBy(xpath="//div[@class='library1-container']/div[1]/div[1]/label")
	private WebElement FirstQuestion;
	
	@FindBy(xpath="//span[@data-tip='EliteQA Library']/div/*[name()='svg']")
	private WebElement EliteQALibraryCheckB;
	
	@FindBy(xpath="//div[@class='libray-name-header']")
	private WebElement hire360Library;
	
	@FindBy(xpath="//div[@class='loader']")
	private WebElement loader;
	
	private String locatorSkill="//span[@data-tip='%s']/div";
	private String locatorDiffiLev="//div[@class='left-side']/span[%s]/div";
	private String locatorQType="//div[@class='left-side']/span[%s]/div";
	
	public LibraryPage(){
		PageFactory.initElements(driver, this);
	}
	
	public CreateQuetionPage clickOnCreateQuetion() {
		ControlActions.clickOnElement(createQuetion, true,false);
		return new CreateQuetionPage();
	}
	
	public String getFirstQuestionText(){
		return ControlActions.getText(FirstQuestion,true);
	}
	
	public boolean isCheckedOrNotcheckedEliteQALibrary() {
		return isElementDisplayed(EliteQALibraryCheckB, 5);
	}
	
	public int  getTotalNoOfQFromHire360Lib() {
		String count=getText(hire360Library, true);
		return Integer.parseInt(count.split("\\(")[1].replace(")", ""));
	}
	
	public void waitForLoaderVissibility() {
		waitForVisibilityOfElement(loader);
	}
	
	public void waitForLoaderInVissibility() {
		waitForInvisibilityOfElement(loader);
	}
	
	public void selectSkill( SkillOptions skill) {
		String locator=String.format(locatorSkill, skill.getSKill());
		WebElement e=getElement("xpath", locator, false);
		String locator1=locator+"/*[name()='svg']";
		boolean flag=isElementDisplayed("xpath", locator1, 2);
		if(!flag) {	
			clickOnElement(e,true, false);
		}
	}
	
	public void deSelectSkill( SkillOptions skill) {
		String locator=String.format(locatorSkill, skill.getSKill());
		WebElement e=getElement("xpath", locator, false);
		String locator1=locator+"/*[name()='svg']";
		boolean flag=isElementDisplayed("xpath", locator1, 5);
		if(flag) {	
			clickOnElement(e,true, false);
			waitForLoaderInVissibility();
		}
	}
	
	public void selectDifficultyLevel(DifficultyLibrary level) {
		String locator=String.format(locatorDiffiLev, level.getDifficultyLevel());
		WebElement e=getElement("xpath", locator, false);
		String locator1="//div[@class='left-side']/span["+level.getDifficultyLevel()+"]/div"+"/*[name()='svg']";
		boolean flag=isElementDisplayed("xpath", locator1, 5);
		if(!flag) {
			clickOnElement(e,true, true);
			waitForLoaderInVissibility();
		}
	}
	
	public void deSelectDifficultyLevel(DifficultyLibrary level) {
		String locator=String.format(locatorDiffiLev, level.getDifficultyLevel());
		WebElement e=getElement("xpath", locator, true);
		String locator1="//div[@class='left-side']/span["+level.getDifficultyLevel()+"]/div"+"/*[name()='svg']";
		boolean flag=isElementDisplayed("xpath", locator1, 5);
		if(flag) {
			waitForLoaderInVissibility();
			clickOnElement(e,true, false);
		}
	}
	
	public void selectQType(QuestionType type) {
		String locQType=String.format( locatorQType, type.getQType());
		WebElement e=getElement("xpath", locQType,false);
		String locQType1=locQType+"/*[name()='svg']";
		boolean flag=isElementDisplayed("xpath",locQType1 ,5 );
		if(!flag) {
			clickOnElement(e,true, true);
			waitForLoaderInVissibility();
		}
	}
	
	public void deSelectQType(QuestionType type) {
		String locQType=String.format( locatorQType, type.getQType());
		WebElement e=getElement("xpath", locQType,false);
		String locQType1=locQType+"/*[name()='svg']";
		boolean flag=isElementDisplayed("xpath",locQType1 ,5 );
		if(flag) {
			clickOnElement(e,true, true);
			waitForLoaderInVissibility();
		}	
	}
	
	public enum SkillOptions{
		PYTHON("python"),JAVA("java"),MONGODB("mongodb"),POSTGRE("postgre"),REACTNATIVE("react native");
		private final String text;
		private SkillOptions(String text){
			this.text=text;
		}
		public String getSKill() {
			return text;
		}
	}
	
	public enum DifficultyLibrary{
		EASYQ("7"),MEDIUMQ("8"),HARDQ("9"),ALLDIFFICULTYQ("6");
		private final String text;
		private DifficultyLibrary(String text){
			this.text=text;
		}
		public String getDifficultyLevel() {
			return text;
		}
	}
	
	public enum QuestionType{
		ALLQTYPE("10"),MCQ("11"),PROGRAMMING("12");
		private final String text;
		private QuestionType(String text){
			this.text=text;
		}
		public String getQType() {
			return text;
		}
	}
}
