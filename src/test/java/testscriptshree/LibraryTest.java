package testscriptshree;

import java.io.IOException;
import java.util.Arrays;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import page.CreateQuetionPage;
import page.CreateQuetionPage.DifficultyLevel;
import page.DashBoardPage;
import page.DashBoardPage.MenuOptions;
import page.LibraryPage;
import page.LibraryPage.DifficultyLibrary;
import page.LibraryPage.QuestionType;
import page.LibraryPage.SkillOptions;
import path.Constant;
import utility.ExcelRead;

public class LibraryTest extends TestBase {
	CreateQuetionPage createquetionpage;
	SoftAssert soft;
	
	private void clickOnSaveBtnAndVerifyPoppUpMessage(String expectedText) throws InterruptedException {
		Thread.sleep(1000);
		createquetionpage.clickOnSaveButton();
		soft=new SoftAssert();
		String actualText=createquetionpage.getPopUpTextAndClose();
		/*
		 * if(!expectedText.equals(actualText)) { //System.out.println(actualText+
		 * "      "+expectedText); createquetionpage.closePopUp();
		 * createquetionpage.clickOnSaveButton();
		 * actualText=createquetionpage.getPopUpTextAndClose(); }
		 */
		soft.assertEquals(actualText, expectedText);
	}
	
	@Test
	public void verifyCreateQustionPopUpValidation() throws InterruptedException {
		DashBoardPage dashbord=new DashBoardPage();
		LibraryPage librarypage=new LibraryPage();
		soft=new SoftAssert();
		
		System.out.println("STEP 2 - Click on Library Menu.");
		dashbord.navigateToMenu(MenuOptions.LIBRARY);
		
		System.out.println("STEP 3 - Click on Create Question button.");
		createquetionpage=librarypage.clickOnCreateQuetion();
		
		System.out.println("STEP 4 - Click on MCQ");
		createquetionpage.clickOnMcqOption();
		
		System.out.println("STEP 5 - Click on Save button. and VERIFY - \\\"Enter question title\\\" error message should be displayed.");
		clickOnSaveBtnAndVerifyPoppUpMessage("Enter question title.");
		
		System.out.println("STEP 6 - Add \"Access modifier - Theory\" Title in Question.");
		createquetionpage.enterTitle("Access modifier - Theory");
		
		System.out.println("STEP 7 - Click on Save button and VERIFY - \\\"Enter question score\\\" error message should be displayed.");
		clickOnSaveBtnAndVerifyPoppUpMessage("Enter question score.");
		
		System.out.println("STEP 8 - Add \"1\" in the score value.");
		createquetionpage.enterScore("1");
		
		System.out.println("STEP 9 - Click on Save button and VERIFY - \\\"Enter question.\\\" error message should be displayed.");
		clickOnSaveBtnAndVerifyPoppUpMessage("Enter question.");
		
		System.out.println("STEP 10 - Enter question \"Constructor can be declared as Protected?\"");
		createquetionpage.enterProblemQuestion("Constructor can be declared as Protected?");
		
		System.out.println("STEP 11 - Click on Save button and Verify -\"Atleast one correct answer should be selected.\" error message should be displayed");
		clickOnSaveBtnAndVerifyPoppUpMessage("Atleast one correct answer should be selected.");
		
		System.out.println("STEP 12 - Enter option 1 value as true and option 2 value is false.");
		Thread.sleep(2000);
		createquetionpage.enterTextOptions("true","false");
		
		System.out.println("STEP 13 - Select correct answer by clicking on the first option.");
		createquetionpage.clickOnCorrectAnswer(1);
		
		System.out.println(" STEP 14 - Click on Save button and VERIFY - \"Difficulty level is mandatory.\" error message should be displayed.");
		clickOnSaveBtnAndVerifyPoppUpMessage("Difficulty level is mandatory.");
		
		System.out.println("STEP 15 - Select difficulty level as Easy.");
		createquetionpage.selectQuestionLevel(DifficultyLevel.EASY);
		
		System.out.println("STEP 16 - Click on Save button and VERIFY - \"Atleast one skill is mandatory.\" error message should be displayed.");
		clickOnSaveBtnAndVerifyPoppUpMessage("Atleast one skill is mandatory.");
		
		System.out.println("STEP 17 - Select skill \"Java\" from search box.");
		createquetionpage.enterSkillsAndSelect("java");
		
		System.out.println("STEP 18- Select topic \"object-oriented programming\".");
		createquetionpage.selectTopic("object-oriented programming");
		
		soft.assertAll();
	}
	
