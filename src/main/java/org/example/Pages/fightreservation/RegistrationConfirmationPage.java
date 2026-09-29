package org.example.Pages.fightreservation;

import org.example.Pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class RegistrationConfirmationPage extends BasePage {

    @FindBy(xpath = "//h2[text() = 'Registration Confirmation']")
    private WebElement confirmationMessage;

    @FindBy(id = "go-to-flights-search")
    private WebElement goToFlightsSearchButton;

    public RegistrationConfirmationPage(WebDriver driver){
        super(driver);
    }

    public void verifyConfirmationPage() {
        this.confirmationMessage.isDisplayed();
    }

    public void clickGoToFlightsSearch() {
        this.goToFlightsSearchButton.click();
    }

    @Override
    public boolean isPageLoaded() {
        this.wait.until(ExpectedConditions.visibilityOf(this.goToFlightsSearchButton));
        return this.goToFlightsSearchButton.isDisplayed()   ;
    }
}
