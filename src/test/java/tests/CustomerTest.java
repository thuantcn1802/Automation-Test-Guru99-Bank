package tests;

import base.BaseTest;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.NewCustomerPage;

public class CustomerTest extends BaseTest {
    NewCustomerPage customerPage;
    String validEmail;

    @BeforeMethod
    public void setupCustomerTest() {
        new LoginPage(driver).login("mngr658369", "ytUdame");
        driver.get("https://demo.guru99.com/V4/manager/addcustomerpage.php");

        try {
            driver.switchTo().alert().accept();
        } catch (Exception e) {
            // Không có alert thì thôi, chạy tiếp
        }
        customerPage = new NewCustomerPage(driver);
        validEmail = "valid" + System.currentTimeMillis() + "@gmail.com";
    }

    // --- HÀM HỖ TRỢ: Điền đúng tất cả các trường ---
    public void fillAllFieldsCorrectly() {
        customerPage.txtName.sendKeys("Nguyen Van A");
        customerPage.txtDob.sendKeys("01012003");
        customerPage.txtAddr.sendKeys("123 Street HCM City");
        customerPage.txtCity.sendKeys("Ho Chi Minh");
        customerPage.txtState.sendKeys("South");
        customerPage.txtPin.sendKeys("123456");
        customerPage.txtMobile.sendKeys("0901234567");
        customerPage.txtEmail.sendKeys("nva123@gmail.com");
        customerPage.txtPassword.sendKeys("123456");
    }

    @Test(priority = 1, description = "TC01: Để trống Name")
    public void testNameBlank() {
        fillAllFieldsCorrectly();
        customerPage.txtName.clear();
        customerPage.txtName.sendKeys(Keys.TAB); // Dùng TAB thay vì Submit để tránh Alert
        Assert.assertEquals(customerPage.lblNameErr.getText(), "Customer name must not be blank");
    }

    @Test(priority = 2, description = "TC02: Email sai định dạng")
    public void testInvalidEmail() {
        fillAllFieldsCorrectly();
        customerPage.txtEmail.clear();
        customerPage.txtEmail.sendKeys("email_sai_dinh_dang");
        customerPage.txtEmail.sendKeys(Keys.TAB);
        Assert.assertEquals(customerPage.lblEmailErr.getText(), "Email-ID is not valid");
    }

    @Test(priority = 3, description = "TC03: PIN nhập chữ")
    public void testInvalidPinCharacters() {
        fillAllFieldsCorrectly();
        customerPage.txtPin.clear();
        customerPage.txtPin.sendKeys("ABCDEF");
        customerPage.txtPin.sendKeys(Keys.TAB);
        Assert.assertEquals(customerPage.lblPinErr.getText(), "Characters are not allowed");
    }

    @Test(priority = 4, description = "TC04: City chứa số")
    public void testCityWithNumbers() {
        fillAllFieldsCorrectly();
        customerPage.txtCity.clear();
        customerPage.txtCity.sendKeys("HCM123");

        // SỬA TẠI ĐÂY: Thay clickSubmit() bằng TAB
        customerPage.txtCity.sendKeys(Keys.TAB);

        Assert.assertEquals(customerPage.lblCityErr.getText(), "Numbers are not allowed");
    }

    @Test(priority = 5, description = "TC05: City chứa ký tự đặc biệt")
    public void testCityWithSpecialChars() {
        fillAllFieldsCorrectly();
        customerPage.txtCity.clear();
        customerPage.txtCity.sendKeys("HCM@#");
        customerPage.txtCity.sendKeys(Keys.TAB);
        Assert.assertEquals(customerPage.lblCityErr.getText(), "Special characters are not allowed");
    }

    @Test(priority = 6, description = "TC06: State chứa số")
    public void testStateWithNumbers() {
        fillAllFieldsCorrectly();
        customerPage.txtState.clear();
        customerPage.txtState.sendKeys("South12");
        customerPage.txtState.sendKeys(Keys.TAB);
        Assert.assertEquals(customerPage.lblStateErr.getText(), "Numbers are not allowed");
    }

