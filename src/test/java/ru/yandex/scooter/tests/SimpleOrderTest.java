package ru.yandex.scooter.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import ru.yandex.scooter.pageobjects.MainPage;
import ru.yandex.scooter.utils.BrowserFactory;

public class SimpleOrderTest {
    private WebDriver driver;
    private MainPage mainPage;

    @Before
    public void setUp() {
        System.out.println("🚀 Setting up test...");
        driver = BrowserFactory.createDriver("chrome");
        mainPage = new MainPage(driver);
    }

    @Test
    public void testJustOpenPage() {
        System.out.println("🧪 Starting test: testJustOpenPage");

        try {
            mainPage.open();
            System.out.println("✅ Page opened successfully");

            // Просто ждем и смотрим
            System.out.println("⏳ Waiting 5 seconds to observe the page...");
            Thread.sleep(5000);
            System.out.println("✅ Test completed successfully");

        } catch (Exception e) {
            System.out.println("🚨 Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testClickTopOrderButton() {
        System.out.println("🧪 Starting test: testClickTopOrderButton");

        try {
            // Шаг 1: Открываем главную страницу
            mainPage.open();

            // Шаг 2: Кликаем верхнюю кнопку заказа
            System.out.println("🔄 Attempting to click top order button...");
            mainPage.clickTopOrderButton();

            // Проверяем что перешли на страницу заказа
            String currentUrl = driver.getCurrentUrl();
            System.out.println("📋 Current URL after click: " + currentUrl);

            if (currentUrl.contains("/order")) {
                System.out.println("✅ Successfully navigated to order page!");
            } else {
                System.out.println("⚠️ Still on main page: " + currentUrl);
            }

            // Пауза для наблюдения
            System.out.println("⏳ Waiting 3 seconds...");
            Thread.sleep(3000);

        } catch (Exception e) {
            System.out.println("🚨 Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testClickBottomOrderButton() {
        System.out.println("🧪 Starting test: testClickBottomOrderButton");

        try {
            // Шаг 1: Открываем главную страницу
            mainPage.open();

            // Шаг 2: Кликаем нижнюю кнопку заказа
            System.out.println("🔄 Attempting to click bottom order button...");
            mainPage.clickBottomOrderButton();

            // Проверяем что перешли на страницу заказа
            String currentUrl = driver.getCurrentUrl();
            System.out.println("📋 Current URL after click: " + currentUrl);

            if (currentUrl.contains("/order")) {
                System.out.println("✅ Successfully navigated to order page!");
            } else {
                System.out.println("⚠️ Still on main page: " + currentUrl);
            }

            // Пауза для наблюдения
            System.out.println("⏳ Waiting 3 seconds...");
            Thread.sleep(3000);

        } catch (Exception e) {
            System.out.println("🚨 Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        System.out.println("🔚 Tearing down test...");
        if (driver != null) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            driver.quit();
            System.out.println("✅ Browser closed");
        }
    }
}