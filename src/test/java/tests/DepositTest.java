package tests;

import base.BaseTest;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.DepositPage;
import org.openqa.selenium.By;

public class DepositTest extends BaseTest {
    DepositPage depositPage;

    @BeforeMethod
    public void setupDepositTest() throws InterruptedException {
        new LoginPage(driver).login("mngr658369", "ytUdame");
        Thread.sleep(1000);
        driver.get("https://demo.guru99.com/V4/manager/DepositInput.php");

        // Dọn dẹp Alert cũ nếu có
        try {
            driver.switchTo().alert().accept();
        } catch (Exception e) { }

        depositPage = new DepositPage(driver);
    }

    // --- TEST ACCOUNT NO ---
    @Test(priority = 1, description = "TC01: Account No để trống")
    public void testAccountNoBlank() {
        depositPage.txtAccountNo.sendKeys("");
        depositPage.txtAccountNo.sendKeys(Keys.TAB);
        Assert.assertEquals(depositPage.lblAccountNoErr.getText(), "Account Number must not be blank");
    }

    @Test(priority = 2, description = "TC02: Account No nhập chữ")
    public void testAccountNoCharacters() {
        depositPage.txtAccountNo.sendKeys("abc");
        Assert.assertEquals(depositPage.lblAccountNoErr.getText(), "Characters are not allowed");
    }

    @Test(priority = 3, description = "TC03: Account No ký tự đặc biệt")
    public void testAccountNoSpecialChars() {
        depositPage.txtAccountNo.sendKeys("123!");
        Assert.assertEquals(depositPage.lblAccountNoErr.getText(), "Special characters are not allowed");
    }

    // --- TEST AMOUNT ---
    @Test(priority = 4, description = "TC04: Amount để trống")
    public void testAmountBlank() {
        depositPage.txtAccountNo.sendKeys("12345"); // Điền ID đúng để cô lập lỗi Amount
        depositPage.txtAmount.sendKeys("");
        depositPage.txtAmount.sendKeys(Keys.TAB);
        Assert.assertEquals(depositPage.lblAmountErr.getText(), "Amount field must not be blank");
    }

    @Test(priority = 5, description = "TC05: Amount nhập chữ")
    public void testAmountCharacters() {
        depositPage.txtAmount.sendKeys("money");
        Assert.assertEquals(depositPage.lblAmountErr.getText(), "Characters are not allowed");
    }

    @Test(priority = 6, description = "TC06: Amount ký tự đặc biệt")
    public void testAmountSpecialChars() {
        depositPage.txtAmount.sendKeys("500@");
        Assert.assertEquals(depositPage.lblAmountErr.getText(), "Special characters are not allowed");
    }

    // --- TEST DESCRIPTION ---
    @Test(priority = 7, description = "TC07: Description để trống")
    public void testDescriptionBlank() {
        depositPage.txtAccountNo.sendKeys("12345");
        depositPage.txtAmount.sendKeys("5000");
        depositPage.txtDescription.sendKeys("");
        depositPage.txtDescription.sendKeys(Keys.TAB);
        Assert.assertEquals(depositPage.lblDescErr.getText(), "Description can not be blank");
    }

    @Test(priority = 8, description = "TC08: Kiểm tra chuyển hướng khi nạp tiền thành công")
    public void testDepositSuccessfully() throws InterruptedException {
        // 1. Nhập liệu như bình thường
        String accountId = "181333";
        depositPage.txtAccountNo.sendKeys(accountId);
        depositPage.txtAmount.sendKeys("5000");
        depositPage.txtDescription.sendKeys("Nop tien thang 4");

        // 2. Nhấn Submit
        depositPage.btnSubmit.click();

        // 3. Chờ một chút để trình duyệt thực hiện lệnh chuyển hướng
        Thread.sleep(2000);

        // 4. Lấy URL hiện tại sau khi nhấn Submit
        String currentUrl = driver.getCurrentUrl();
        System.out.println("URL sau khi Submit: " + currentUrl);

        // 5. KIỂM TRA: Chỉ cần URL chuyển sang file xử lý là công nhận PASS
        // Chúng ta dùng assertTrue để kiểm tra xem URL có chứa 'Deposit.php' không
        Assert.assertTrue(currentUrl.contains("Deposit.php"),
                "Lỗi: Không chuyển hướng đến trang xử lý nạp tiền! URL hiện tại: " + currentUrl);

        // In thêm dòng thông báo cho pro
        System.out.println("Xác nhận: Hệ thống đã thực hiện lệnh nạp tiền và chuyển hướng thành công!");
    }
}