	@Test 
	public void verifyCreateQuestion() throws InterruptedException {
		DashBoardPage dashbord=new DashBoardPage();
		LibraryPage librarypage=new LibraryPage();
		
		System.out.println("STEP 2 - Click on Library Menu.");
		dashbord.navigateToMenu(MenuOptions.LIBRARY);
		
		System.out.println("STEP 3 - Click on Create Question button.");
		createquetionpage=librarypage.clickOnCreateQuetion();
		
		System.out.println("STEP 4 - Click on MCQ");
		createquetionpage.clickOnMcqOption();
		
		System.out.println("STEP 5 - Add \"Access modifier - Theory\" Title in Question.");
		createquetionpage.enterTitle("Access modifier - Theory");
		
		System.out.println("STEP 6 - Add \"1\" in the score value.");
		createquetionpage.enterScore("1");
		
		System.out.println("STEP 7 - Enter question \"Constructor can be declared as Protected?\"");
		createquetionpage.enterProblemQuestion("Constructor can be declared as Protected?");
		
		System.out.println("STEP 8 - Enter option 1 value as true and option 2 value is false.");
		createquetionpage.enterTextOptions("true","false");
		
		System.out.println("STEP 9 - Select correct answer by clicking on the first option.");
		createquetionpage.clickOnCorrectAnswer(1);
		
		System.out.println("STEP 10 - Select difficulty level as Easy.");
		createquetionpage.selectQuestionLevel(DifficultyLevel.EASY);
		
		System.out.println("STEP 11 - Select skill \"Java\" from search box.");
		createquetionpage.enterSkillsAndSelect("java");
		
		System.out.println("STEP 12 - Select topic \"object-oriented programming\".");
		createquetionpage.selectTopic("object-oriented programming");
		
		System.out.println("STEP 13 - Click on Save button. and VERIFY - \"Question created successfully.\" success message should be displayed."); 
		clickOnSaveBtnAndVerifyPoppUpMessage("Question created successfully.");
		
		System.out.println("STEP - verify is questiobn Added in My library or not ");
		String actual=librarypage.getFirstQuestionText();
		Assert.assertEquals(actual, "Access modifier - Theory");
		soft.assertAll();
	}
	
	@DataProvider
	public Object[][] getDataQreateQuestion() throws IOException{
		Object [][]data=ExcelRead.getDataFromExcel(Constant.EXCELPATH,Constant.CREATEQSHEET);
		Arrays.deepToString(data);
		return data;
	}
	
	@Test (dataProvider = "getDataQreateQuestion")
	public void verifyCreateQuestionUsingExcelData(String title,String problem,String score,
			String difficultyLevel,String option1,String option2,String correctAns,String skill,String skillTopic,String result) throws InterruptedException {
		DashBoardPage dashbord=new DashBoardPage();
		LibraryPage librarypage=new LibraryPage();
		
		System.out.println("STEP 2 - Click on Library Menu.");
		dashbord.navigateToMenu(MenuOptions.LIBRARY);
		
		System.out.println("STEP 3 - Click on Create Question button.");
		createquetionpage=librarypage.clickOnCreateQuetion();
		
		System.out.println("STEP 4 - Click on MCQ");
		createquetionpage.clickOnMcqOption();
		
		System.out.println("STEP 5 - Add Question in Title.");
		createquetionpage.enterTitle(title);
		
		//System.out.println(score);
		System.out.println("STEP 6 - Add score value.");
		if(score.length()>0) {
			if(score.split("\\.")[1].equals("0")) {
				createquetionpage.enterScore(score.split("\\.")[0]); // . has special meaning in java,so to ignore that special meaning we used \\
			}else {
				createquetionpage.enterScore(score);
				String actualScoreVale=createquetionpage.getScoreFieldValue();
				String expectedScoreVaue=score.replace(".", "");
				soft.assertEquals(actualScoreVale,expectedScoreVaue);
			}
		}
		
		System.out.println("STEP 7 - Enter question");
		createquetionpage.enterProblemQuestion(problem);
		
		System.out.println("STEP 8 - Enter options");
		createquetionpage.enterTextOptions(option1,option2);
		
		//System.out.println(correctAns);
		System.out.println("STEP 9 - Select correct answer");
		if(correctAns.length() >0) {
			createquetionpage.clickOnCorrectAnswer(Integer.parseInt(correctAns.split("\\.")[0]));
		}
		
		System.out.println("STEP 10 - Select difficulty level");
		if(difficultyLevel.equals("EASY")) {
			createquetionpage.selectQuestionLevel(DifficultyLevel.EASY);
		}else if(difficultyLevel.equals("MEDIUM")) {
			createquetionpage.selectQuestionLevel(DifficultyLevel.MEDIUM);
		}else if(difficultyLevel.equals("HARD")) {
			createquetionpage.selectQuestionLevel(DifficultyLevel.HARD);
		}
		
		System.out.println("STEP 11 - Select skill from search box.");
		createquetionpage.enterSkillsAndSelect(skill.toLowerCase());
		
		System.out.println("STEP 12 - Select topic ");
		if(skillTopic.length()>0) {
			createquetionpage.selectTopic(skillTopic);
		}
		
		System.out.println("STEP 13 - Click on Save button. and VERIFY - message should be displayed."); 
		clickOnSaveBtnAndVerifyPoppUpMessage(result);
		
		/*
		 * System.out.println("STEP - verify is question button Added in My library or not ");
		 * String actual=librarypage.getFirstQuestionText(); Assert.assertEquals(actual,
		 * "Access modifier - Theory"); soft.assertAll();
		 */
	}
	
