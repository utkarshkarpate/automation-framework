package org.example.Pages.vendorportal;

import org.example.Pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class loginPage extends BasePage {
    public loginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "username")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(xpath = "//h1[contains(text(),  'Welcome Back')]")
    private WebElement welcomeBackMessage;

    @FindBy(id = "login")
    private WebElement loginButton;

    public void goToUrl(String url) {
        driver.get(url);
    }

    @Override
    public boolean isPageLoaded() {
        this.wait.until(ExpectedConditions.visibilityOf(this.welcomeBackMessage));
        return welcomeBackMessage.isDisplayed();
    }

    public void login(String username, String password) {
        this.usernameInput.sendKeys(username);
        this.passwordInput.sendKeys(password);
        this.loginButton.click();
    }
}
