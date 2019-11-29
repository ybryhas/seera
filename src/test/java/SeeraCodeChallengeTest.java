import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Random;

public class SeeraCodeChallengeTest extends BaseTest {

    HomePage homePage;
    FlightsPage flightsPage;

    String[] origins = new String[]{"DXB", "AUH", "SHJ", "JED", "RUH"};
    String[] destinations =  new String []{"AMM", "CAI", "DEL", "KHI", "PAR"};

    @Test()
    public void openPageTest(){

//        1- Open tajawal.com or almosafer.com App
        driver.get("https://www.tajawal.com/");
        homePage = new HomePage(driver);

        Assert.assertTrue(homePage.isPageOpened(), "Homepage is not opened");

//        2- Check for current set language. If language is already set to english then proceed with next steps. If not, then first change language to english and then proceed.
        if (!homePage.getLanguage().equals("en")){
            homePage.switchLanguage();
        }

        Assert.assertEquals(homePage.getLanguage(), "en", "Language is not English");
    }

    @Test(dependsOnMethods = {"openPageTest"})
    public void flightsHomeTest(){

//        3- Navigate to flights-home page, and enter below criteria in flights search form to make flight search:
        flightsPage = homePage.openFlightsPage();

        Assert.assertTrue(flightsPage.isPageOpened(), "Flights Page is not opened");
    }

    @Test(dependsOnMethods = {"flightsHomeTest"})
    public void tajawalMainTest(){
        String origin = origins[new Random().nextInt(origins.length)];
        String destnation = destinations[new Random().nextInt(destinations.length)];

        flightsPage.waitVisibilityOfElement(flightsPage.getSearchFlightsButton());

        //origin
        flightsPage.searchOrigin(origin);

        //destination
        flightsPage.waitVisibilityOfElement(flightsPage.getSearchFlightsButton());
        flightsPage.searchDestanation(destnation);


        //dates
        flightsPage.waitVisibilityOfElement(flightsPage.getSearchFlightsButton());

        flightsPage.getDepartureDateField().click();

        FlightsPage.CalendarWidget calendarWidget = new FlightsPage.CalendarWidget(driver);

        int start = getRandomNumber(0, calendarWidget.getAllDatesCount());

        System.out.println("All " + calendarWidget.getAllDatesCount());
        System.out.println("Start " + start);

        flightsPage.enterDepartureDate(start);

        calendarWidget = new FlightsPage.CalendarWidget(driver);
        int end = getRandomNumber(0, calendarWidget.getAllDatesCount());
        System.out.println("All " + calendarWidget.getAllDatesCount());
        System.out.println("End " + end);

        flightsPage.enterReturnDate(end);


        //passenger
        flightsPage.waitVisibilityOfElement(flightsPage.getSearchFlightsButton());
        flightsPage.getPassengerField().click();
        flightsPage.selectPassengersCount(1);

        //class
        flightsPage.waitVisibilityOfElement(flightsPage.getSearchFlightsButton());
        flightsPage.getClassField().click();
        flightsPage.setClass("Economy");

        //        //press button
        flightsPage.waitVisibilityOfElement(flightsPage.getSearchFlightsButton( ));
        flightsPage.getSearchFlightsButton().click();


        flightsPage.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".MuiLinearProgress-bar")));

          if(flightsPage.getAlPrces().size() > 0){
              System.out.println("FIRST PRICE: " + flightsPage.getFirstPrice());
              Assert.assertTrue(flightsPage.isCollectionSorted(flightsPage.getAlPrces()), "Prices are not sorted");
          } else {
              Assert.assertTrue(false, "No flights were found between " + origin + " and " + destnation);
          }

    }

    private static int getRandomNumber(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

}