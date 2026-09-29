package com.example.tests.flightreservation;

import com.example.tests.BaseTest;
import com.example.tests.flightreservation.model.FlightReservationTestData;
import org.example.Pages.fightreservation.*;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.Config;
import utils.Constants;

import java.io.IOException;

public class FlightReservationTest extends BaseTest {

    private FlightReservationTestData testData;

    @BeforeTest
    @Parameters("testDataPath") // to get the json
    public void setparams(String testDataPath) throws IOException {
    this.testData = utils.JsonUtils.getTestData(testDataPath, FlightReservationTestData.class);
    // read the json file and store it in testData object
        System.out.println(testData);
    }

    @Test
    public void userRegistrationPage(){
        RegistrationPage registrationPage = new RegistrationPage(webDriver);
        registrationPage.goToUrl((Config.getProperty(Constants.FLIGHT_RESERVATION_URL)));
        Assert.assertTrue(registrationPage.isPageLoaded());
        registrationPage.enterUserDetails(testData.firstname(), testData.lastname());
        registrationPage.userCredentials(testData.email(), testData.password());
        registrationPage.enterAddressDetails(testData.street(), testData.city(), testData.zip());
        registrationPage.clickRegisterButton();
    }

    @Test(dependsOnMethods = "userRegistrationPage")
    public void registrationConfirmation(){
        RegistrationConfirmationPage registrationConfirmationPage = new RegistrationConfirmationPage(webDriver);
        Assert.assertTrue(registrationConfirmationPage.isPageLoaded());
        registrationConfirmationPage.verifyConfirmationPage();
        registrationConfirmationPage.clickGoToFlightsSearch();
    }

    @Test(dependsOnMethods = "registrationConfirmation")
    public void flightSearch(){
        FlightSearchPage flightSearchPage = new FlightSearchPage(webDriver);
        Assert.assertTrue(flightSearchPage.isPageLoaded());
        flightSearchPage.selectPassengers(testData.passengersCount());
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        flightSearchPage.clickSearchFlightsButton();
    }

    @Test(dependsOnMethods = "flightSearch")
    public void searchResults(){
        searchResultsPage searchResultsPage = new searchResultsPage(webDriver);
        Assert.assertTrue(searchResultsPage.isPageLoaded());
        searchResultsPage.selectDepartureFlight();
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        searchResultsPage.selectArrivalFlight();
        searchResultsPage.clickFlightButton();
    }

    @Test(dependsOnMethods = "searchResults")
    public void flightConfirmation(){
        FlightsConfirmationPage flightsConfirmationPage = new FlightsConfirmationPage(webDriver);
        Assert.assertTrue(flightsConfirmationPage.isPageLoaded());
        Assert.assertEquals(flightsConfirmationPage.getPrice(), testData.expectedPrice());
    }
}