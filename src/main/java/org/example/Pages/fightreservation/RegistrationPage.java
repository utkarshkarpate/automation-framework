package org.example.Pages.fightreservation;

import org.example.Pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class RegistrationPage extends BasePage {
    @FindBy(id = "firstName")
    private WebElement firstNameInput;

    @FindBy(id = "lastName")
    private WebElement lastNameInput;

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(name = "street")
    private WebElement streetInput;

    @FindBy(name = "city")
    private WebElement cityInput;

    @FindBy(name = "zip")
    private WebElement zipInput;

    @FindBy(id = "register-btn")
    private WebElement registerButton;

    public RegistrationPage(WebDriver driver){
        super(driver);

    }

    public void goToUrl(String url){
        driver.get(url);
    }

    public void enterUserDetails(String fname, String lname){
        this.firstNameInput.sendKeys(fname);
        this.firstNameInput.sendKeys(lname);
    }

    public void userCredentials(String email, String password){
        this.emailInput.sendKeys(email);
        this.passwordInput.sendKeys(password);
    }

    public void enterAddressDetails(String street, String city, String zip){
        this.streetInput.sendKeys(street);
        this.cityInput.sendKeys(city);
        this.zipInput.sendKeys(zip);
    }

    public void clickRegisterButton(){
        this.registerButton.click();
    }

    @Override
    public boolean isPageLoaded() {
        this.wait.until(ExpectedConditions.visibilityOf(this.firstNameInput));
        return this.firstNameInput.isDisplayed()   ;
    }
}
