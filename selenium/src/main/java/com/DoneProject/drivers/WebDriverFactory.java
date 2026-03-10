package com.DoneProject.drivers;

import org.openqa.selenium.WebDriver;

public class WebDriverFactory {


    private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public static AbstractDriver getDriver(String browser) {
        return switch (browser) {
            case "chrome" -> new ChromeFactory();
            case "firefox" -> new FirefoxFactory();
            case "edge" -> new EdgeFactory();
            default ->
                throw new IllegalArgumentException("Browser");
            };
        public static WebDriver initDriver(String browser) {
            WebDriver driver = getDriver(browser).createDriver();
            driverThreadLocal.set(driver);
            return driverThreadLocal.get();
        }
            if (driverThreadLocal.get() == null) {
                throw new IllegalStateException("WebDriver not initialized. Call getDriver(String browser) first.");
            }
            return driverThreadLocal.get();

        }

    }

