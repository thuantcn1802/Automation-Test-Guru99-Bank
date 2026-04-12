package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import java.time.Duration;

public class LoginTest extends BaseTest {

    // --- NHÓM 1: HAPPY PATH (CHẠY ĐÚNG) ---

    @Test(priority = 1, description = "TC01: Đăng nhập thành công với tài khoản hợp lệ")
    public void testLoginSuccessfully() {
        LoginPage loginPage = new LoginPage(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        loginPage.login("mngr658369", "ytUdame");

        try {
            wait.until(ExpectedConditions.alertIsPresent());
            String alertText = driver.switchTo().alert().getText();
            driver.switchTo().alert().accept();
            Assert.fail("Login thất bại ngoài ý muốn: " + alertText);
        } catch (Exception e) {
            try {
                wait.until(ExpectedConditions.titleIs("Guru99 Bank Manager HomePage"));
            } catch (Exception timeout) {
                // Vượt quảng cáo Google Interstitial
                driver.get("https://demo.guru99.com/V4/manager/Managerhomepage.php");
            }
        }
        Assert.assertEquals(driver.getTitle(), "Guru99 Bank Manager HomePage");
        System.out.println("PASSED: TC01 - Login thành công.");
    }

    // --- NHÓM 2: NEGATIVE PATH (CHẠY SAI / LỖI) ---

    @Test(priority = 2, description = "TC02: Đăng nhập thất bại khi sai Password")
    public void testLoginWithWrongPassword() {
        driver.get("https://demo.guru99.com/V4/");
        LoginPage loginPage = new LoginPage(driver);

        loginPage.login("mngr658369", "wrong_pass");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.alertIsPresent());
        String alertText = driver.switchTo().alert().getText();

        Assert.assertEquals(alertText, "User or Password is not valid");

        try {
            Thread.sleep(3000); // Dừng 3 giây để quan sát Alert
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.switchTo().alert().accept();
        System.out.println("PASSED: TC02 - Báo lỗi đúng khi sai Password.");
    }

    @Test(priority = 3, description = "TC03: Đăng nhập thất bại khi sai UserID")
    public void testLoginWithWrongUserID() {
        driver.get("https://demo.guru99.com/V4/");
        LoginPage loginPage = new LoginPage(driver);

        loginPage.login("invalid_user", "ytUdame");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.alertIsPresent());
        String alertText = driver.switchTo().alert().getText();

        Assert.assertEquals(alertText, "User or Password is not valid");
        driver.switchTo().alert().accept();
        System.out.println("PASSED: TC03 - Báo lỗi đúng khi sai UserID.");
    }

    @Test(priority = 4, description = "TC04: Để trống cả UserID và Password")
    public void testLoginWithEmptyFields() {
        driver.get("https://demo.guru99.com/V4/");
        LoginPage loginPage = new LoginPage(driver);

        loginPage.login("", ""); // Nhấn Login luôn không nhập gì

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.alertIsPresent());
        String alertText = driver.switchTo().alert().getText();

        // Guru99 bắn alert chung cho thông tin trống hoặc sai
        Assert.assertEquals(alertText, "User or Password is not valid");
        driver.switchTo().alert().accept();
        System.out.println("PASSED: TC04 - Xử lý tốt khi để trống dữ liệu.");
    }

    // --- NHÓM 3: GIAO DIỆN & TÍNH NĂNG PHỤ ---

    @Test(priority = 5, description = "TC05: Kiểm tra tính năng Reset xóa sạch dữ liệu")
    public void testResetButton() {
        driver.get("https://demo.guru99.com/V4/");
        LoginPage loginPage = new LoginPage(driver);

        driver.findElement(By.name("uid")).sendKeys("ClearMe");
        driver.findElement(By.name("password")).sendKeys("Secret123");

        loginPage.clickReset();

        Assert.assertEquals(loginPage.getUsernameValue(), "");
        // Kiểm tra thêm ô password cũng phải trống
        String passVal = driver.findElement(By.name("password")).getAttribute("value");
        Assert.assertEquals(passVal, "");
        System.out.println("PASSED: TC05 - Nút Reset xóa sạch form.");
    }

    @Test(priority = 6, description = "TC06: Kiểm tra tiêu đề trang Login")
    public void testLoginPageTitle() {
        driver.get("https://demo.guru99.com/V4/");
        Assert.assertEquals(driver.getTitle(), "Guru99 Bank Home Page");
        System.out.println("PASSED: TC06 - Tiêu đề trang chủ đúng.");
    }
}