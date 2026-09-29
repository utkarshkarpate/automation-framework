package com.example.tests;

import com.example.Listener.TestListener;
import com.google.common.util.concurrent.Uninterruptibles;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.*;
import utils.Config;
import utils.Constants;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

@Listeners(TestListener.class) //this is used here so that our test can listen to the events
public abstract class BaseTest {
    protected WebDriver webDriver;
    public static final Logger logger = LoggerFactory.getLogger(BaseTest.class);


    @BeforeSuite
    public void initConfig() {
        Config.init();

    }

    @BeforeTest
    public void setDriver(ITestContext ctx) throws MalformedURLException {
        /*
        ItestContext will give all the information about the current test being executed
        Each and every webdriver in parallel execution will have its own information and be
        stored in this context
         */



        /*if(Boolean.parseBoolean(Config.getProperty("selenium.grid.enabled"))){
            this.webDriver = getRemoteDriver();
            webDriver.manage().window().maximize();
        }
        else{
            this.webDriver = getLocalDriver();
            webDriver.manage().window().maximize();
        }*/

        //this can be written as below as well. It's a ternary operator. If the condition is true, it will execute the first statement, else it will execute the second statement.
        this.webDriver = Boolean.parseBoolean(Config.getProperty(Constants.GRID_ENABLED)) ? getRemoteDriver() : getLocalDriver();
        ctx.setAttribute(Constants.DRIVER, webDriver);
        webDriver.manage().window().maximize();
    }

//    this we have done so that we can run in local and selenium grid
    private WebDriver getLocalDriver(){
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver() ;
    }

    //When the property for selenium.grid.enabled is true, we will execute in remote browser, else we will execute in local browser
    private WebDriver getRemoteDriver() throws MalformedURLException {
        Capabilities capabilities = new ChromeOptions();
        if(Constants.FIREFOX.equalsIgnoreCase(Config.getProperty(Constants.BROWSER))){
            capabilities = new FirefoxOptions();
        }
        String urlFormat = Config.getProperty(Constants.GRID_URL_FORMAT);
        String hubHost = Config.getProperty(Constants.GRID_HUB_HOST);
        String url = String.format(urlFormat, hubHost);
        logger.info("Connecting to Selenium Grid at: " + url);

        return new RemoteWebDriver(new URL(url), capabilities);
    }

    @AfterTest
    public void closeBrowser() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }

    @AfterMethod(enabled = false) // set enabled to true if you want to see the browser before it closes
    public void sleep(){
        Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(5));
        /*
        using this method to sleep after each test method execution.
        This is useful for debugging purposes, as it allows you to see the state of the
        browser before it closes. However, in a real test suite, you might want to remove this or
         make it conditional based on a debug flag.

         using this, we can see live execution in our dockerized containers.

         Once we run the suite, go to the grid, go to sessions and open the session to see the test running
         it will ask for password, password is "secret"
         if we have to disable the password, make the below change sin docker-compose file
         SE_VNC_NO_PASSWORD= 1
         */
    }
}
