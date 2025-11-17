package ru.yandex.scooter.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import ru.yandex.scooter.pageobjects.MainPage;
import ru.yandex.scooter.utils.BrowserFactory;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class FaqTest {
    private WebDriver driver;
    private MainPage mainPage;
    private final int questionIndex;
    private final String expectedAnswerContains;

    public FaqTest(int questionIndex, String expectedAnswerContains) {
        this.questionIndex = questionIndex;
        this.expectedAnswerContains = expectedAnswerContains;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][] {
                {0, "Сутки — 400 рублей"},
                {1, "один заказ — один самокат"},
                {2, "Отсчёт времени аренды начинается с момента"},
                {3, "Только начиная с завтрашнего дня"},
                {4, "Пока что нет"},
                {5, "Самокат приезжает к вам с полной зарядкой"},
                {6, "Штрафа не будет"},
                {7, "Да, обязательно"}
        };
    }

    @Before
    public void setUp() {
        driver = BrowserFactory.createDriver("chrome");
        mainPage = new MainPage(driver);
    }

    @Test
    public void testFaqAnswer() {
        mainPage.open();
        
        int questionsCount = mainPage.getFaqQuestionsCount();
        assertTrue("Должно быть не менее 8 вопросов в FAQ", questionsCount >= 8);
        
        mainPage.clickFaqQuestion(questionIndex);
        
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        String actualAnswer = mainPage.getFaqAnswerText(questionIndex);
        assertTrue("Для вопроса " + questionIndex + " ответ должен содержать: '" + expectedAnswerContains + "', но получили: '" + actualAnswer + "'",
                   actualAnswer.contains(expectedAnswerContains));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
