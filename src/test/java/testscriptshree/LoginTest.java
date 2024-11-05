package testscriptshree;

import java.io.IOException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import baseshree.ControlActions;
import page.DashBoardPage;
import page.LoginPage;
import path.Constant;
import utility.ExcelRead;

public class LoginTest extends TestBase{
	
	@BeforeMethod
	public void setup() {
		ControlActions.start("https://staging.theeliteqa.com/login");
	}
	
	@DataProvider(name="loginTestDataProvider")
	public Object[][] loginTestData() {
		Object [][] data=new Object[4][2];
		data[0][0]="adeshmay24@gmail.com";
		data[0][1]="May@123";
		
		data[1][0]="adityammay24@gmail.com";
		data[1][1]="May@123";
		
		data[2][0]="anshulmay24@gmail.com";
		data[2][1]="May@123";
		
		data[3][0]="geetikamay24@gmail.com";
		data[3][1]="May@123";
		
		return data;
	}
	
	@Test(dataProvider = "loginTestDataProvider" ,enabled=false)
	public void verifyLoginTestValidData(String emailID,String password){
		System.out.println("VERFIY - Login page is visible [Login button is displayed, useremail is enabled, password is enabled]");
		LoginPage loginpage=new LoginPage();
		boolean emailField=loginpage.isEmailFieldDisplayed();
		boolean passwordField=loginpage.ispasswordFieldDisplayed();
		boolean loginButton=loginpage.isloginButtonDisplayed();
		SoftAssert soft=new SoftAssert();
		soft.assertTrue(emailField,"Email id field is not displayed");
		soft.assertTrue(passwordField, "password field is not displayed");
		soft.assertTrue(loginButton, " login button is not displayed");
		soft.assertAll();
		
		System.out.println("STEP 3 - Enter valid username");
		loginpage.enterEmail(emailID);
		
		System.out.println("STEP 4 - Enter valid password");
		loginpage.enterPassword(password);
		
		System.out.println("STEP 5 - Click on Login button");
		loginpage.clickOnLoginButton();
		
		System.out.println("VERIFY - Dashboard page is displayed");
		DashBoardPage dashboardpage=new DashBoardPage();
		soft.assertTrue(dashboardpage.isDashBoardDisplayed(),"DashBoard menu card is not displayed");
	}
	
	@DataProvider(name="loginNegAndPosTestDataProvider1")
	public Object[][] loginTestData1() {
		Object [][] data=new Object[4][3];
		data[0][0]="adeshmay24@gmail.com";
		data[0][1]="May@123";
		data[0][2]=true;
		
		data[1][0]="adityammay24@gmail.com";
		data[1][1]="May@123";
		data[1][2]=true;
		
		data[2][0]="anshulmay24@gmail.com";
		data[2][1]="May@12345";
		data[2][2]=false;
		
		data[3][0]="geetikamay24@gmail.com";
		data[3][1]="may@123";
		data[3][2]=false;
		
		return data;
	}
	
	@DataProvider
	public Object[][] loginTestDataFromExcel() throws IOException {
		Object [][] data=ExcelRead.getDataFromExcel(Constant.EXCELPATH,Constant.LOGINTESTSHEET);
		//System.out.println(Arrays.deepToString(data));
		return data;
	}
	
	@Test(dataProvider = "loginTestDataFromExcel")
	public void verifyLoginTestWithValidAndInvalidData(String emailID,String password,Object objFlag){
		boolean flag=(boolean)objFlag;
		
		System.out.println("VERFIY - Login page is visible [Login button is displayed, useremail is enabled, password is enabled]");
		LoginPage loginpage=new LoginPage();
		boolean emailField=loginpage.isEmailFieldDisplayed();
		boolean passwordField=loginpage.ispasswordFieldDisplayed();
		boolean loginButton=loginpage.isloginButtonDisplayed();
		
		SoftAssert soft=new SoftAssert();
		soft.assertTrue(emailField,"Email id field is not displayed");
		soft.assertTrue(passwordField, "password field is not displayed");
		soft.assertTrue(loginButton, " login button is not displayed");
		soft.assertAll();
		
		System.out.println("STEP 3 - Enter valid username");
		loginpage.enterEmail(emailID);
		
		System.out.println("STEP 4 - Enter valid password");
		loginpage.enterPassword(password);
		
		System.out.println("STEP 5 - Click on Login button");
		loginpage.clickOnLoginButton();
		
		if(flag) {
			System.out.println("VERIFY - Dashboard page is displayed");
			DashBoardPage dashboardpage=new DashBoardPage();
			soft.assertTrue(dashboardpage.isDashBoardDisplayed(),"DashBoard menu card is not displayed");
		}else {
			System.out.println("VERIFY - Popup with the error message "+
					"Authentication failed, please check your username and password." +" should be displayed");
					soft.assertEquals(loginpage.getPopUpText(), "Authentication failed, please check your username and password.","Expected is not matching with Actual");
					
					System.out.println("VERIFY - Login page is still visible.");
					soft.assertTrue(loginpage.isloginButtonDisplayed());
		}
		soft.assertAll();
	}
	
	
	@Test(enabled = false)
	public void verifInvalidAuthentication() {
		LoginPage loginpage=new LoginPage();
		
		System.out.println("STEP 2 - Enter valid username");
		loginpage.enterEmail("shreekantmay24@gmail.com");
		
		System.out.println("STEP 3 - Enter Invalid password");
		loginpage.enterPassword("My@123");
		
		System.out.println("STEP 4 - Click on Login button");
		loginpage.clickOnLoginButton();
		
		System.out.println("VERIFY - Popup with the error message "+
		"Authentication failed, please check your username and password." +" should be displayed");
		SoftAssert soft=new SoftAssert();
		soft.assertEquals(loginpage.getPopUpText(), "Authentication failed, please check your username and password.","Expected is not matching with Actual");
		
		System.out.println("VERIFY - Login page is still visible.");
		soft.assertTrue(loginpage.isloginButtonDisplayed());
		soft.assertAll();
	}
	
	@AfterMethod
	public void tearDown() {
		System.out.println("CLEAN UP - closing all browsers");
		ControlActions.quitBrowser();
	}
}

