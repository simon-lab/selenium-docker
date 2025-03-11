package com.saimen.tests.vendorportal;

import com.saimen.pages.vendorportal.DasboardPage;
import com.saimen.pages.vendorportal.LoginPage;
import com.saimen.tests.AbstractTest;
import com.saimen.util.Config;
import com.saimen.util.Constants;
import com.saimen.util.JsonUtil;
import com.saimen.tests.vendorportal.model.VendorPortalTestData;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class VendorPortalTest extends AbstractTest {

    private LoginPage loginPage;
    private DasboardPage dasboardPage;
    private VendorPortalTestData testData;

    @BeforeTest
    @Parameters({ "testDataPath" })
    public void setPageObjects(String testDataPath) {
        System.out.println("Using testDataPath for VendorPortal: " + testDataPath);

        // Debugging: Cek apakah file ada
        File file = new File(testDataPath);
        System.out.println("File path: " + file.getAbsolutePath());
        System.out.println("File exists: " + file.exists());
        this.loginPage = new LoginPage(driver);
        this.dasboardPage = new DasboardPage(driver);
        this.testData = JsonUtil.getTestData(testDataPath, VendorPortalTestData.class);
    }

    @Test
    public void loginTest() {
        loginPage.goTo(Config.get(Constants.VENDOR_PORTAL_URL));
        Assert.assertTrue(loginPage.isAt());
        loginPage.login(testData.username(), testData.password());

    }

    @Test(dependsOnMethods = "loginTest")
    public void dashboardTest() {
        DasboardPage dashboardPage = new DasboardPage(driver);
        Assert.assertTrue(dashboardPage.isAt());

        Assert.assertEquals(dashboardPage.getMonthlyEarning(), testData.monthlyEarning());
        Assert.assertEquals(dashboardPage.getAnnualEarning(), testData.annualEarning());
        Assert.assertEquals(dashboardPage.getProfitEarning(), testData.profitMargin());
        Assert.assertEquals(dashboardPage.getAvailableInventory(), testData.availableInventory());

        dashboardPage.searchOrderHistoryBy(testData.searchKeyword());

        Assert.assertEquals(dashboardPage.getSearchResultsCount(), testData.searchResultCount());

    }

    @Test(dependsOnMethods = "dashboardTest")
    public void logoutTest() {
        dasboardPage.logout();
        Assert.assertTrue(loginPage.isAt());

    }

}
