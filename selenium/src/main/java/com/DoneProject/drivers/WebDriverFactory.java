package com.DoneProject.drivers;

import org.openqa.selenium.WebDriver;

public class WebDriverFactory {

    private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public static AbstractDriver getDriver(String browser) {

        return switch (browser.toLowerCase()) {
            case "chrome" -> new ChromeFactory();
            case "edge" -> new EdgeFactory();
            case "firefox" -> new FirefoxFactory();
            default -> throw new IllegalArgumentException("Browser not supported: " + browser);
        };
    }

    public static WebDriver initDriver(String browser) {

        WebDriver driver = getDriver(browser).createDriver();

        driverThreadLocal.set(driver);

        return driver;
    }

    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

//    public static void quitDriver() {
//
//        if (driverThreadLocal.get() != null) {
//
//            driverThreadLocal.get().quit();
//            driverThreadLocal.remove();
//        }
//    }
}