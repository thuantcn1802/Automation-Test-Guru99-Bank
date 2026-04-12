package tests;

import base.BaseTest;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ManagerHomePage;
import java.time.Duration;

public class LogoutTest extends BaseTest {

    @Test(priority = 10, description = "TC10: Đăng xuất hệ thống thành công")
    public void testLogoutSuccessfully() {
        // 1. Đăng nhập
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("mngr658369", "ytUdame");

        // 2. Nhấn Log out
        ManagerHomePage homePage = new ManagerHomePage(driver);
        homePage.clickLogout();

        // 3. Sử dụng WebDriverWait để đợi Alert xuất hiện (Tránh lỗi NoAlertPresentException)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.alertIsPresent());

            String alertMessage = driver.switchTo().alert().getText();
            Assert.assertEquals(alertMessage, "You Have Succesfully Logged Out!!",
                    "Nội dung thông báo Logout không chính xác!");

            // Nhấn OK trên Alert
            driver.switchTo().alert().accept();

        } catch (NoAlertPresentException | org.openqa.selenium.TimeoutException e) {
            Assert.fail("Không tìm thấy Alert xác nhận đăng xuất sau 10 giây!");
        }

        // 4. Kiểm tra trang đích
        // Đợi Title trang chủ xuất hiện để đảm bảo trang đã load xong
        wait.until(ExpectedConditions.titleIs("Guru99 Bank Home Page"));
        Assert.assertEquals(driver.getTitle(), "Guru99 Bank Home Page");

        System.out.println("PASS: Đăng xuất thành công và quay về trang Login.");
    }
}