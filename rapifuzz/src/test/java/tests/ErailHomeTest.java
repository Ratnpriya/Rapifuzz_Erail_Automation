package tests;

import org.testng.annotations.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.ErailHomePage;
import utils.ExcelUtilManager;
import utils.ExtentUtilManager;
import com.aventstack.extentreports.*;

import java.util.*;

public class ErailHomeTest {
    WebDriver driver;
    ExtentReports extent;
    ExtentTest test;

    @BeforeTest
    public void setupReport(){
        extent = ExtentUtilManager.getReportInstance();
    }

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://erail.in/");
    }

    @Test
    public void verifyStations() throws Exception {
        test = extent.createTest("Erail Station Dropdown Test");
        ErailHomePage erail = new ErailHomePage(driver);

        // Enter station code in From field
        erail.enterFromStation("DEL");
        
        // Select 4th stations using indexing
        String fourthStation = erail.selectStationAt(3);
        test.info("4th Station Selected: " + fourthStation);
        test.pass("4th station selected successfully.");

        // get the station names
        List<String> dropdownStations = erail.getDropdownStationNames();

        // write into excel
        ExcelUtilManager.writeStationsNamesIntoExcel("actualStations.xlsx", "Sheet1", dropdownStations);

        // read expected station names and compare
        List<String> expectedStations = ExcelUtilManager.readStationNamesFromExcel("expectedStations.xlsx", "Sheet1");
        if (dropdownStations.containsAll(expectedStations)) {
            test.pass("Dropdown stations matches expected stations list");
        } else {
            test.fail("Dropdown stations do not match expected list");
            test.info("Expected: " + expectedStations);
            test.info("Actual: " + dropdownStations);
        }
        String selectedDate = erail.selectDate30DaysAheadFromToday();
        //erail.selectDate30DaysAheadFromToday();
        test.info("Date selected from calendar is: " + selectedDate);
        test.pass("Date successfully selected 30 days from current date.");
    }
    
    @AfterMethod
    public void teardown(){
        driver.quit();
    }

    @AfterTest
    public void endReport(){
        extent.flush();
    }


}
