import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;

public abstract class BasePage {
    protected WebDriver driver;
    protected Wait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new FluentWait(driver)
                .withTimeout(30, SECONDS)
                .pollingEvery(5, SECONDS)
                .ignoring(NoSuchElementException.class);
    }

    public void waitVisibilityOfElement(WebElement element){
        wait.until( ExpectedConditions.visibilityOf(element));
    }

    public boolean isCollectionSorted(List list) {
        List copy = new ArrayList(list);
        Collections.sort(copy);
        return copy.equals(list);
    }
}