package com.example.genesystestszz.tests.E2E;

import com.example.genesystestszz.tests.DriverSetup;
import com.example.genesystestszz.tests.TestConstants;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.log4j.BasicConfigurator;
import org.openqa.selenium.*;
import org.junit.jupiter.api.*;
import org.apache.log4j.Logger;
import org.openqa.selenium.interactions.Actions;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class E2eTests {
    private static WebDriver driver;
    private static DriverSetup driverSetup;
    private static final Logger log = Logger.getLogger(E2eTests.class);

    @BeforeAll
    static void setup() {
        driverSetup = new DriverSetup();
        driver = driverSetup.initializeWebDriver();
        BasicConfigurator.configure();
    }

    @Test
    public void case1() {
        log.info("Load credentials from the JSON file");
        JsonObject credentials = getCredentials(TestConstants.LOGIN_JSON);
        JsonObject checkoutData = getCredentials(TestConstants.CHECKOUT_DATA_JSON);

        loginWithJson(credentials);

        log.info("Add elements to cart");
        addItemsToCart(TestConstants.ELEMENTS_TO_BUY);

        log.info("Validate the number of elements");
        var shoppingCartNumber = driver.findElement(By.xpath("//*[@id=\"shopping_cart_container\"]/a/span"));
        Assertions.assertEquals(TestConstants.EXPECTED_ELEMENT_COUNT,
                shoppingCartNumber.getText(),
                "The cart value is not as expected");

        log.info("Go through checkout process");
        shoppingCartNumber.click();
        driver.findElement(By.id(TestConstants.CHECKOUT_BUTTON)).click();
        driver.findElement(By.id(TestConstants.FIRSTNAME_INPUT)).sendKeys(checkoutData.get("firstName").getAsString());
        driver.findElement(By.id(TestConstants.LASTNAME_INPUT)).sendKeys(checkoutData.get("lastName").getAsString());
        driver.findElement(By.id(TestConstants.POSTALCODE_INPUT)).sendKeys(checkoutData.get("zip").getAsString());
        driver.findElement(By.id(TestConstants.CONTINUE_BUTTON)).click();
        driver.findElement(By.id(TestConstants.FINISH_BUTTON)).click();
        Assertions.assertEquals(TestConstants.EXPECTED_CHECKOUT_MESSAGE,
                driver.findElement(By.xpath("//*[@id=\"checkout_complete_container\"]/h2")).getText(),
                "Message is not as expected");
        teardown();
    }

    @Test
    public void case2() {
        setup();
        JsonObject credentials = getCredentials(TestConstants.LOGIN_JSON2);
        log.info("Navigate to the login page");
        driver.get(TestConstants.SHOPPING_URL);
        log.info("Click on login and validate error message");
        driver.findElement(By.id(TestConstants.LOGIN_BUTTON)).click();
        Assertions.assertEquals(TestConstants.EXPECTED_LOGIN_ERROR_MESSAGE,
                driver.findElement(
                        By.xpath("//*[@id=\"login_button_container\"]/div/form/div[3]/h3")).getText(),
                "Error message is not as expected");
        log.info("login with credentials");
        loginWithJson(credentials);
        log.info("validate footer");
        var footerText = driver.findElement(By.xpath("//*[@id=\"page_wrapper\"]/footer/div")).getText();
        Assertions.assertTrue(footerText.contains(TestConstants.EXPECTED_FOOTER_TERMS_TEXT),
                "Footer doesn't contains: " + TestConstants.EXPECTED_FOOTER_TERMS_TEXT);
        Assertions.assertTrue(footerText.contains(TestConstants.EXPECTED_FOOTER_YEAR_TEXT),
                "Footer doesn't contains: " + TestConstants.EXPECTED_FOOTER_YEAR_TEXT);
    }

    @Test
    public void case3() {
        log.info("Navigate to the rich text page");
        driver.get(TestConstants.RICHTEXT_URL);
        var boldButton = driver.findElement(By.id(TestConstants.BOLD_BUTTON_ID));
        var underlinedButton = driver.findElement(By.id(TestConstants.UNDERLINED_BUTTON_ID));

        log.info("Check if text editor buttons are in default state.");
        if(boldButton.isSelected()){
            boldButton.click();
        }
        if(underlinedButton.isSelected()){
            underlinedButton.click();
        }

        log.info("Switch to the editor");
        driver.switchTo().frame(0);

        var textEditor = driver.findElement(By.xpath("/html/body/p"));

        log.info("Write text in editor");
        textEditor.sendKeys(Keys.chord(Keys.CONTROL, "b"));
        textEditor.sendKeys(TestConstants.TEXT_BOLD + " ");
        textEditor.sendKeys(Keys.chord(Keys.CONTROL, "b"));
        textEditor.sendKeys(Keys.chord(Keys.CONTROL, "u"));
        textEditor.sendKeys(TestConstants.TEXT_UNDERLINED + " ");
        textEditor.sendKeys(Keys.chord(Keys.CONTROL, "u"));
        textEditor.sendKeys(TestConstants.TEXT_NORMAL);

        log.info("Check text in editor");
        Assertions.assertEquals(driver.findElement(By.xpath(
                "//*[contains(text(), '" + TestConstants.TEXT_BOLD + "')]")).getTagName(), "strong");
        Assertions.assertEquals(driver.findElement(By.xpath(
                "//*[contains(text(), '" + TestConstants.TEXT_UNDERLINED + "')]")).getTagName(), "u");
        var fullText = TestConstants.TEXT_BOLD + " " + TestConstants.TEXT_UNDERLINED + " " + TestConstants.TEXT_NORMAL;
        Assertions.assertEquals(textEditor.getText(), fullText,"The full text is not as expected.");
    }

    @Test
    public void case4() {
        log.info("Navigate to the iframe page");
        driver.get(TestConstants.IFRAME_URL);

        log.info("Switch to GDPR iframe");
        driver.switchTo().frame(TestConstants.GDPR_IFRAME_ID);
        log.info("Close banner message");
        driver.findElement(By.id(TestConstants.SAVE_BUTTON_ID)).click();
        driver.switchTo().defaultContent();
        log.info("Switch to iframe");
        driver.switchTo().frame(TestConstants.IMAGE_IFRAME_ID);
        var imageInIframe = driver.findElement(By.xpath("/html/body/a/img"));

        log.info("Select image");
        imageInIframe.click();

        log.info("switch to the new tab and then close it");
        switchToTabByName(TestConstants.NEWPAGE_NAME);
        driver.close();

        log.info("switch back to main page");
        switchToTabByName(TestConstants.ORIGINALPAGE_NAME);

        log.info("Select selenium in testing dropdown");
        var testingDropdownMenuElement = driver.findElement(
                By.xpath("//*[@id=\"rt-header\"]/div/div[2]/div/ul/li[2]/a"));
        Actions actions = new Actions(driver);
        actions.moveToElement(testingDropdownMenuElement).perform();
        driver.findElement(By.xpath(
                "//*[contains(text(), '" + TestConstants.SELENIUM_DROPDOWN_ITEM + "')]")).click();

        log.info("Switch to Google AD iframe");
        driver.switchTo().frame(TestConstants.GOOGLEAD_IFRAME_ID);
        List<WebElement> closeButton = driver.findElements(By.id(TestConstants.DISMISS_BUTTON_ID));
        //There are 2 types of ads and the dismiss button can be in another iframe
        if(closeButton.isEmpty()){
            log.info("Switch to AD iframe");
            driver.switchTo().frame(TestConstants.AD_IFRAME_ID);
            log.info("Close banner message");
            driver.findElement(By.id(TestConstants.DISMISS_BUTTON_ID)).click();
        }
        else{
            closeButton.get(0).click();
        }

        driver.switchTo().defaultContent();
        var  AWForm = driver.findElement(By.xpath("//*[@class=\"AW-Form-1742405067\"]"));
        actions.moveToElement(AWForm);
        actions.perform();
        var submitButton = driver.findElement(By.xpath(
                "//*[@id=\"af-body-1742405067\"]/div[2]"));
        Assertions.assertTrue(submitButton.isDisplayed(), "Submit button is not displayed");
    }

    private JsonObject getCredentials(String resourcePath) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath);
             InputStreamReader reader = new InputStreamReader(inputStream)) {
            return new Gson().fromJson(reader, JsonObject.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void addItemsToCart(List<String> elementsToBuy){
        for (String element : elementsToBuy) {
            driver.findElement(By.id("add-to-cart-" + element)).click();
        }
    }

    private void loginWithJson(JsonObject credentialJson){
        log.info("Navigate to the login page");
        driver.get(TestConstants.SHOPPING_URL);

        log.info("Fill in the username and password fields with the credentials from the JSON");
        driver.findElement(By.id(TestConstants.USERNAME_INPUT)).sendKeys(credentialJson.get("username").getAsString());
        driver.findElement(By.id(TestConstants.PASSWORD_INPUT)).sendKeys(credentialJson.get("password").getAsString());

        log.info("Submit the login form");
        driver.findElement(By.id(TestConstants.LOGIN_BUTTON)).click();
    }

    private void switchToTabByName(String tabName) {
        for (String windowHandle : driver.getWindowHandles()) {
            driver.switchTo().window(windowHandle);
            if (driver.getTitle().equalsIgnoreCase(tabName)) {
                return;
            }
        }
        Assertions.fail("No window found with name:" + tabName);
    }

    @AfterAll
    public static void teardown() {
        driverSetup.quitDriver();
    }
}
