package ru.yandex.scooter.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import ru.yandex.scooter.model.Order;
import ru.yandex.scooter.pageobjects.MainPage;
import ru.yandex.scooter.pageobjects.OrderPage;
import ru.yandex.scooter.utils.BrowserFactory;

import static org.junit.Assert.assertTrue;

public class OrderTest {
    private WebDriver driver;
    private MainPage mainPage;
    private OrderPage orderPage;

    @Before
    public void setUp() {
        driver = BrowserFactory.createDriver("chrome");
        mainPage = new MainPage(driver);
        orderPage = new OrderPage(driver);
    }

    @Test
    public void testOrderViaTopButton() {
        mainPage.open();
        mainPage.clickTopOrderButton();
        
        Order order = Order.getValidOrder1();
        orderPage.completeOrder(order);
        
        String successMessage = orderPage.getOrderSuccessMessage();
        assertTrue("Заказ должен быть успешно оформлен через верхнюю кнопку", 
                   successMessage.contains("Заказ оформлен"));
    }

    @Test
    public void testOrderViaBottomButton() {
        mainPage.open();
        mainPage.clickBottomOrderButton();
        
        Order order = Order.getValidOrder2();
        orderPage.completeOrder(order);
        
        String successMessage = orderPage.getOrderSuccessMessage();
        assertTrue("Заказ должен быть успешно оформлен через нижнюю кнопку", 
                   successMessage.contains("Заказ оформлен"));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
