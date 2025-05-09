package com.ecommerce.enums;

import org.openqa.selenium.By;

public enum EcommerceEnum {

    MODEL_DISPLAYED(By.xpath("(//div[@class='modal-content'])[2]"), "Model Displayed."),

    SIGN_UP(By.xpath("//a[@id='signin2']"), "Sign Up Button."),

    USER_NAME(By.xpath("//input[@id='sign-username']"), "User Name."),

    PASSWORD(By.xpath("//input[@id='sign-password']"), "Password."),

    SIGN_UP_BUTTON(By.xpath("//button[text()='Sign up']"), "Sign Up Button."),

    LOGIN_USER(By.xpath("//input[@id='loginusername']"), "Login User."),

    LOGIN_PASSWORD(By.xpath("//input[@id='loginpassword']"), "Login Password."),

    LOG_IN(By.xpath("//button[text()='Log in']"), "Login Button."),

    LOGIN(By.xpath("//a[@id='login2']"), "Login Button."),

    PRODUCT(By.xpath("//a[text()='Samsung galaxy s6']"), "Product Displayed."),

    ADD_TO_CART(By.xpath("//a[text()='Add to cart']"), "Add to Cart Button."),

    CART(By.xpath("//a[text()='Cart']"), "Cart Button."),

    CART_MODEL_DISPLAYED(By.xpath("//tbody[@id='tbodyid']//td[text()='Samsung galaxy s6']"), "Cart Model Displayed."),

    PLACE_ORDER(By.xpath("//button[text()='Place Order']"), "Place Order Button."),

    PLACE_ORDER_MODEL_DISPLAYED(By.xpath("(//div[@class='modal-content'])[3]"), "Place Order Model Displayed."),

    NAME(By.xpath("//input[@id='name']"), "Name."),

    COUNTRY(By.xpath("//input[@id='country']"), "Country."),

    CITY(By.xpath("//input[@id='city']"), "City."),

    CREDIT_CARD(By.xpath("//input[@id='card']"), "Credit Card."),

    YEAR(By.xpath("//input[@id='year']"), "Year."),

    MONTH(By.xpath("//input[@id='month']"), "Month."),

    PURCHASE_BUTTON(By.xpath("//button[text()='Purchase']"),"Purchase buttton"),

    PURCHASE_MODEL(By.xpath("//div[@class='sweet-alert  showSweetAlert visible']"), "Purchase model"),

    PURCHASE_VALIDATE(By.xpath("//h2[text()='Thank you for your purchase!']"), "Purchase validate"),

    OK_BUTTON(By.xpath("//button[text()='OK']"),"Ok button"),

    LOGOUT(By.xpath("//a[text()='Log out']"),"Logout"),


    TEXTAREA(By.xpath("//textarea[contains(@class,'form-control')]"), "Textarea.");

    /**
     * The xpath.
     */
    public By byLocator = null;

    /**
     * The description.
     */
    public String description = null;

    /**
     * The xpath.
     */
    private String xpath;

    /**
     * Gets the by locator.
     *
     * @return the by locator
     */
    public By getByLocator() {

        return byLocator;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {

        return description;
    }

    /**
     * Gets the xpath.
     *
     * @return the xpath
     */
    public String getXpath() {

        return xpath;
    }

    private EcommerceEnum(By byLocator, String description) {

        this.byLocator = byLocator;
        this.description = description;
    }

    private EcommerceEnum(String xpath, String description) {

        this.xpath = xpath;
        this.description = description;
    }
}
