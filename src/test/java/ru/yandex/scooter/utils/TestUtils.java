package ru.yandex.scooter.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TestUtils {

    public static void takeScreenshot(WebDriver driver, String testName) {
        try {
            if (driver instanceof TakesScreenshot) {
                TakesScreenshot ts = (TakesScreenshot) driver;
                byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
                System.out.println("📸 Screenshot taken for: " + testName);
            }
        } catch (Exception e) {
            System.out.println("❌ Cannot take screenshot: " + e.getMessage());
        }
    }

    public static void waitForPageLoad(WebDriver driver, WebDriverWait wait) {
        try {
            wait.until(webDriver ->
                    ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
            System.out.println("✅ Page loaded completely");
        } catch (Exception e) {
            System.out.println("⚠️ Page load timeout: " + e.getMessage());
        }
    }

    public static boolean isElementPresent(WebDriver driver, By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public static void printPageInfo(WebDriver driver) {
        System.out.println("🌐 Current URL: " + driver.getCurrentUrl());
        System.out.println("📄 Page title: " + driver.getTitle());
        System.out.println("📊 Page source length: " + driver.getPageSource().length());
    }

    public static void safeClick(WebDriver driver, WebElement element) {
        try {
            element.click();
        } catch (Exception e) {
            System.out.println("🔄 Standard click failed, trying JS click...");
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", element);
        }
    }

    public static void waitAndClick(WebDriver driver, WebDriverWait wait, By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        safeClick(driver, element);
    }
}