    @Test(priority = 7, description = "TC07: Address chứa ký tự đặc biệt")
    public void testAddressWithSpecialChars() {
        fillAllFieldsCorrectly();
        customerPage.txtAddr.clear();
        customerPage.txtAddr.sendKeys("123 Street!");

        // SỬA TẠI ĐÂY: Thay clickSubmit() bằng TAB
        customerPage.txtAddr.sendKeys(Keys.TAB);

        Assert.assertEquals(customerPage.lblAddrErr.getText(), "Special characters are not allowed");
    }
    @Test(priority = 8, description = "TC08: Mobile chứa chữ")
    public void testMobileWithCharacters() {
        fillAllFieldsCorrectly();
        customerPage.txtMobile.clear();
        customerPage.txtMobile.sendKeys("090abc123");
        customerPage.txtMobile.sendKeys(Keys.TAB);
        Assert.assertEquals(customerPage.lblMobileErr.getText(), "Characters are not allowed");
    }

    @Test(priority = 9, description = "TC09: Mobile chứa ký tự đặc biệt")
    public void testMobileWithSpecialChars() {
        fillAllFieldsCorrectly();
        customerPage.txtMobile.clear();
        customerPage.txtMobile.sendKeys("090123!");
        customerPage.txtMobile.sendKeys(Keys.TAB);
        Assert.assertEquals(customerPage.lblMobileErr.getText(), "Special characters are not allowed");
    }

    @Test(priority = 10, description = "TC10: PIN ít hơn 6 chữ số")
    public void testPinLessThanSixDigits() {
        fillAllFieldsCorrectly();
        customerPage.txtPin.clear();
        customerPage.txtPin.sendKeys("123");
        customerPage.txtPin.sendKeys(Keys.TAB);
        Assert.assertEquals(customerPage.lblPinErr.getText(), "PIN Code must have 6 Digits");
    }

    @Test(priority = 11, description = "TC11: Để trống Password")
    public void testPasswordBlank() {
        fillAllFieldsCorrectly();
        customerPage.txtPassword.clear();
        customerPage.txtPassword.sendKeys(Keys.TAB);
        Assert.assertEquals(customerPage.lblPassErr.getText(), "Password must not be blank");
    }

    @Test(priority = 12, description = "TC12: Tạo Customer mới thành công với dữ liệu hợp lệ")
    public void testCreateCustomerSuccessfully() throws InterruptedException {
        // 1. Điền đầy đủ thông tin hợp lệ (Sử dụng hàm fill có sẵn)
        fillAllFieldsCorrectly();

        // 2. Nhấn Submit để thực hiện đăng ký
        customerPage.btnSubmit.click();

        // 3. Chờ trang kết quả tải xong (Guru99 xử lý DB hơi chậm nên để 2s)
        Thread.sleep(2000);

        // 4. Kiểm tra xem có hiện Alert lỗi không (Ví dụ: Email đã tồn tại)
        try {
            String alertMsg = driver.switchTo().alert().getText();
            System.out.println("Lỗi hệ thống: " + alertMsg);
            driver.switchTo().alert().accept();
            Assert.fail("Không thể tạo Customer vì: " + alertMsg);
        } catch (org.openqa.selenium.NoAlertPresentException e) {
            // 5. Nếu không có Alert, kiểm tra dòng tiêu đề thành công trên trang mới
            String successMsg = driver.findElement(org.openqa.selenium.By.className("heading3")).getText();
            Assert.assertEquals(successMsg, "Customer Registered Successfully!!!");

            // 6. LẤY CUSTOMER ID: Đây là bước quan trọng nhất để làm AccountTest
            // Dùng Xpath tìm ô bên cạnh ô có chữ 'Customer ID'
            String customerId = driver.findElement(org.openqa.selenium.By.xpath("//td[text()='Customer ID']/following-sibling::td")).getText();

            System.out.println("----------------------------------------------");
            System.out.println("TẠO CUSTOMER THÀNH CÔNG!");
            System.out.println("CUSTOMER ID MỚI CỦA BẠN LÀ: " + customerId);
            System.out.println("HÃY DÙNG ID NÀY CHO BÀI ACCOUNT TEST");
            System.out.println("----------------------------------------------");
        }
    }
}