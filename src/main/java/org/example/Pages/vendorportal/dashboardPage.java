package org.example.Pages.vendorportal;

import org.example.Pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class dashboardPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(dashboardPage.class);
    public dashboardPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "monthly-earning")
    private WebElement monthlyEarningElement;

    @FindBy(id = "annual-earning")
    private WebElement annualEarningElement;

    @FindBy(id = "profit-margin")
    private WebElement profitMarginElement;

    @FindBy(id = "available-inventory")
    private WebElement availableInventoryElement;

    @FindBy(css = "#dataTable_filter input")
    private WebElement searchInput;

    @FindBy(id = "dataTable_info")
    private WebElement searchResultsInfo;

    @FindBy(css = "#userDropdown img")
    private WebElement userProfileImage;

    @FindBy(linkText = "Logout")
    private WebElement logoutLink;

    @FindBy(xpath = "//h1[text() = 'Dashboard']")
    private WebElement dashboardHeader;

    @FindBy(css = "#logoutModal a")
    private WebElement confirmLogoutButton;

    @Override
    public boolean isPageLoaded() {
        // Implement logic to check if the dashboard page is loaded
        this.wait.until(ExpectedConditions.visibilityOf(this.dashboardHeader));
        return this.dashboardHeader.isDisplayed();
    }

    public String getMonthlyEarning() {
        this.wait.until(ExpectedConditions.visibilityOf(this.monthlyEarningElement));
        return this.monthlyEarningElement.getText();
    }

    public String getAnnualEarning() {
        this.wait.until(ExpectedConditions.visibilityOf(this.annualEarningElement));
        return this.annualEarningElement.getText();
    }

    public String getProfitMargin() {
        this.wait.until(ExpectedConditions.visibilityOf(this.profitMarginElement));
        return this.profitMarginElement.getText();
    }

    public String getAvailableInventory() {
        this.wait.until(ExpectedConditions.visibilityOf(this.availableInventoryElement));
        return this.availableInventoryElement.getText();
    }

    public void searchInDataTable(String searchTerm) {
        this.wait.until(ExpectedConditions.visibilityOf(this.searchInput));
        this.searchInput.sendKeys(searchTerm);
    }

    public int searchResultCount(){
//Split based on space in the String
        String infoText = this.searchResultsInfo.getText();
        String[] parts = infoText.split(" ");
        logger.info("Search result info text: {}", infoText);
        logger.info("Search result info text: {}", Integer.parseInt(parts[5]));
        return Integer.parseInt(parts[5]);
    }

    public void logout(){
        this.wait.until(ExpectedConditions.visibilityOf(this.userProfileImage));
        this.userProfileImage.click();
        this.wait.until(ExpectedConditions.visibilityOf(this.logoutLink));
        this.logoutLink.click();
        this.wait.until(ExpectedConditions.visibilityOf(this.confirmLogoutButton));
        this.confirmLogoutButton.click();
    }
}
