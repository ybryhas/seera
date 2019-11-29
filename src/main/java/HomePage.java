import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage{
    @FindBy(id = "main-home")
    private WebElement mainHome;

    @FindBy(tagName = "html")
    private WebElement htmlTag;

    @FindBy(id = "pwa-home-drawer")
    private WebElement menuButton;

    @FindBy(id = "mobile-home-homeScreen-changeLanguage-button")
    private WebElement changeLanguageButton;

    @FindBy(id = "mobile-home-homeScreen-flight-cta")
    private WebElement flightButton;

    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean isPageOpened(){
        wait.until(ExpectedConditions.visibilityOf(mainHome));

        return  mainHome != null;
    }

    public String getLanguage(){
        return htmlTag.getAttribute("lang");
    }

    public void switchLanguage(){
        menuButton.click();
        changeLanguageButton.click();
    }

    public FlightsPage openFlightsPage(){
        flightButton.click();

        return new FlightsPage(driver);
    }
}