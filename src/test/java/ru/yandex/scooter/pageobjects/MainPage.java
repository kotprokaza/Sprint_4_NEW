package ru.yandex.scooter.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MainPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    
    // Локаторы
    private final By topOrderButton = By.className("Button_Button__ra12g");
    private final By bottomOrderButton = By.xpath("//button[contains(@class, 'Button_Middle__1CSJM')]");
    private final By faqQuestions = By.xpath("//div[contains(@class, 'accordion__button')]");
    private final By faqAnswers = By.xpath("//div[contains(@class, 'accordion__panel') and not(contains(@class, 'hidden'))]");
    
    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    public void open() {
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }
    
    public void clickTopOrderButton() {
        List<WebElement> orderButtons = driver.findElements(topOrderButton);
        if (!orderButtons.isEmpty()) {
            orderButtons.get(0).click();
        }
    }
    
    public void clickBottomOrderButton() {
        List<WebElement> orderButtons = driver.findElements(topOrderButton);
        if (orderButtons.size() > 1) {
            orderButtons.get(1).click();
        } else if (!orderButtons.isEmpty()) {
            WebElement bottomButton = driver.findElement(bottomOrderButton);
            bottomButton.click();
        }
    }
    
    public void clickFaqQuestion(int index) {
        List<WebElement> questions = driver.findElements(faqQuestions);
        if (index < questions.size()) {
            questions.get(index).click();
        }
    }
    
    public String getFaqAnswerText(int index) {
        List<WebElement> answers = driver.findElements(faqAnswers);
        if (index < answers.size()) {
            return answers.get(index).getText();
        }
        return "";
    }
    
    public boolean isFaqAnswerDisplayed(int index) {
        List<WebElement> answers = driver.findElements(faqAnswers);
        if (index < answers.size()) {
            return answers.get(index).isDisplayed();
        }
        return false;
    }
    
    public int getFaqQuestionsCount() {
        return driver.findElements(faqQuestions).size();
    }
}
