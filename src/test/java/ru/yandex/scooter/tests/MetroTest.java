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

public class MetroTest {
    private WebDriver driver;
    private MainPage mainPage;
    
    @Before
    public void setUp() {
        System.out.println("🚀 Setting up MetroTest...");
        driver = BrowserFactory.createDriver("chrome");
        mainPage = new MainPage(driver);
    }
    
    @Test
    public void testMetroSelectionDebug() {
        System.out.println("🧪 Testing metro station selection debug...");
        
        try {
            mainPage.open();
            mainPage.clickTopOrderButton();
            
            // Ждем загрузки формы
            Thread.sleep(3000);
            
            // Кликаем на поле метро
            WebElement metroField = driver.findElement(By.xpath("//input[@placeholder='* Станция метро']"));
            System.out.println("📍 Found metro field");
            metroField.click();
            
            Thread.sleep(2000);
            
            // Ищем все элементы в выпадающем списке
            List<WebElement> allOptions = driver.findElements(By.xpath("//div[contains(@class, 'select-search__option')] | //div[@class='Order_Text__2broi']"));
            System.out.println("📊 Found " + allOptions.size() + " options in dropdown");
            
            for (int i = 0; i < allOptions.size(); i++) {
                WebElement option = allOptions.get(i);
                System.out.println("   Option " + i + ": '" + option.getText() + "'");
                System.out.println("      Class: " + option.getAttribute("class"));
            }
            
            // Пробуем выбрать первую станцию
            if (!allOptions.isEmpty()) {
                System.out.println("🖱️ Clicking first option: '" + allOptions.get(0).getText() + "'");
                allOptions.get(0).click();
                Thread.sleep(2000);
                System.out.println("✅ Metro station selected");
            }
            
            Thread.sleep(3000);
            
        } catch (Exception e) {
            System.out.println("🚨 Metro selection debug failed: " + e.getMessage());
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
