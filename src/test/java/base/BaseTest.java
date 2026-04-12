package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().window().maximize();
        driver.get("https://demo.guru99.com/V4/");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // Đóng trình duyệt sau mỗi bài test
        }
    }

    @AfterMethod
    public void handleAlert() {
        try {
            driver.switchTo().alert().accept(); // Luôn đóng Alert nếu nó lỡ hiện ra
        } catch (Exception e) {
            // Không có alert thì thôi
        }
    }
}