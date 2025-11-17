package ru.yandex.scooter.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.scooter.config.AppConfig;

import java.time.Duration;
import java.util.List;

public class MainPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Локаторы для кнопок заказа
    private final By orderButtonTop = By.className("Button_Button__ra12g");
    private final By orderButtonBottom = By.xpath("//button[text()='Заказать']");

    // Локаторы для FAQ - пробуем разные варианты
    private final By faqSection = By.className("Home_FAQ__3uVm4");
    private final By faqQuestions = By.xpath("//div[contains(@class, 'accordion__button')]");
    private final By faqAnswers = By.xpath("//div[contains(@class, 'accordion__panel')]");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void open() {
        System.out.println("🔄 Opening main page: " + AppConfig.BASE_URL);
        driver.get(AppConfig.BASE_URL);

        // Ждем загрузки страницы
        wait.until(webDriver ->
                ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        System.out.println("✅ Main page loaded");
    }

    public void clickTopOrderButton() {
        System.out.println("🔄 Looking for TOP order button...");
        try {
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(orderButtonTop));
            System.out.println("✅ Found TOP order button");

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
            Thread.sleep(1000);

            System.out.println("🖱️ Clicking TOP order button...");
            button.click();
            System.out.println("✅ TOP order button clicked");

            Thread.sleep(2000);

        } catch (Exception e) {
            System.out.println("❌ Failed to click TOP order button: " + e.getMessage());
            throw new RuntimeException("Cannot click top order button", e);
        }
    }

    public void clickBottomOrderButton() {
        System.out.println("🔄 Looking for BOTTOM order button...");
        try {
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
            Thread.sleep(1000);

            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(orderButtonBottom));
            System.out.println("✅ Found BOTTOM order button");

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
            Thread.sleep(1000);

            System.out.println("🖱️ Clicking BOTTOM order button...");
            button.click();
            System.out.println("✅ BOTTOM order button clicked");

            Thread.sleep(2000);

        } catch (Exception e) {
            System.out.println("❌ Failed to click BOTTOM order button: " + e.getMessage());
            throw new RuntimeException("Cannot click bottom order button", e);
        }
    }

    // Методы для FAQ
    public List<WebElement> getFaqQuestions() {
        try {
            // Ждем появления FAQ секции
            wait.until(ExpectedConditions.visibilityOfElementLocated(faqSection));

            // Скроллим к FAQ
            WebElement faqSectionElement = driver.findElement(faqSection);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", faqSectionElement);
            Thread.sleep(1000);

            // Ищем вопросы
            List<WebElement> questions = driver.findElements(faqQuestions);
            System.out.println("🔍 Found " + questions.size() + " FAQ questions");
            return questions;
        } catch (Exception e) {
            System.out.println("❌ Error getting FAQ questions: " + e.getMessage());
            return new java.util.ArrayList<>();
        }
    }

    public List<WebElement> getFaqAnswers() {
        try {
            return driver.findElements(faqAnswers);
        } catch (Exception e) {
            System.out.println("❌ Error getting FAQ answers: " + e.getMessage());
            return new java.util.ArrayList<>();
        }
    }

    public void clickFaqQuestion(int index) {
        try {
            List<WebElement> questions = getFaqQuestions();
            if (index >= 0 && index < questions.size()) {
                WebElement question = questions.get(index);
                System.out.println("🖱️ Clicking FAQ question " + index);

                // Дополнительный скролл к конкретному вопросу
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", question);
                Thread.sleep(500);

                // Кликаем
                question.click();
                Thread.sleep(1000); // Ждем анимацию
            } else {
                System.out.println("❌ FAQ question index out of bounds: " + index);
            }
        } catch (Exception e) {
            System.out.println("❌ Error clicking FAQ question " + index + ": " + e.getMessage());
        }
    }

    public String getFaqAnswerText(int index) {
        try {
            List<WebElement> answers = getFaqAnswers();
            if (index >= 0 && index < answers.size()) {
                WebElement answer = answers.get(index);

                // Проверяем видимость
                if (answer.isDisplayed()) {
                    String text = answer.getText();
                    System.out.println("📋 FAQ answer " + index + " text length: " + text.length());
                    return text;
                }
            }
            return "";
        } catch (Exception e) {
            System.out.println("❌ Error getting FAQ answer " + index + ": " + e.getMessage());
            return "";
        }
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}