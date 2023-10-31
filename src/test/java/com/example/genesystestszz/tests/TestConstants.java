package com.example.genesystestszz.tests;

import java.util.*;

public class TestConstants {
    // URLs
    public static final String SHOPPING_URL = "https://www.saucedemo.com/inventory.html";
    public static final String RICHTEXT_URL = "https://onlinehtmleditor.dev/";
    public static final String IFRAME_URL = "http://demo.guru99.com/test/guru99home";

    // Web element locators
    public static final String USERNAME_INPUT = "user-name";
    public static final String PASSWORD_INPUT = "password";
    public static final String LOGIN_BUTTON = "login-button";
    public static final List<String> ELEMENTS_TO_BUY = Arrays.asList("sauce-labs-backpack", "sauce-labs-fleece-jacket");
    public static final String CHECKOUT_BUTTON = "checkout";
    public static final String FIRSTNAME_INPUT = "first-name";
    public static final String LASTNAME_INPUT = "last-name";
    public static final String POSTALCODE_INPUT = "postal-code";
    public static final String CONTINUE_BUTTON = "continue";
    public static final String FINISH_BUTTON = "finish";
    public static final String BOLD_BUTTON_ID = "cke_19";
    public static final String UNDERLINED_BUTTON_ID = "cke_21";
    public static final String GDPR_IFRAME_ID = "gdpr-consent-notice";
    public static final String IMAGE_IFRAME_ID = "a077aa5e";
    public static final String GOOGLEAD_IFRAME_ID = "google_ads_iframe_/24132379/INTERSTITIAL_DemoGuru99_0";
    public static final String AD_IFRAME_ID = "ad_iframe";
    public static final String NEWPAGE_NAME = "Selenium Live Project: FREE Real Time Project for Practice";
    public static final String ORIGINALPAGE_NAME = "Demo Guru99 Page";
    public static final String SAVE_BUTTON_ID = "save";
    public static final String DISMISS_BUTTON_ID = "dismiss-button";
    public static final String SELENIUM_DROPDOWN_ITEM = "Selenium";

    // Expected values
    public static final String EXPECTED_ELEMENT_COUNT = "2";
    public static final String EXPECTED_CHECKOUT_MESSAGE = "Thank you for your order!";
    public static final String EXPECTED_LOGIN_ERROR_MESSAGE = "Epic sadface: Username is required";
    public static final String EXPECTED_FOOTER_TERMS_TEXT = "Terms of Service";
    public static final String EXPECTED_FOOTER_YEAR_TEXT = "2023";
    public static final String TEXT_BOLD = "Automation";
    public static final String TEXT_UNDERLINED = "Test";
    public static final String TEXT_NORMAL = "Example";
    //Json files
    public static final String LOGIN_JSON = "credential.json";
    public static final String LOGIN_JSON2 = "credential2.json";
    public static final String CHECKOUT_DATA_JSON = "checkoutData.json";
}
