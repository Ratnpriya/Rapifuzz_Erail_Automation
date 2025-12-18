package tests;

import org.testng.annotations.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.OrangeHRMLoginPage;
import utils.ExcelUtilManager;
import utils.ExtentUtilManager;
import com.aventstack.extentreports.*;

public class OrangeHRMLoginTest {
    WebDriver driver;
    ExtentReports extent;
    ExtentTest test;

    @BeforeTest
    public void setupReport(){
        extent = ExtentUtilManager.getReportInstance();
    }

    @BeforeMethod
    public void setup(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void loginTest() throws Exception {
        test = extent.createTest("OrangeHRM Login Test");
        String path = "loginData.xlsx";
        String[][] data = ExcelUtilManager.readLoginData(path, "Sheet1");

        for (int i=1; i<data.length; i++) {
            String user = data[i][0];
            String pass = data[i][1];
            String expected = data[i][2];

            driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
            OrangeHRMLoginPage loginPage = new OrangeHRMLoginPage(driver);
            loginPage.login(user, pass);

            if (expected.equalsIgnoreCase("dashboard")) {
                String url = loginPage.waitForUrlContains("dashboard");
                if (url.contains("dashboard")) {
                    test.pass("Valid login successful for user: "+user+" and password: "+pass);
                    loginPage.logout();
                } else {
                    test.fail("Valid login failed for user: "+user+" | URL: "+ url);
                }
            } else if (expected.equalsIgnoreCase("auth/login")) {
                String error = loginPage.waitForErrorMessage();
                if (!error.isEmpty()) {
                    test.pass("Invalid login correctly displayed error for user: "+user+" and password: "+pass+" | Error: "+error);
                } else {
                    test.fail("Invalid login did not display error for user: "+user+ " and password: "+pass);
                }
            } else {
                test.warning("Invalid expected value in Excel: "+ expected);
            }
        }
    }

    @AfterMethod
    public void teardown(){
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterTest
    public void endReport(){
        extent.flush();
    }


}
