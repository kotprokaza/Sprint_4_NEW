package ru.yandex.scooter.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.yandex.scooter.pageobjects.MainPage;
import ru.yandex.scooter.utils.BrowserFactory;

import java.util.List;

public class DebugTest {
    private WebDriver driver;
    private MainPage mainPage;

    @Before
    public void setUp() {
        System.out.println("🚀 Setting up debug test...");
        driver = BrowserFactory.createDriver("chrome");
        mainPage = new MainPage(driver);
    }

    @Test
    public void debugFindOrderButtons() {
        System.out.println("🔍 Debug: Finding order buttons on page");

        try {
            mainPage.open();

            // Ищем все кнопки с текстом "Заказать"
            List<WebElement> orderButtons = driver.findElements(By.xpath("//button[contains(text(), 'Заказать')]"));
            System.out.println("📊 Found " + orderButtons.size() + " order buttons on page");

            for (int i = 0; i < orderButtons.size(); i++) {
                WebElement button = orderButtons.get(i);
                System.out.println("Button " + i + ":");
                System.out.println("  - Text: " + button.getText());
                System.out.println("  - Class: " + button.getAttribute("class"));
                System.out.println("  - Displayed: " + button.isDisplayed());
                System.out.println("  - Enabled: " + button.isEnabled());
            }

            // Ищем по классу
            List<WebElement> classButtons = driver.findElements(By.className("Button_Button__ra12g"));
            System.out.println("📊 Found " + classButtons.size() + " buttons with class 'Button_Button__ra12g'");

            // Ждем чтобы посмотреть
            Thread.sleep(5000);

        } catch (Exception e) {
            System.out.println("🚨 Debug test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}