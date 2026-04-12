package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FundTransferPage {
    WebDriver driver;

    @FindBy(name = "payersaccount") public WebElement txtPayerAccount;
    @FindBy(name = "payeeaccount") public WebElement txtPayeeAccount;
    @FindBy(name = "ammount") public WebElement txtAmount; // 2 chữ 'm' chuẩn Guru99
    @FindBy(name = "desc") public WebElement txtDescription;
    @FindBy(name = "AccSubmit") public WebElement btnSubmit;
    @FindBy(name = "res") public WebElement btnReset;

    // --- Các thông báo lỗi (ID thường dùng trên Guru99) ---
// --- Các thông báo lỗi (Validation messages) ---
    @FindBy(id = "message10") public WebElement lblPayerErr;   // Lỗi Payers account
    @FindBy(id = "message11") public WebElement lblPayeeErr;   // Lỗi Payees account
    @FindBy(id = "message1") public WebElement lblAmountErr;   // Lỗi Amount
    @FindBy(id = "message17") public WebElement lblDescErr;    // Lỗi Description

    public FundTransferPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void performFundTransfer(String payer, String payee, String amount, String desc) {
        txtPayerAccount.sendKeys(payer);
        txtPayeeAccount.sendKeys(payee);
        txtAmount.sendKeys(amount);
        txtDescription.sendKeys(desc);
        btnSubmit.click();
    }
}