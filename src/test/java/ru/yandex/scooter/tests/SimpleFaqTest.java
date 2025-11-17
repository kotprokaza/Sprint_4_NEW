package ru.yandex.scooter.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.yandex.scooter.pageobjects.MainPage;
import ru.yandex.scooter.utils.BrowserFactory;

import java.util.List;

public class SimpleFaqTest {
    private WebDriver driver;
    private MainPage mainPage;

    @Before
    public void setUp() {
        System.out.println("🚀 Setting up SimpleFaqTest...");
        driver = BrowserFactory.createDriver("chrome");
        mainPage = new MainPage(driver);
    }

    @Test
    public void testFaqBasic() {
        System.out.println("🧪 Testing FAQ basic functionality...");

        try {
            // Открываем главную страницу
            mainPage.open();

            // Ждем немного
            Thread.sleep(2000);

            // Получаем вопросы
            List<WebElement> questions = mainPage.getFaqQuestions();
            System.out.println("📊 Found " + questions.size() + " FAQ questions");

            // Проверяем первый вопрос
            if (questions.size() > 0) {
                System.out.println("🔍 Testing first FAQ question...");

                // Кликаем на первый вопрос
                mainPage.clickFaqQuestion(0);

                // Ждем
                Thread.sleep(2000);

                // Получаем текст ответа
                String answerText = mainPage.getFaqAnswerText(0);
                System.out.println("📋 Answer text: '" + answerText + "'");

                if (!answerText.isEmpty()) {
                    System.out.println("✅ FAQ question expands successfully!");
                } else {
                    System.out.println("❌ FAQ answer is empty");
                }
            }

        } catch (Exception e) {
            System.out.println("🚨 SimpleFaqTest failed: " + e.getMessage());
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