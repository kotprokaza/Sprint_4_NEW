package ru.yandex.scooter.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Order {
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private String deliveryDate;
    private String rentalPeriod;
    private String color;
    private String comment;

    public Order(String firstName, String lastName, String address, String metroStation,
                 String phone, String deliveryDate, String rentalPeriod, String color, String comment) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.deliveryDate = deliveryDate;
        this.rentalPeriod = rentalPeriod;
        this.color = color;
        this.comment = comment;
    }

    // Геттеры
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getAddress() { return address; }
    public String getMetroStation() { return metroStation; }
    public String getPhone() { return phone; }
    public String getDeliveryDate() { return deliveryDate; }
    public String getRentalPeriod() { return rentalPeriod; }
    public String getColor() { return color; }
    public String getComment() { return comment; }

    // Статические методы для создания тестовых данных
    public static Order getValidOrder1() {
        return new Order(
                "Иван",
                "Петров",
                "ул. Ленина, д. 10",
                "Черкизовская",
                "+79161234567",
                LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                "трое суток",
                "black",
                "Позвонить за час до доставки"
        );
    }

    public static Order getValidOrder2() {
        return new Order(
                "Мария",
                "Сидорова",
                "пр. Мира, д. 25",
                "Сокольники",
                "+79167654321",
                LocalDate.now().plusDays(5).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                "сутки",
                "grey",
                "Оставить у двери"
        );
    }
}