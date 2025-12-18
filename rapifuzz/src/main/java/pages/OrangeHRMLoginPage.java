package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrangeHRMLoginPage {
    WebDriver driver;
    WebDriverWait wait;

    @FindBy(name="username")
    WebElement username;

    @FindBy(name="password")
    WebElement password;

    @FindBy(xpath="//button[@type='submit']")
    WebElement loginBtn;

    // Error message for invalid login
    @FindBy(xpath="//p[@class='oxd-text oxd-text--p oxd-alert-content-text']")
    WebElement errorMsg;
    
    @FindBy(css = "p.oxd-userdropdown-name")
    WebElement profileMenu;

    @FindBy(xpath = "//a[text()='Logout']")
    WebElement logoutLink;

    public void logout() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(profileMenu)).click();
            wait.until(ExpectedConditions.elementToBeClickable(logoutLink)).click();
        } catch (Exception e) {
            System.out.println("Logout not possible, maybe already on login page.");
        }
    }

    public OrangeHRMLoginPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void login(String user, String pass){
        wait.until(ExpectedConditions.visibilityOf(username));
        username.clear();
        username.sendKeys(user);

        wait.until(ExpectedConditions.visibilityOf(password));
        password.clear();
        password.sendKeys(pass);

        loginBtn.click();
    }

    public String waitForUrlContains(String keyword) {
        wait.until(ExpectedConditions.urlContains(keyword));
        return driver.getCurrentUrl();
    }

    public String waitForErrorMessage() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(errorMsg)).getText();
        } catch (TimeoutException e) {
            return "";
        }
    }


}
