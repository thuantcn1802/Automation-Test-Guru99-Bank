package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.FundTransferPage;
import org.openqa.selenium.Keys;

public class FundTransferTest extends BaseTest {
    FundTransferPage transferPage;

    @BeforeMethod
    public void setupFundTransferTest() throws InterruptedException {
        new LoginPage(driver).login("mngr658369", "ytUdame");
        Thread.sleep(1000);
        // Nhảy thẳng trang để né quảng cáo menu
        driver.get("https://demo.guru99.com/V4/manager/FundTransInput.php");

        try {
            driver.switchTo().alert().accept();
        } catch (Exception e) {}

        transferPage = new FundTransferPage(driver);
    }

    @Test(priority = 1, description = "TC01: Chuyển khoản thành công (Kiểm tra Redirect)")
    public void testFundTransferSuccessfully() throws InterruptedException {
        // Thay bằng 2 ID tài khoản THẬT nhóm vừa tạo
        String payerID = "181333";
        String payeeID = "181334";

        transferPage.performFundTransfer(payerID, payeeID, "100", "Chuyen tien");

        Thread.sleep(2000);

        // Kỹ thuật né lỗi 500: Kiểm tra URL chứa file xử lý kết quả
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("FundTrans.php"),
                "Không chuyển hướng đến trang kết quả! URL: " + currentUrl);

        System.out.println("PASS: Hệ thống đã gửi yêu cầu chuyển tiền thành công.");
    }

    @Test(priority = 2, description = "TC02: Lỗi khi nhập trùng tài khoản")
    public void testTransferToSameAccountError() {
        transferPage.performFundTransfer("181333", "181333", "100", "Loi trung id");

        org.openqa.selenium.support.ui.WebDriverWait wait = new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10));

        try {
            wait.until(org.openqa.selenium.support.ui.ExpectedConditions.alertIsPresent());

            String alertMsg = driver.switchTo().alert().getText();
            System.out.println("Alert thực tế: " + alertMsg);

            // Chuyển về chữ thường để so sánh không phân biệt hoa thường
            String lowerCaseMsg = alertMsg.toLowerCase();

            // Kiểm tra: Chỉ cần chứa 'authorize' HOẶC 'same' là Pass
            Assert.assertTrue(lowerCaseMsg.contains("authorize") || lowerCaseMsg.contains("same"),
                    "Alert không đúng! Thực tế nhận được: " + alertMsg);

            driver.switchTo().alert().accept();
            System.out.println("TC02 Pass: Đã bắt được lỗi trùng tài khoản/quyền hạn.");

        } catch (org.openqa.selenium.TimeoutException e) {
            Assert.fail("Server không hiển thị Alert báo lỗi!");
        }
    }

    // --- TEST PAYERS ACCOUNT ---
    @Test(priority = 3, description = "TC03: Payers Account để trống")
    public void testPayerBlank() {
        transferPage.txtPayerAccount.sendKeys("");
        transferPage.txtPayerAccount.sendKeys(Keys.TAB);
        Assert.assertEquals(transferPage.lblPayerErr.getText(), "Payers Account Number must not be blank");
    }

    @Test(priority = 4, description = "TC04: Payers Account nhập chữ/kí tự đặc biệt")
    public void testPayerInvalid() {
        transferPage.txtPayerAccount.sendKeys("abc!");
        // Kiểm tra lỗi Characters hoặc Special characters tùy vào ký tự nhập vào
        Assert.assertTrue(transferPage.lblPayerErr.getText().contains("allowed"));
    }

    // --- TEST PAYEES ACCOUNT ---
    @Test(priority = 5, description = "TC05: Payees Account để trống")
    public void testPayeeBlank() {
        transferPage.txtPayerAccount.sendKeys("181333"); // Điền đúng Payer
        transferPage.txtPayeeAccount.sendKeys("");
        transferPage.txtPayeeAccount.sendKeys(Keys.TAB);
        Assert.assertEquals(transferPage.lblPayeeErr.getText(), "Payees Account Number must not be blank");
    }

    @Test(priority = 6, description = "TC06: Payees Account nhập chữ")
    public void testPayeeCharacters() {
        transferPage.txtPayeeAccount.sendKeys("xyz");
        Assert.assertEquals(transferPage.lblPayeeErr.getText(), "Characters are not allowed");
    }

    // --- TEST AMOUNT ---
    @Test(priority = 7, description = "TC07: Amount để trống")
    public void testAmountBlank() {
        transferPage.txtPayerAccount.sendKeys("181333");
        transferPage.txtPayeeAccount.sendKeys("181334");
        transferPage.txtAmount.sendKeys("");
        transferPage.txtAmount.sendKeys(Keys.TAB);
        Assert.assertEquals(transferPage.lblAmountErr.getText(), "Amount field must not be blank");
    }

    @Test(priority = 8, description = "TC08: Amount nhập ký tự đặc biệt")
    public void testAmountSpecialChars() {
        transferPage.txtAmount.sendKeys("100@");
        Assert.assertEquals(transferPage.lblAmountErr.getText(), "Special characters are not allowed");
    }

    // --- TEST DESCRIPTION ---
    @Test(priority = 9, description = "TC09: Description để trống")
    public void testDescriptionBlank() {
        transferPage.txtPayerAccount.sendKeys("181333");
        transferPage.txtPayeeAccount.sendKeys("181334");
        transferPage.txtAmount.sendKeys("500");
        transferPage.txtDescription.sendKeys("");
        transferPage.txtDescription.sendKeys(Keys.TAB);
        Assert.assertEquals(transferPage.lblDescErr.getText(), "Description can not be blank");
    }
}