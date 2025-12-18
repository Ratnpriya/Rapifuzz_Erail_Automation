package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
public class ErailHomePage {
    WebDriver driver;

    @FindBy(id="txtStationFrom")
    WebElement fromField;

    @FindBy(xpath="//div[contains(@class,'autocomplete')]/div[@title]")
    List<WebElement> stationDropdown;

    @FindBy(xpath="//td[@id=\"tdDateFromTo\"]/input")
    WebElement dateField;
    
    String stationLocatorWithoutTitle= "//div[contains(@class,'autocomplete')]/div";

    public ErailHomePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void enterFromStation(String code) {
        fromField.click();
        fromField.clear();
        fromField.sendKeys(code);
    }

    // wait for dropdown
    private List<WebElement> getDropdownElements() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfAllElements(stationDropdown));
        return stationDropdown;
    }

    //return the station names
    public List<String> getDropdownStationNames() { 
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); 
    	wait.until(ExpectedConditions.presenceOfElementLocated( 
    			By.xpath(stationLocatorWithoutTitle))); 
    	List<String> names = new ArrayList<>(); 
    	for (WebElement el : stationDropdown) { 
    		String title = el.getAttribute("title"); 
    		if (title != null && !title.trim().isEmpty()) {
    			names.add(title.trim()); } 
    		else { names.add(el.getText().trim()); 
    		} 
    	} 
    	return names; }

    // Select station and return its name
    public String selectStationAt(int index) {
    	List<WebElement> elements = getDropdownElements();
        if (elements.size() > index) {
            WebElement el = elements.get(index);
            String name = el.getAttribute("title");
            el.click();
            return (name != null && !name.trim().isEmpty()) ? name.trim() : el.getText().trim(); } 
        else { 
        	throw new RuntimeException("Not enough stations in dropdown. Found: " + elements.size()); 
        	} 
        }
    
    // select date 30 days ahead from current date
    public String selectDate30DaysAheadFromToday(){
        dateField.click();
        java.time.LocalDate futureDate = java.time.LocalDate.now().plusDays(30);
        String foramttedDate = futureDate.format(java.time.format.DateTimeFormatter.ofPattern("dd-MMM-yy"));
        dateField.sendKeys(foramttedDate);
        return foramttedDate;
    }


}
