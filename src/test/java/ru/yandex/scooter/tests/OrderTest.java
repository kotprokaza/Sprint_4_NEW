package ru.yandex.scooter.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import ru.yandex.scooter.model.Order;
import ru.yandex.scooter.pageobjects.MainPage;
import ru.yandex.scooter.pageobjects.OrderPage;
import ru.yandex.scooter.utils.BrowserFactory;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class OrderTest {
    private WebDriver driver;
    private MainPage mainPage;
    private OrderPage orderPage;

    private final String browser;
    private final Order order;
    private final boolean useTopButton;

    public OrderTest(String browser, Order order, boolean useTopButton) {
        this.browser = browser;
        this.order = order;
        this.useTopButton = useTopButton;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {"chrome", Order.getValidOrder1(), true},
                {"chrome", Order.getValidOrder2(), false},
                {"firefox", Order.getValidOrder1(), true},
                {"firefox", Order.getValidOrder2(), false}
        });
    }

    @Before
    public void setUp() {
        driver = BrowserFactory.createDriver(browser);
        mainPage = new MainPage(driver);
        orderPage = new OrderPage(driver);
        mainPage.open();
    }

    @Test
    public void testSuccessfulOrder() {
        // Выбираем точку входа (верхняя или нижняя кнопка)
        if (useTopButton) {
            mainPage.clickTopOrderButton();
        } else {
            mainPage.clickBottomOrderButton();
        }

        // Заполняем форму заказа
        orderPage.completeOrder(order);

        // Проверяем успешное создание заказа
        String successMessage = orderPage.getOrderSuccessMessage();
        assertTrue("Success message should contain 'Заказ оформлен'",
                successMessage.contains("Заказ оформлен"));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}