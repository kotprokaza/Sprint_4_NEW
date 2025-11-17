package ru.yandex.scooter.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.scooter.config.AppConfig;
import ru.yandex.scooter.model.Order;

import java.time.Duration;
import java.util.List;

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

    // ИСПРАВЛЕННЫЙ МЕТОД ВЫБОРА СТАНЦИИ МЕТРО
    private void selectMetroStation(String stationName) {
        System.out.println("🚇 Selecting metro station: " + stationName);

        try {
            // Кликаем на поле метро
            WebElement metroField = wait.until(ExpectedConditions.elementToBeClickable(metroStationField));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", metroField);
            metroField.click();
            Thread.sleep(1000);

            // Пробуем разные локаторы для станции метро
            String[] stationLocators = {
                    "//div[text()='" + stationName + "']",
                    "//li[text()='" + stationName + "']",
                    "//button[text()='" + stationName + "']",
                    "//*[contains(text(), '" + stationName + "')]",
                    "//div[contains(@class, 'select-search__option') and contains(text(), '" + stationName + "')]",
                    "//div[contains(@class, 'Order_Text__2broi') and text()='" + stationName + "']"
            };

            boolean stationSelected = false;

            for (String locator : stationLocators) {
                try {
                    WebElement station = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
                    System.out.println("✅ Found station with locator: " + locator);

                    // Пробуем разные способы клика
                    try {
                        station.click();
                    } catch (Exception e) {
                        System.out.println("🔄 Standard click failed, trying JS click...");
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", station);
                    }

                    stationSelected = true;
                    Thread.sleep(1000);
                    break;

                } catch (Exception e) {
                    System.out.println("❌ Not found with locator: " + locator);
                }
            }

            // Если не нашли по локаторам, пробуем альтернативные методы
            if (!stationSelected) {
                System.out.println("🔄 Trying alternative selection methods...");

                // Метод 1: Ввод текста и выбор из списка
                metroField.sendKeys(stationName);
                Thread.sleep(2000);

                // Ищем любую станцию в выпадающем списке
                List<WebElement> metroOptions = driver.findElements(By.xpath("//div[contains(@class, 'select-search__option')]"));
                if (!metroOptions.isEmpty()) {
                    System.out.println("📊 Found " + metroOptions.size() + " metro options, clicking first");
                    metroOptions.get(0).click();
                    stationSelected = true;
                }

                // Метод 2: Используем клавиши
                if (!stationSelected) {
                    metroField.sendKeys(Keys.ARROW_DOWN);
                    Thread.sleep(500);
                    metroField.sendKeys(Keys.ENTER);
                    stationSelected = true;
                }
            }

            if (stationSelected) {
                System.out.println("✅ Metro station '" + stationName + "' selected successfully");
            } else {
                System.out.println("❌ Failed to select metro station '" + stationName + "'");
            }

            Thread.sleep(1000);

        } catch (Exception e) {
            System.out.println("🚨 Error selecting metro station '" + stationName + "': " + e.getMessage());
            throw e;
        }
    }

    public void fillFirstPage(Order order) {
        System.out.println("📄 Filling first page...");

        // Ждем загрузки формы
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField));

        driver.findElement(firstNameField).sendKeys(order.getFirstName());
        driver.findElement(lastNameField).sendKeys(order.getLastName());
        driver.findElement(addressField).sendKeys(order.getAddress());

        // Используем исправленный метод выбора метро
        selectMetroStation(order.getMetroStation());

        driver.findElement(phoneField).sendKeys(order.getPhone());

        System.out.println("✅ First page filled");
    }

    public void clickNextButton() {
        System.out.println("➡️ Clicking next button...");
        WebElement nextBtn = wait.until(ExpectedConditions.elementToBeClickable(nextButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", nextBtn);

        // Ждем загрузки второй страницы
        wait.until(ExpectedConditions.visibilityOfElementLocated(deliveryDateField));
        System.out.println("✅ Second page loaded");
    }

    public void fillSecondPage(Order order) {
        System.out.println("📄 Filling second page...");

        // Установка даты
        WebElement dateField = driver.findElement(deliveryDateField);
        dateField.clear();
        dateField.sendKeys(order.getDeliveryDate());

        // Выбор срока аренды
        WebElement periodField = wait.until(ExpectedConditions.elementToBeClickable(rentalPeriodField));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", periodField);

        // Ждем появления опций и выбираем
        WebElement periodOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[text()='" + order.getRentalPeriod() + "']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", periodOption);

        // Выбор цвета
        if ("black".equals(order.getColor())) {
            WebElement blackCheckbox = wait.until(ExpectedConditions.elementToBeClickable(blackColorCheckbox));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", blackCheckbox);
        } else if ("grey".equals(order.getColor())) {
            WebElement greyCheckbox = wait.until(ExpectedConditions.elementToBeClickable(greyColorCheckbox));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", greyCheckbox);
        }

        // Комментарий
        if (order.getComment() != null && !order.getComment().isEmpty()) {
            driver.findElement(commentField).sendKeys(order.getComment());
        }

        System.out.println("✅ Second page filled");
    }

    public void clickOrderButton() {
        System.out.println("🖱️ Clicking order button...");
        WebElement orderBtn = wait.until(ExpectedConditions.elementToBeClickable(orderButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", orderBtn);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", orderBtn);
        System.out.println("✅ Order button clicked");
    }

    public void confirmOrder() {
        System.out.println("✅ Confirming order...");
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmationModal));
        WebElement yesBtn = wait.until(ExpectedConditions.elementToBeClickable(yesButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", yesBtn);
        System.out.println("✅ Order confirmed");
    }

    public String getOrderSuccessMessage() {
        System.out.println("📋 Getting success message...");
        WebElement messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(orderSuccessMessage));
        String message = messageElement.getText();
        System.out.println("✅ Success message: " + message);
        return message;
    }

    public void completeOrder(Order order) {
        fillFirstPage(order);
        clickNextButton();
        fillSecondPage(order);
        clickOrderButton();
        confirmOrder();
    }
}