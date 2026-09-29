package org.example.Pages.fightreservation;

import org.example.Pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FlightsConfirmationPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(FlightsConfirmationPage.class);
    public FlightsConfirmationPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//h2[text() = 'Flights Confirmation']")
    private WebElement filghtConfirmationHeader;

    @FindBy(css = "#flights-confirmation-section .card-body .row:nth-child(1) .col:nth-child(2)")
    private WebElement flightConfirmationNumber;

    @FindBy(css = "#flights-confirmation-section .card-body .row:nth-child(3) .col:nth-child(2)")
    private WebElement totalPrice;
    @Override
    public boolean isPageLoaded() {
        this.wait.until(ExpectedConditions.visibilityOf(this.filghtConfirmationHeader));
        return this.filghtConfirmationHeader.isDisplayed();
    }

    public String getPrice(){

        String confirmationNumber = this.flightConfirmationNumber.getText();
        String totalPrice = this.totalPrice.getText();
        logger.atInfo().log("Flight Confirmation: {}", confirmationNumber);
        logger.atInfo().log("Flight price: {}", totalPrice);
        return totalPrice;
    }
}
