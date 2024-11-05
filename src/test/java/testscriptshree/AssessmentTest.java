package testscriptshree;


import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import page.AssessmentPage;
import page.AssessmentPage.ProctoringSettings;
import page.CandidatePortalPage;
import page.DashBoardPage.MenuOptions;

public class AssessmentTest extends TestBase{
	
	@Test
	void verifyAssessmentTab(){
		AssessmentPage assessmentpage=new AssessmentPage();
		System.out.println("STEP 3 - Go to Assessments Tab");
		dashboardpage.navigateToMenu(MenuOptions.ASSESSMENTS); 
		
		System.out.println("STEP 4 - Count number of assessments from Published");
		int countOfPublishedAssessment=assessmentpage.getCountOfpublishedAssessment();
		
		System.out.println("STEP 5 - Count number of assessments from completed");
		int countOfCompletedAssessment=assessmentpage.getCountOfCompltedAssessment();
		
		System.out.println("STEP 6 - Count number of assessments from draft");
		int countOfdraftAssessment=assessmentpage.getCountOfDraftAssessment();
		
		System.out.println("STEP 7 - Count Total number of assessments from draft,completed,published");
		int totalCountOfComPubDraft=countOfPublishedAssessment+countOfCompletedAssessment+countOfdraftAssessment;
		
		System.out.println("STEP 8 - Count Total number of assement from assessment tab");
		int countOfassessmentTab=assessmentpage.getCountOfAssessmentTab();
		
		System.out.println("VERIFY - Total Assessments on assessment page should be equal to sum of published, completed and draft count.");
		SoftAssert soft=new SoftAssert();
		soft.assertEquals(countOfassessmentTab, totalCountOfComPubDraft);
		
		System.out.println("STEP 9 - navigate to dashboard tab");
		dashboardpage.navigateToMenu(MenuOptions.DASHBOARD);
		
		System.out.println("STEP 10 - Find total count from TotalAssessmentTab");
		int totalAssessementTabCount=dashboardpage.getCountOfTotalAssessmentTab();
		
		System.out.println("VERIFY - Total assessments count on dashboard page against Assessments tab from assessmentpage.");
		soft.assertEquals(totalAssessementTabCount, countOfassessmentTab);
		
		System.out.println("STEP 11 - Find total count from RecentAssessmentTab");
		int countOfRecentAssessmentTab=dashboardpage.getCountOfRecentAssessmentTab();
		
		System.out.println("VERIFY - Total assessments count is less than 6, then Recent assessments should match the same "
				+"count, if total assements are more than 6, then recent assessments count should be 6.");
		if(totalAssessementTabCount>=6) {
			soft.assertTrue(countOfRecentAssessmentTab<=6);	
		}else{		
			soft.assertEquals(totalAssessementTabCount,countOfRecentAssessmentTab);
		}
		soft.assertAll();
	}
	
	@Test
	public void verifyWebCamSettings() throws InterruptedException {
		System.out.println("Prerequisite for this test is One test assessment should be present");
		AssessmentPage assessmentpage=new AssessmentPage();
		SoftAssert soft=new SoftAssert();
		System.out.println("STEP2: Click on \"Assessment\" Menu");
		dashboardpage.navigateToMenu(MenuOptions.ASSESSMENTS);
		
		System.out.println("STEP4: Click on tae-assessment");
		assessmentpage.clickOnTestReqTest("tae-assessment");
		
		System.out.println("STEP5: click on Take Snapshots via webcam every 30 second");
		boolean flag=assessmentpage.switchToggleOFF(ProctoringSettings.WEBCAMSNAPSHOTS);
		if(flag) {
			assessmentpage.closePopup();
		}
		flag=assessmentpage.switchToggleON(ProctoringSettings.WEBCAMSNAPSHOTS);
		if(flag) {
			System.out.println("STEP 6 :verify Proctoring setting updated successfully messege");
			String Actual=assessmentpage.getPopUpTextAndClose();
			soft.assertEquals(Actual,"Proctoring setting updated successfully.");
			assessmentpage.closePopup();
		}
		
		System.out.println("STEP 7: click on Test Preview button");
		assessmentpage.clickOnTestReviewBtn();
		
		System.out.println("STEP 8- Switch to Excellio Candidate Assesment Portal");
		CandidatePortalPage candidatePortalPage=assessmentpage.switchToCandidatePortalWindow();
		
		System.out.println("STEP 9- I agree to the Privacy Policy and Terms of Use of EliteQA. button");
		candidatePortalPage.clickOnTermsandCondCheckbox();
		
		System.out.println("STEP 10- Click on Start Test button");
		candidatePortalPage.clickOnStartTest();
		
		System.out.println("VERIFY - 'Can't start test, Please turn on camera messege'");
		String ActualErrorText=candidatePortalPage.getTextFromErrorPopUpAndClose();
		Assert.assertEquals(ActualErrorText, "Can't start test, Please turn on camera");
		
		System.out.println("STEP 11- switch back to main window and turn off the camera setting");
		assessmentpage=candidatePortalPage.switchBackToAssessmentWindow();
		assessmentpage.switchToggleOFF(ProctoringSettings.WEBCAMSNAPSHOTS);
		assessmentpage.closePopup();
		
		System.out.println("STEP - switch back to candidate portal page and refresh the page");
		candidatePortalPage=assessmentpage.switchToCandidatePortalWindow();
		candidatePortalPage.refreshCandidatePortalPage();
		candidatePortalPage.clickOnTermsandCondCheckbox();
		
		System.out.println("STEP - Click on Start Test button");
		candidatePortalPage.clickOnStartTest();
		
		System.out.println("VERIFY - Error popup message is not displayed");
		flag=candidatePortalPage.isPopupMessageDisplayed();
		soft.assertFalse(flag,"Error popup message is displayed");
	}
}
