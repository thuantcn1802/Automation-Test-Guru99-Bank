package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ManagerHomePage {
    WebDriver driver;

    // Locator cho nút Log out
    @FindBy(linkText = "Log out")
    WebElement lnkLogout;

    // Thêm locator để kiểm tra tiêu đề trang (Manager Home Page) nếu cần
    @FindBy(className = "heading3")
    public WebElement lblWelcomeMsg;

    public ManagerHomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickLogout() {
        try {
            lnkLogout.click();
        } catch (Exception e) {
            // Nếu click bình thường bị che bởi quảng cáo, dùng JS click
            org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", lnkLogout);
        }
    }
}