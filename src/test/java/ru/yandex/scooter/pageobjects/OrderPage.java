package ru.yandex.scooter.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.scooter.model.Order;

import java.time.Duration;

public class OrderPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Локаторы для формы заказа
    private final By firstNameField = By.xpath("//input[@placeholder='* Имя']");
    private final By lastNameField = By.xpath("//input[@placeholder='* Фамилия']");
    private final By addressField = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    private final By metroStationField = By.xpath("//input[@placeholder='* Станция метро']");
    private final By phoneField = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    private final By nextButton = By.xpath("//button[text()='Далее']");

    private final By deliveryDateField = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    private final By rentalPeriodField = By.className("Dropdown-placeholder");
    private final By blackColorCheckbox = By.id("black");
    private final By greyColorCheckbox = By.id("grey");
    private final By commentField = By.xpath("//input[@placeholder='Комментарий для курьера']");
    private final By orderButton = By.xpath("//button[text()='Заказать']");

    private final By confirmationModal = By.className("Order_Modal__YZD3");
    private final By yesButton = By.xpath("//button[text()='Да']");
    private final By orderSuccessMessage = By.className("Order_ModalHeader__3FDaJ");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Метод выбора станции метро
    private void selectMetroStation(String stationName) {
        WebElement metroField = wait.until(ExpectedConditions.elementToBeClickable(metroStationField));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", metroField);
        metroField.click();

        WebElement station = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//div[text()='" + stationName + "']")));
        station.click();
    }

    public void fillFirstPage(Order order) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField));

        driver.findElement(firstNameField).sendKeys(order.getFirstName());
        driver.findElement(lastNameField).sendKeys(order.getLastName());
        driver.findElement(addressField).sendKeys(order.getAddress());
        selectMetroStation(order.getMetroStation());
        driver.findElement(phoneField).sendKeys(order.getPhone());
    }

    public void clickNextButton() {
        WebElement nextBtn = wait.until(ExpectedConditions.elementToBeClickable(nextButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", nextBtn);
        wait.until(ExpectedConditions.visibilityOfElementLocated(deliveryDateField));
    }

    public void fillSecondPage(Order order) {
        WebElement dateField = driver.findElement(deliveryDateField);
        dateField.clear();
        dateField.sendKeys(order.getDeliveryDate());

        WebElement periodField = wait.until(ExpectedConditions.elementToBeClickable(rentalPeriodField));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", periodField);

        WebElement periodOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[text()='" + order.getRentalPeriod() + "']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", periodOption);

        if ("black".equals(order.getColor())) {
            WebElement blackCheckbox = wait.until(ExpectedConditions.elementToBeClickable(blackColorCheckbox));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", blackCheckbox);
        } else if ("grey".equals(order.getColor())) {
            WebElement greyCheckbox = wait.until(ExpectedConditions.elementToBeClickable(greyColorCheckbox));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", greyCheckbox);
        }

        if (order.getComment() != null && !order.getComment().isEmpty()) {
            driver.findElement(commentField).sendKeys(order.getComment());
        }
    }

    public void clickOrderButton() {
        WebElement orderBtn = wait.until(ExpectedConditions.elementToBeClickable(orderButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", orderBtn);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", orderBtn);
    }

    public void confirmOrder() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmationModal));
        WebElement yesBtn = wait.until(ExpectedConditions.elementToBeClickable(yesButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", yesBtn);
    }

    public String getOrderSuccessMessage() {
        WebElement messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(orderSuccessMessage));
        return messageElement.getText();
    }

    public void completeOrder(Order order) {
        fillFirstPage(order);
        clickNextButton();
        fillSecondPage(order);
        clickOrderButton();
        confirmOrder();
    }
}
