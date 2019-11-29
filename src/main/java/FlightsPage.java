import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class FlightsPage extends BasePage{
    @FindBy(id = "mobile-flights-cta")
    private WebElement searchFlightsButton;

    @FindBys
    (@FindBy(css = "ul div.MuiButtonBase-root"))
    private List<WebElement> userInputs;

    @FindBys
    (@FindBy(xpath = "//div[contains(text(), \"AED\")]"))
    private List<WebElement> prices;

    public FlightsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    public boolean isPageOpened(){
        return searchFlightsButton != null;
    }

    public WebElement getSearchFlightsButton() {
        return searchFlightsButton;
    }

    public WebElement getOriginField() {
        return userInputs.get(0);
    }

    public WebElement getDestinationField() {
        return userInputs.get(1);
    }

    public WebElement getDepartureDateField() {
        return userInputs.get(2);
    }

    public WebElement getReturnDateField() {
        return userInputs.get(3);
    }

    public WebElement getPassengerField() {
        return userInputs.get(4);
    }

    public WebElement getClassField() {
        return userInputs.get(5);
    }

    public class OriginWidget{
        @FindBy(id = "downshift-0-input")
        private WebElement inputField;

        @FindBy(id = "downshift-0-item-0")
        private WebElement firstSearchResult;

        public OriginWidget(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        public WebElement getInputField() {
            return inputField;
        }

        public WebElement getFirstSearchResult() {
            return firstSearchResult;
        }
    }

    public class DestinationWidget{
        @FindBy(id = "downshift-1-input")
        private WebElement inputField;

        @FindBy(id = "downshift-1-item-0")
        private WebElement firstSearchResult;

        public DestinationWidget(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        public WebElement getInputField() {
            return inputField;
        }

        public WebElement getFirstSearchResult() {
            return firstSearchResult;
        }
    }

    static class CalendarWidget{
        @FindBys
                (@FindBy(css = ".CalendarDay[aria-disabled='false']"))
        private List<WebElement> dates;

        public CalendarWidget(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        public WebElement getDate(int date) {
            return dates.get(date);
        }

        public int getAllDatesCount(){
            return dates.size();
        }
    }

    class PassengersWidget{
        @FindBy(id = "mobile-flights-done-passengers")
        private WebElement okButton;

        public PassengersWidget(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        public WebElement getOkButton() {
            return okButton;
        }
    }

    class ClassWidget{
        public ClassWidget(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }
    }

    public void searchOrigin(String airportCode){
        getOriginField().click();

        OriginWidget widget = new OriginWidget(driver);

        waitVisibilityOfElement(widget.getInputField());
        widget.getInputField().sendKeys(airportCode);

        waitVisibilityOfElement(widget.getFirstSearchResult());
        widget.getFirstSearchResult().click();
    }

    public void  searchDestanation(String airportCode){
        getDestinationField().click();

        DestinationWidget widget = new DestinationWidget(driver);

        waitVisibilityOfElement(widget.getInputField());
        widget.getInputField().sendKeys(airportCode);

        waitVisibilityOfElement(widget.getFirstSearchResult());
        widget.getFirstSearchResult().click();
    }

    public void enterDepartureDate(int date){
        CalendarWidget widget = new CalendarWidget(driver);

        waitVisibilityOfElement( widget.getDate(0));
        Actions actions = new Actions(driver);
        actions.moveToElement(widget.getDate(date)).perform();
        widget.getDate(date).click();

    }

    public void enterReturnDate(int date){
        CalendarWidget widget = new CalendarWidget(driver);

        waitVisibilityOfElement( widget.getDate(0));
        Actions actions = new Actions(driver);
        actions.moveToElement(widget.getDate(date)).perform();
        widget.getDate(date).click();

    }
    public void selectPassengersCount(int count){
        PassengersWidget widget = new PassengersWidget(driver);

        waitVisibilityOfElement( widget.getOkButton());
        widget.getOkButton().click();
    }

    public void setClass(String clazz){
        waitVisibilityOfElement(driver.findElement(By.xpath("//ul/li[text()='" + clazz + "']")));
        driver.findElement(By.xpath("//ul/li[text()='" + clazz + "']")).click();

    }

    public String getFirstPrice() {
        return prices.get(0).getText();
    }

    public List<Integer> getAlPrces() {

        List<Integer> res = new ArrayList();
        for(WebElement price : prices){
            res.add(Integer.parseInt(price.getText().split(" ")[1].replace(",","")));
        }

        return res;
    }
}
