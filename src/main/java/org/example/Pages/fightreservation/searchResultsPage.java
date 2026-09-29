package org.example.Pages.fightreservation;

import org.example.Pages.BasePage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class searchResultsPage extends BasePage {

    public searchResultsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//h2[text() = 'Select Flights']")
    private WebElement selectFlightsHeader;

    @FindBy(name = "departure-flight")
    private List<WebElement> departureFlights;

    @FindBy(name = "arrival-flight")
    private List<WebElement> arrivalFlights;

    @FindBy(id = "confirm-flights")
    private WebElement confirmFlightsButton;

    @Override
    public boolean isPageLoaded() {
        this.wait.until(ExpectedConditions.visibilityOf(this.selectFlightsHeader));
        return this.selectFlightsHeader.isDisplayed();
    }

    public void clickFlightButton(){
            try {
                new WebDriverWait(this.driver, Duration.ofSeconds(10))
                        .until(ExpectedConditions.and(
                                ExpectedConditions.visibilityOf(this.confirmFlightsButton),
                                ExpectedConditions.elementToBeClickable(this.confirmFlightsButton)
                        ));
                this.confirmFlightsButton.click();
            } catch (org.openqa.selenium.ElementClickInterceptedException e) {
                ((JavascriptExecutor) this.driver).executeScript("arguments[0].click();", this.confirmFlightsButton);
            }
        }

    public void selectDepartureFlight(){
        new WebDriverWait(this.driver, Duration.ofSeconds(20))

                .until(ExpectedConditions.and(
                        ExpectedConditions.visibilityOf(this.confirmFlightsButton),
                        ExpectedConditions.elementToBeClickable(this.confirmFlightsButton)
                ));
        int randomIndex = (int) (Math.random() * departureFlights.size());
        departureFlights.get(randomIndex).click();
    }

    public void selectArrivalFlight(){
        new WebDriverWait(this.driver, Duration.ofSeconds(20))

                .until(ExpectedConditions.and(
                        ExpectedConditions.visibilityOf(this.confirmFlightsButton),
                        ExpectedConditions.elementToBeClickable(this.confirmFlightsButton)
                ));
        int randomIndex = (int) (Math.random() * arrivalFlights.size());
        arrivalFlights.get(randomIndex).click();
    }
}
