package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NewCustomerPage {
    WebDriver driver;

    @FindBy(name = "name") public WebElement txtName;
    @FindBy(name = "dob") public WebElement txtDob;
    @FindBy(name = "addr") public WebElement txtAddr;
    @FindBy(name = "city") public WebElement txtCity;
    @FindBy(name = "state") public WebElement txtState;
    @FindBy(name = "pinno") public WebElement txtPin;
    @FindBy(name = "telephoneno") public WebElement txtMobile;
    @FindBy(name = "emailid") public WebElement txtEmail;
    @FindBy(name = "password") public WebElement txtPassword;
    @FindBy(name = "res") public WebElement btnReset;

    // Các ID thông báo lỗi (Validation Messages) dựa trên giao diện Guru99
    @FindBy(id = "message") public WebElement lblNameErr;
    @FindBy(id = "message24") public WebElement lblDobErr;
    @FindBy(id = "message3") public WebElement lblAddrErr;
    @FindBy(id = "message4") public WebElement lblCityErr;
    @FindBy(id = "message5") public WebElement lblStateErr;
    @FindBy(id = "message6") public WebElement lblPinErr;
    @FindBy(id = "message7") public WebElement lblMobileErr;
    @FindBy(id = "message9") public WebElement lblEmailErr;
    @FindBy(id = "message18") public WebElement lblPassErr;

    @FindBy(name = "sub") public WebElement btnSubmit;

    public NewCustomerPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickReset() {
        btnReset.click();
    }

    public void clickSubmit() {
        btnSubmit.click();
    }

    public boolean isFormCleared() {
        return txtName.getAttribute("value").isEmpty()
                && txtAddr.getAttribute("value").isEmpty()
                && txtPin.getAttribute("value").isEmpty();
    }

}