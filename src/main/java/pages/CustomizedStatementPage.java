package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CustomizedStatementPage {
    WebDriver driver;

    @FindBy(name = "accountno") public WebElement txtAccountNo;
    @FindBy(name = "fdate") public WebElement txtFromDate;
    @FindBy(name = "tdate") public WebElement txtToDate;
    @FindBy(name = "amountlowerlimit") public WebElement txtMinimumTransaction;
    @FindBy(name = "numtransaction") public WebElement txtNumTransaction;
    @FindBy(name = "AccSubmit") public WebElement btnSubmit;

    // --- Locators thông báo lỗi ---
    @FindBy(id = "message2") public WebElement lblAccountNoErr;
    @FindBy(id = "message1") public WebElement lblMinTransErr;
    @FindBy(id = "message17") public WebElement lblNumTransErr;

    public CustomizedStatementPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void getCustomizedStatement(String accNo, String fDate, String tDate, String minTrans, String numTrans) {
        txtAccountNo.sendKeys(accNo);
        // Lưu ý: Đối với ô Date trên Chrome, đôi khi cần dùng Keys.TAB để chuyển giữa ngày/tháng/năm
        txtFromDate.sendKeys(fDate);
        txtToDate.sendKeys(tDate);
        txtMinimumTransaction.sendKeys(minTrans);
        txtNumTransaction.sendKeys(numTrans);
        btnSubmit.click();
    }
}