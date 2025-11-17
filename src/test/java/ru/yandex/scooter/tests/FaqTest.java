package ru.yandex.scooter.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import ru.yandex.scooter.pageobjects.MainPage;
import ru.yandex.scooter.utils.BrowserFactory;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class FaqTest {
    private WebDriver driver;
    private MainPage mainPage;

    private final String browser;
    private final int questionIndex;
    private final String expectedTextContains;

    public FaqTest(String browser, int questionIndex, String expectedTextContains) {
        this.browser = browser;
        this.questionIndex = questionIndex;
        this.expectedTextContains = expectedTextContains;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {"chrome", 0, "сутки"},
                {"chrome", 1, "покататься"},
                {"chrome", 2, "один день"},
                {"chrome", 3, "заказ"},
                {"chrome", 4, "продлить"},
                {"chrome", 5, "заряда"},
                {"chrome", 6, "отменить"},
                {"chrome", 7, "Москве"},

                {"firefox", 0, "сутки"},
                {"firefox", 1, "покататься"},
                {"firefox", 2, "один день"},
                {"firefox", 3, "заказ"},
                {"firefox", 4, "продлить"},
                {"firefox", 5, "заряда"},
                {"firefox", 6, "отменить"},
                {"firefox", 7, "Москве"}
        });
    }

    @Before
    public void setUp() {
        driver = BrowserFactory.createDriver(browser);
        mainPage = new MainPage(driver);
        mainPage.open();
    }

    @Test
    public void testFaqQuestionExpands() {
        // Проверяем, что ответ изначально не виден
        String initialAnswer = mainPage.getFaqAnswerText(questionIndex);
        assertTrue("Answer should be empty initially", initialAnswer.isEmpty());

        // Кликаем на вопрос
        mainPage.clickFaqQuestion(questionIndex);

        // Проверяем, что ответ появился и содержит ожидаемый текст
        String actualAnswer = mainPage.getFaqAnswerText(questionIndex);
        assertFalse("Answer should not be empty after click", actualAnswer.isEmpty());
        assertTrue("Answer should contain: " + expectedTextContains + ", but was: " + actualAnswer,
                actualAnswer.contains(expectedTextContains));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}