package com.example.genesystestszz.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverSetup {
    private WebDriver driver;

    public WebDriver initializeWebDriver() {
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(false);
        options.addArguments("start-maximized"); // open Browser in maximized mode
        options.addArguments("disable-infobars"); // disabling infobars
        options.addArguments("--disable-extensions"); // disabling extensions
        options.addArguments("--disable-gpu"); // applicable to Windows os only
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.addArguments("--no-sandbox"); // Bypass OS security model
        options.addArguments("--disable-in-process-stack-traces");
        options.addArguments("--disable-logging");
        options.addArguments("--log-level=3");
        options.addArguments("--remote-allow-origins=*");

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);

        return driver;
    }

    public void quitDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}