package com.example.tests.vendorPortal;

import com.example.tests.BaseTest;
import com.example.tests.vendorPortal.model.VendorPortalTestData;
import org.example.Pages.vendorportal.dashboardPage;
import org.example.Pages.vendorportal.loginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.Config;
import utils.Constants;

import java.io.IOException;

/*test data from json file*/
public class VendorPortalTest extends BaseTest {
        private loginPage loginPage;
    private dashboardPage dashboardPage;
    private VendorPortalTestData testData;

    @BeforeTest
    @Parameters("testDataPath") // to get the json
    public void setPageObjects(String testDataPath) throws IOException {
        this.loginPage = new loginPage(webDriver);
        this.dashboardPage = new dashboardPage(webDriver);
        this.testData = utils.JsonUtils.getTestData(testDataPath, VendorPortalTestData.class); // read the json file and store it in testData object
    }

    @Test
    public void loginTest(){
        loginPage.goToUrl(Config.getProperty(Constants.VENDOR_PORTAL_URL));
        Assert.assertTrue(loginPage.isPageLoaded());
        loginPage.login(testData.username(), testData.password());
    }

    @Test(dependsOnMethods = "loginTest")
    public void dashboardTest(){
        Assert.assertTrue(dashboardPage.isPageLoaded());
        Assert.assertEquals(dashboardPage.getMonthlyEarning(), testData.monthlyEarnings());
        Assert.assertEquals(dashboardPage.getAnnualEarning(), testData.annualEarnings());
        Assert.assertEquals(dashboardPage.getProfitMargin(), testData.profitMargins());
        Assert.assertEquals(dashboardPage.getAvailableInventory(), testData.availableInventory());
        dashboardPage.searchInDataTable(testData.searchKeyword());
        Assert.assertEquals(dashboardPage.searchResultCount(),testData.searchResult());
        dashboardPage.logout();
        Assert.assertTrue(loginPage.isPageLoaded());
    }
}
