package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class WithdrawPage {
    WebDriver driver;

    @FindBy(name = "accountno") public WebElement txtAccountNo;
    @FindBy(name = "ammount") public WebElement txtAmount; // Vẫn là 2 chữ 'm' nhé
    @FindBy(name = "desc") public WebElement txtDescription;
    @FindBy(name = "AccSubmit") public WebElement btnSubmit;
    @FindBy(name = "res") public WebElement btnReset;

    // --- Các thông báo lỗi đỏ ---
    @FindBy(id = "message2") public WebElement lblAccountNoErr;
    @FindBy(id = "message1") public WebElement lblAmountErr;
    @FindBy(id = "message17") public WebElement lblDescErr;

    public WithdrawPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void performWithdraw(String accountNo, String amount, String description) {
        txtAccountNo.sendKeys(accountNo);
        txtAmount.sendKeys(amount);
        txtDescription.sendKeys(description);
        btnSubmit.click();
    }
}