	@Test
	public void verifyTotalQueInSkills() throws InterruptedException {
		System.out.println("Step 2 :- click on library button ");
		dashboardpage.navigateToMenu(MenuOptions.LIBRARY);
		
		System.out.println("Verify - 'Hire360 Library' is by default selected");
		LibraryPage librarypage=new LibraryPage();
		boolean flag=librarypage.isCheckedOrNotcheckedEliteQALibrary();
		soft=new SoftAssert();
		soft.assertTrue(flag);
		
		System.out.println("Step 3 - get the total number of questions available in Hire360library");
		int totalQuesInHire360=librarypage.getTotalNoOfQFromHire360Lib();
		soft.assertEquals(totalQuesInHire360,349);	
		
		System.out.println("Step 4 - select 'Python' in skill checkbox");
		librarypage.selectSkill(SkillOptions.PYTHON);
		
		System.out.println("Step 5 - get total number of questions in python skill");
		int totalPythonQuestions=librarypage.getTotalNoOfQFromHire360Lib();
	
		System.out.println("Step 6 - select 'Easy' in difficulty checkbox");
		librarypage.selectDifficultyLevel(DifficultyLibrary.EASYQ);
		
		System.out.println("step 7 - get total number of question in easy difficulty ");
		int easyQFromPython=librarypage.getTotalNoOfQFromHire360Lib();
		System.out.println(easyQFromPython);
		
		System.out.println("step 8 - select 'Medium' in difficulty and uncheck the 'Easy' checkbox");
		librarypage.deSelectDifficultyLevel(DifficultyLibrary.EASYQ);
		librarypage.selectDifficultyLevel(DifficultyLibrary.MEDIUMQ);
		
		System.out.println("step 9 - get total number of question in medium difficulty");
		int mediumQFromPython=librarypage.getTotalNoOfQFromHire360Lib();
		
		System.out.println("step 10 - select 'Hard' in difficulty and uncheck the 'Medium' checkbox");
		librarypage.deSelectDifficultyLevel(DifficultyLibrary.MEDIUMQ);
		librarypage.selectDifficultyLevel(DifficultyLibrary.HARDQ);
		
		System.out.println("step 11 - get total number of question in hard difficulty");
		int hardQFromPython=librarypage.getTotalNoOfQFromHire360Lib();
		
		System.out.println("step 12 - uncheck 'Hard' checkbox");
		librarypage.deSelectDifficultyLevel(DifficultyLibrary.HARDQ);
		
		System.out.println("Verify - total number of questions in python skill is same as sum of number of question from difficulties");
		int sumOfAllLevels=easyQFromPython+mediumQFromPython+hardQFromPython;
		soft.assertEquals(totalPythonQuestions, sumOfAllLevels, " The sum is not matched ");
		
		System.out.println("step 13 - select 'MCQ' checkbox in question type");
		librarypage.selectQType(QuestionType.MCQ);
		
		System.out.println("step 14 - get total number of question in type MCQ");
		int mcqQfromPython=librarypage.getTotalNoOfQFromHire360Lib();
		
		System.out.println("step 15 - select 'Programming' and uncheck 'MCQ' in question type");
		librarypage.deSelectQType(QuestionType.MCQ);
		librarypage.selectQType(QuestionType.PROGRAMMING);
	
		System.out.println("step 16 - get total number of question in type Programming");
		int programmingQfromPython=librarypage.getTotalNoOfQFromHire360Lib();
		
		System.out.println("Verify - total number of question in python skill is same as sum of number of question from Question type");
		int totalNoOfQMcqProgram=mcqQfromPython+programmingQfromPython;
		soft.assertEquals(totalNoOfQMcqProgram, totalPythonQuestions);
		soft.assertAll();
	}
}
