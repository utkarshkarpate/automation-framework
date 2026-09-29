package org.example.Pages.fightreservation;

import org.example.Pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class FlightSearchPage extends BasePage {

    public FlightSearchPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isPageLoaded() {
        this.wait.until(ExpectedConditions.visibilityOf(this.passengersSelect));
        return this.passengersSelect.isDisplayed();
    }

    @FindBy(id = "passengers")
    private WebElement passengersSelect;

    @FindBy(xpath = "//button[@id = 'search-flights']")
    private WebElement searchFlightsButton;


    public void selectPassengers(String passengers) {
        Select select = new Select(this.passengersSelect);
        select.selectByVisibleText(passengers);
    }

    public void clickSearchFlightsButton() {
        new WebDriverWait(this.driver, Duration.ofSeconds(10))

                .until(ExpectedConditions.and(
                        ExpectedConditions.visibilityOf(this.searchFlightsButton),
                        ExpectedConditions.elementToBeClickable(this.searchFlightsButton)
                ));
        this.searchFlightsButton.click();
    }

}
