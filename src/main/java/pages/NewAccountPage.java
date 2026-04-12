package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class NewAccountPage {
    WebDriver driver;

    // --- Các ô nhập liệu ---
    @FindBy(name = "cusid") public WebElement txtCustomerId;
    @FindBy(name = "selaccount") public WebElement ddAccountType;
    // Chữ 'inital' viết thiếu chữ 'i' là đúng với mã nguồn của Guru99
    @FindBy(name = "inideposit") public WebElement txtDeposit;
    // --- Các nút bấm ---
    @FindBy(name = "button2") public WebElement btnSubmit;
    @FindBy(name = "reset") public WebElement btnReset;

    // --- Các thông báo lỗi (Validation messages) ---
    // id='message14' dành cho Customer ID
    @FindBy(id = "message14") public WebElement lblCustomerIdErr;
    // id='message17' dành cho Initial Deposit
    @FindBy(id = "message19") public WebElement lblDepositErr;

    public NewAccountPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Hàm chọn Account Type: 'Savings' hoặc 'Current'
    public void selectAccountType(String type) {
        Select select = new Select(ddAccountType);
        select.selectByVisibleText(type);
    }

    // Hàm nhấn Reset
    public void clickReset() {
        btnReset.click();
    }

    // Hàm nhấn Submit (dùng khi test thành công hoặc test Alert)
    public void clickSubmit() {
        btnSubmit.click();
    }
}