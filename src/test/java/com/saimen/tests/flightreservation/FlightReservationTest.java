package com.saimen.tests.flightreservation;

import com.saimen.pages.flightReservation.*;
import com.saimen.tests.AbstractTest;
import com.saimen.util.Config;
import com.saimen.util.Constants;
import com.saimen.util.JsonUtil;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.saimen.tests.flightreservation.model.FlightReservationTestData;

public class FlightReservationTest extends AbstractTest {

    private FlightReservationTestData testData;

    @BeforeTest
    @Parameters({ "testDataPath" })
    public void setParameters(String testDataPath) {
        this.testData = JsonUtil.getTestData(testDataPath, FlightReservationTestData.class);
    }

    @Test
    public void userRegistration() {
        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.goTo(Config.get(Constants.FLIGHT_RESERVATION_URL));
        Assert.assertTrue(registrationPage.isAt());

        registrationPage.enterUserDetails(testData.firstName(), testData.lastName());
        registrationPage.enterUserCredentials(testData.email(), testData.password());
        registrationPage.enterUserAddress(testData.street(), testData.city(), testData.zip());
        registrationPage.register();

    }

    @Test(dependsOnMethods = "userRegistration")
    public void registrationConfirmationTest() {
        RegistrationConfirmationPage registrationConfirmation = new RegistrationConfirmationPage(driver);
        Assert.assertTrue(registrationConfirmation.isAt());
        Assert.assertEquals(registrationConfirmation.getFirstName(), testData.firstName());
        registrationConfirmation.goToFlightsSearch();

    }

    @Test(dependsOnMethods = "registrationConfirmationTest")
    public void flightSearchTest() {
        FlightsSearchPage flightsSearchPage = new FlightsSearchPage(driver);
        Assert.assertTrue(flightsSearchPage.isAt());
        flightsSearchPage.selectPassengers(testData.passengersCount());
        flightsSearchPage.searchFlights();
    }

    @Test(dependsOnMethods = "flightSearchTest")
    public void flightsSelectionTest() {
        FlightsSelectionPage flightsSelectionPage = new FlightsSelectionPage(driver);
        Assert.assertTrue(flightsSelectionPage.isAt());
        flightsSelectionPage.selectFlights();
        flightsSelectionPage.confirmFlight();

    }

    @Test(dependsOnMethods = "flightsSelectionTest")
    public void flightsReservationConfirmationTest() {
        FlightConfirmationPage flightConfirmationPage = new FlightConfirmationPage(driver);
        Assert.assertTrue(flightConfirmationPage.isAt());
        Assert.assertEquals(flightConfirmationPage.getPrice(), testData.expectedPrice());

    }

}
