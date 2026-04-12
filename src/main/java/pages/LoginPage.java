package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    WebDriver driver;

    // Locators
    By txtUsername = By.name("uid");
    By txtPassword = By.name("password");
    By btnLogin = By.name("btnLogin");
    By btnReset = By.name("btnReset");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Hành động: Nhập User/Pass và nhấn Login
    public void login(String user, String pass) {
        driver.findElement(txtUsername).sendKeys(user);
        driver.findElement(txtPassword).sendKeys(pass);
        driver.findElement(btnLogin).click();
    }

    // Hành động: Nhấn nút Reset
    public void clickReset() {
        driver.findElement(btnReset).click();
    }

    // Lấy giá trị hiện tại trong ô Username (để kiểm tra Reset)
    public String getUsernameValue() {
        return driver.findElement(txtUsername).getAttribute("value");
    }
}