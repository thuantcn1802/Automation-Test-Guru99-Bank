package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.CustomizedStatementPage;

public class StatementTest extends BaseTest {
    CustomizedStatementPage statementPage;

    @BeforeMethod
    public void setupStatementTest() throws InterruptedException {
        new LoginPage(driver).login("mngr658369", "ytUdame");
        Thread.sleep(1000);
        // Nhảy thẳng trang để né quảng cáo menu
        driver.get("https://demo.guru99.com/V4/manager/CustomisedStatementInput.php");

        try {
            driver.switchTo().alert().accept();
        } catch (NoAlertPresentException e) {}

        statementPage = new CustomizedStatementPage(driver);
    }

    @Test(priority = 1, description = "TC01: Xuất sao kê thành công")
    public void testGetStatementSuccessfully() throws InterruptedException {
        String accountId = "181333";
        statementPage.getCustomizedStatement(accountId, "01012024", "12122026", "100", "10");

        Thread.sleep(2000);

        try {
            String alertMsg = driver.switchTo().alert().getText().toLowerCase();
            driver.switchTo().alert().accept();
            Assert.fail("Server báo lỗi: " + alertMsg);
        } catch (NoAlertPresentException e) {}

        Assert.assertTrue(driver.getCurrentUrl().contains("CustomisedStatement.php"),
                "Không chuyển hướng đến trang kết quả! URL: " + driver.getCurrentUrl());
    }

    @Test(priority = 2, description = "TC02: Kiểm tra lỗi trường Account No")
    public void testAccountNoValidation() {
        statementPage.txtAccountNo.click();
        statementPage.txtAccountNo.sendKeys(Keys.TAB);
        // Sử dụng XPath để tìm nhãn lỗi ngay sau ô input để tránh sai ID
        String errorMsg = driver.findElement(By.xpath("//input[@name='accountno']/following-sibling::label")).getText();
        Assert.assertEquals(errorMsg, "Account Number must not be blank");

        statementPage.txtAccountNo.sendKeys("abc");
        errorMsg = driver.findElement(By.xpath("//input[@name='accountno']/following-sibling::label")).getText();
        Assert.assertEquals(errorMsg, "Characters are not allowed");
    }

    @Test(priority = 3, description = "TC03: Kiểm tra lỗi để trống ngày tháng")
    public void testDateBlank() throws InterruptedException {
        statementPage.txtFromDate.click();
        statementPage.txtToDate.click();
        statementPage.txtMinimumTransaction.click();

        Thread.sleep(1000);

        // Dùng XPath sibling để lấy nhãn lỗi của fdate và tdate
        String fromDateErr = driver.findElement(By.xpath("//input[@name='fdate']/following-sibling::label")).getText();
        String toDateErr = driver.findElement(By.xpath("//input[@name='tdate']/following-sibling::label")).getText();

        Assert.assertEquals(fromDateErr, "From Date Field must not be blank");
        Assert.assertEquals(toDateErr, "To Date Field must not be blank");
    }

    @Test(priority = 4, description = "TC04: Kiểm tra lỗi Minimum Transaction Value")
    public void testMinTransactionValidation() {
        statementPage.txtMinimumTransaction.sendKeys("abc");
        statementPage.txtMinimumTransaction.sendKeys(Keys.TAB);

        String errorMsg = driver.findElement(By.xpath("//input[@name='amountlowerlimit']/following-sibling::label")).getText();
        Assert.assertEquals(errorMsg, "Characters are not allowed");

        statementPage.txtMinimumTransaction.clear();
        statementPage.txtMinimumTransaction.sendKeys("100#");
        errorMsg = driver.findElement(By.xpath("//input[@name='amountlowerlimit']/following-sibling::label")).getText();
        Assert.assertEquals(errorMsg, "Special characters are not allowed");
    }

    @Test(priority = 5, description = "TC05: Kiểm tra lỗi Number of Transaction")
    public void testNumTransactionValidation() {
        statementPage.txtNumTransaction.sendKeys("xyz");
        statementPage.txtNumTransaction.sendKeys(Keys.TAB);

        String errorMsg = driver.findElement(By.xpath("//input[@name='numtransaction']/following-sibling::label")).getText();
        Assert.assertEquals(errorMsg, "Characters are not allowed");

        statementPage.txtNumTransaction.clear();
        statementPage.txtNumTransaction.sendKeys("10@");
        errorMsg = driver.findElement(By.xpath("//input[@name='numtransaction']/following-sibling::label")).getText();
        Assert.assertEquals(errorMsg, "Special characters are not allowed");
    }
}