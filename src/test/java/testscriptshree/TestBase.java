package testscriptshree;

import path.Constant;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import baseshree.ControlActions;
import page.DashBoardPage;
import page.LoginPage;
import utility.PropOperation;

public class TestBase {
	DashBoardPage dashboardpage;
	private String url,emailId,password;
	
	
	 @BeforeSuite 
	 public void loadPropOperation() { 
		PropOperation propoperation=new PropOperation(Constant.PROPERTYFILEPATH);
		String envname=System.getProperty("env").toLowerCase();
		System.out.println(envname);
		
		url=propoperation.getValue(envname+"Url");
		emailId=propoperation.getValue("UserEmail");
		password=propoperation.getValue("Password");
	}
	 

	@BeforeMethod
	public void setup() {
		System.out.println("STEP 1 - Launch Chrome browser and Load URL");
		ControlActions.start(url);
		LoginPage loginpage=new LoginPage();
		loginpage.loginWithValidCred(emailId,password);
		dashboardpage=new DashBoardPage();
		Assert.assertTrue(dashboardpage.isDashBoardDisplayed());
	}
	@AfterMethod
	public void tearDown(ITestResult result) {
		if(result.getStatus()==ITestResult.FAILURE) {
			ControlActions.takeScreenshot(result.getName());
		}
		ControlActions.quitBrowser();
	}
}
