import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;
import java.net.URL;

import static java.util.concurrent.TimeUnit.SECONDS;

public abstract class BaseTest {

    private static AppiumDriverLocalService service;
    protected AndroidDriver<WebElement> driver;
    protected Wait wait;

    @BeforeSuite
    public void globalSetup () throws IOException {
        service = AppiumDriverLocalService.buildDefaultService();
        service.start();
    }

    @AfterSuite
    public void globalTearDown () {
        service.stop();
    }

    public URL getServiceUrl () {
        return service.getUrl();
    }

    @BeforeClass
    public void setUp() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "Android");
        capabilities.setCapability("browserName", "Chrome");
        driver = new AndroidDriver<WebElement>(getServiceUrl(), capabilities);
        wait = new FluentWait(driver)
                .withTimeout(30, SECONDS)
                .pollingEvery(5, SECONDS)
                .ignoring(NoSuchElementException.class);
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

}
