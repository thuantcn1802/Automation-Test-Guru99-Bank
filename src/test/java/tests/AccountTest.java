package tests;

import base.BaseTest;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.NewAccountPage;

public class AccountTest extends BaseTest {
    NewAccountPage accountPage;

    @BeforeMethod
    public void setupAccountTest() throws InterruptedException{
        new LoginPage(driver).login("mngr658369", "ytUdame");
        Thread.sleep(1000);
        // Luôn dùng driver.get để đảm bảo quay về đúng trang New Account sau mỗi bài test
        driver.get("https://demo.guru99.com/V4/manager/addAccount.php");

        // Dọn dẹp sạch sẽ Alert rác bằng vòng lặp
        try {
            while (true) {
                driver.switchTo().alert().accept();
            }
        } catch (NoAlertPresentException e) {
            // Đã hết Alert, chạy tiếp
        }

        accountPage = new NewAccountPage(driver);
    }

    // --- TEST CUSTOMER ID ---
    @Test(priority = 1)
    public void testCustomerIdBlank() throws InterruptedException {
        accountPage.txtCustomerId.sendKeys("");
        accountPage.txtCustomerId.sendKeys(Keys.TAB);
        Thread.sleep(1000);
        Assert.assertEquals(accountPage.lblCustomerIdErr.getText(), "Customer ID is required");
    }

    @Test(priority = 2)
    public void testCustomerIdCharacters() throws InterruptedException {
        accountPage.txtCustomerId.sendKeys("abc");
        accountPage.txtCustomerId.sendKeys(Keys.TAB);
        Thread.sleep(1000);
        Assert.assertEquals(accountPage.lblCustomerIdErr.getText(), "Characters are not allowed");
    }

    @Test(priority = 3)
    public void testCustomerIdSpecialChars() throws InterruptedException {
        accountPage.txtCustomerId.sendKeys("123!");
        accountPage.txtCustomerId.sendKeys(Keys.TAB);
        Thread.sleep(1000);
        Assert.assertEquals(accountPage.lblCustomerIdErr.getText(), "Special characters are not allowed");
    }

    // --- TEST INITIAL DEPOSIT ---
    @Test(priority = 4)
    public void testDepositBlank() throws InterruptedException {
        accountPage.txtCustomerId.sendKeys("66072");
        accountPage.txtDeposit.click();
        accountPage.txtDeposit.sendKeys("1");
        accountPage.txtDeposit.sendKeys(Keys.BACK_SPACE);
        accountPage.txtDeposit.sendKeys(Keys.TAB);
        Thread.sleep(1000);
        Assert.assertEquals(accountPage.lblDepositErr.getText(), "Initial Deposit must not be blank");
    }

    @Test(priority = 5)
    public void testDepositCharacters() throws InterruptedException {
        accountPage.txtCustomerId.sendKeys("66072");
        // Đợi 1 chút sau khi nhập ID để trang web ổn định
        Thread.sleep(1000);
        accountPage.txtDeposit.clear();
        accountPage.txtDeposit.sendKeys("sa");
        accountPage.txtDeposit.sendKeys(Keys.TAB);
        Thread.sleep(1000);
        Assert.assertEquals(accountPage.lblDepositErr.getText(), "Characters are not allowed");
    }

    @Test(priority = 6)
    public void testDepositSpecialChars() throws InterruptedException {
        accountPage.txtCustomerId.sendKeys("66072");
        Thread.sleep(1000);
        accountPage.txtDeposit.clear();
        accountPage.txtDeposit.sendKeys("123@");
        accountPage.txtDeposit.sendKeys(Keys.TAB);
        Thread.sleep(1000);
        Assert.assertEquals(accountPage.lblDepositErr.getText(), "Special characters are not allowed");
    }

    @Test(priority = 7)
    public void testSelectAccountType() throws InterruptedException {
        accountPage.txtCustomerId.sendKeys("66072");
        Thread.sleep(1000);
        accountPage.selectAccountType("Savings");
        Assert.assertEquals(accountPage.ddAccountType.getAttribute("value"), "Savings");
        accountPage.selectAccountType("Current");
        Assert.assertEquals(accountPage.ddAccountType.getAttribute("value"), "Current");
    }

    @Test(priority = 8, description = "TC08: Tạo tài khoản mới thành công với dữ liệu hợp lệ")
    public void testCreateAccountSuccessfully() throws InterruptedException {
        // 1. Nhập thông tin hợp lệ
        // Nhóm lưu ý: "66072" phải là một Customer ID đang tồn tại trên hệ thống
        accountPage.txtCustomerId.sendKeys("21312");
        accountPage.selectAccountType("Savings");
        accountPage.txtDeposit.sendKeys("5000");

        // 2. Nhấn Submit để tạo tài khoản
        accountPage.btnSubmit.click();

        // 3. Chờ trang kết quả tải xong
        Thread.sleep(2000);

        // 4. Kiểm tra xem có hiện Alert lỗi không (Ví dụ: Customer ID không tồn tại)
        try {
            String alertMsg = driver.switchTo().alert().getText();
            System.out.println("Lỗi hệ thống: " + alertMsg);
            driver.switchTo().alert().accept();
            Assert.fail("Không thể tạo Account vì: " + alertMsg);
        } catch (NoAlertPresentException e) {
            // Nếu không có Alert, kiểm tra dòng tiêu đề thành công trên trang mới
            String successMsg = driver.findElement(org.openqa.selenium.By.className("heading3")).getText();
            Assert.assertEquals(successMsg, "Account Generated Successfully!!!");

            // Lấy ra Account ID vừa tạo để dùng cho bài test Deposit sau này (nếu cần)
            String accountId = driver.findElement(org.openqa.selenium.By.xpath("//td[text()='Account ID']/following-sibling::td")).getText();
            System.out.println("Tạo Account thành công! ID mới là: " + accountId);
        }
    }
}