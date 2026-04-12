package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DepositPage {
    WebDriver driver;

    @FindBy(name = "accountno") public WebElement txtAccountNo;
    @FindBy(name = "ammount") public WebElement txtAmount; // Lưu ý: 2 chữ 'm'
    @FindBy(name = "desc") public WebElement txtDescription;

    @FindBy(name = "AccSubmit") public WebElement btnSubmit;
    // Các thông báo lỗi (Validation messages)
    @FindBy(id = "message2") public WebElement lblAccountNoErr;
    @FindBy(id = "message1") public WebElement lblAmountErr;
    @FindBy(id = "message17") public WebElement lblDescErr;

    public DepositPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}