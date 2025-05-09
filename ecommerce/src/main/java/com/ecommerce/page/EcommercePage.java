package com.ecommerce.page;

import com.ecommerce.entity.EcommerceDetails;
import com.ecommerce.enums.EcommerceEnum;
import com.ecommerce.enums.LogLevel;
import com.ecommerce.enums.Steps;
import com.ecommerce.enums.Timeout;
import com.ecommerce.log.LogUtil;
import com.ecommerce.setups.EcommerceBasePage;
import com.ecommerce.util.PageUtil;
import com.ecommerce.util.WaitUtil;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import static com.ecommerce.manager.ConfigManager.getValue;
import static com.ecommerce.manager.DriverManager.getDriver;

/**
 * The Class MyPersonaPage.
 */
public class EcommercePage extends EcommerceBasePage {

    /**
     * Instantiates MyPersona page.
     *
     * @param driver
     */
    public EcommercePage(WebDriver driver) {
        super(driver);
    }


    public void ClickSignUpButton() {

        LogUtil.log("Click sign up button", LogLevel.LOW);
        WebElement signup = PageUtil.getElement(getDriver(), EcommerceEnum.SIGN_UP.getByLocator(), Timeout.FIVE_SEC);
        Assert.assertTrue(signup != null && signup.isDisplayed(), "Sign up button is not displayed");
        signup.click();
        WaitUtil.waitUntil(Timeout.THREE_SEC);
    }

    public void CreateNewUser(EcommerceDetails ecommerceDetails) {

        LogUtil.log("Create new user", LogLevel.LOW);
        WebElement modelisdisplayed = PageUtil.getElement(getDriver(), EcommerceEnum.MODEL_DISPLAYED.getByLocator(), Timeout.FIVE_SEC);
        Assert.assertTrue(modelisdisplayed != null && modelisdisplayed.isDisplayed(), "Model is not displayed");
        WebElement emailField = PageUtil.getElement(getDriver(), EcommerceEnum.USER_NAME.getByLocator(), Timeout.FIVE_SEC);
        String username = ecommerceDetails.getUsername();
        PageUtil.setText(emailField, username);
        WebElement passwordField = PageUtil.getElement(getDriver(), EcommerceEnum.PASSWORD.getByLocator(), Timeout.FIVE_SEC);
        String password = ecommerceDetails.getPassword();
        PageUtil.setText(passwordField, password);
        WebElement signupbtn = PageUtil.getElement(getDriver(), EcommerceEnum.SIGN_UP_BUTTON.getByLocator(), Timeout.FIVE_SEC);
        Assert.assertTrue(signupbtn != null && signupbtn.isDisplayed(), "Sign up button is not displayed");
        signupbtn.click();
        WaitUtil.waitUntil(Timeout.THREE_SEC);
        Alert alert = getDriver().switchTo().alert();
        alert.accept();
    }

    public void LoginUser(EcommerceDetails ecommerceDetails) {

        LogUtil.log("Login user", LogLevel.LOW);
        WebElement login = PageUtil.getElement(getDriver(), EcommerceEnum.LOGIN.getByLocator(), Timeout.FIVE_SEC);
        Assert.assertTrue(login != null && login.isDisplayed(), "Login button is not displayed");
        login.click();
        WaitUtil.waitUntil(Timeout.THREE_SEC);
        WebElement emailField = PageUtil.getElement(getDriver(), EcommerceEnum.LOGIN_USER.getByLocator(), Timeout.FIVE_SEC);
        String username = ecommerceDetails.getUsername();
        PageUtil.setText(emailField, username);
        WebElement passwordField = PageUtil.getElement(getDriver(), EcommerceEnum.LOGIN_PASSWORD.getByLocator(), Timeout.FIVE_SEC);
        String password = ecommerceDetails.getPassword();
        PageUtil.setText(passwordField, password);
        WebElement loginbtn = PageUtil.getElement(getDriver(), EcommerceEnum.LOG_IN.getByLocator(), Timeout.FIVE_SEC);
        Assert.assertTrue(loginbtn != null && loginbtn.isDisplayed(), "Login button is not displayed");
        loginbtn.click();
        WaitUtil.waitUntil(Timeout.THREE_SEC);
    }

    public void ClickProduct() {

        LogUtil.log("Select product", LogLevel.LOW);
        WaitUtil.waitUntil(Timeout.THREE_SEC);
        WebElement product = PageUtil.getElement(getDriver(), EcommerceEnum.PRODUCT.getByLocator(), Timeout.FIVE_SEC);
        Assert.assertTrue(product != null && product.isDisplayed(), "Product is not displayed");
        product.click();
        WaitUtil.waitUntil(Timeout.THREE_SEC);
    }

    public void AddToCart() {

        LogUtil.log("Add to cart", LogLevel.LOW);
        getDriver().get(getValue("ecommerce.addtocart"));
        WebElement addtocart = PageUtil.getElement(getDriver(), EcommerceEnum.ADD_TO_CART.getByLocator(), Timeout.FIVE_SEC);
        Assert.assertTrue(addtocart != null && addtocart.isDisplayed(), "Add to cart button is not displayed");
        addtocart.click();
        WaitUtil.waitUntil(Timeout.THREE_SEC);
        Alert alert = getDriver().switchTo().alert();
        alert.accept();
    }

    public void NavigateToCart() {

        LogUtil.log("Navigate to cart", LogLevel.LOW);
        WebElement cart = PageUtil.getElement(getDriver(), EcommerceEnum.CART.getByLocator(), Timeout.FIVE_SEC);
        Assert.assertTrue(cart != null && cart.isDisplayed(), "Cart is not displayed");
        cart.click();
        WaitUtil.waitUntil(Timeout.THREE_SEC);
        WebElement cartmodelisdisplayed = PageUtil.getElement(getDriver(), EcommerceEnum.CART_MODEL_DISPLAYED.getByLocator(), Timeout.FIVE_SEC);
        Assert.assertTrue(cartmodelisdisplayed != null && cartmodelisdisplayed.isDisplayed(), "Cart model is not displayed");
    }

    public void FillPaymentDetails(EcommerceDetails ecommerceDetails) {

        LogUtil.log("Fill payment details", LogLevel.LOW);
        getDriver().get(getValue("ecommerce.payment"));
        WebElement placeorder = PageUtil.getElement(getDriver(), EcommerceEnum.PLACE_ORDER.getByLocator(), Timeout.FIVE_SEC);
        Assert.assertTrue(placeorder != null && placeorder.isDisplayed(), "Place order button is not displayed");
        placeorder.click();
        WaitUtil.waitUntil(Timeout.THREE_SEC);

        WebElement placeordermodelisdisplayed = PageUtil.getElement(getDriver(), EcommerceEnum.PLACE_ORDER_MODEL_DISPLAYED.getByLocator(), Timeout.FIVE_SEC);
        Assert.assertTrue(placeordermodelisdisplayed != null && placeordermodelisdisplayed.isDisplayed(), "Place order model is not displayed");

        WebElement nameField = PageUtil.getElement(getDriver(), EcommerceEnum.NAME.getByLocator(), Timeout.FIVE_SEC);
        String name = ecommerceDetails.getName();
        PageUtil.setText(nameField, name);

        WebElement countryField = PageUtil.getElement(getDriver(), EcommerceEnum.COUNTRY.getByLocator(), Timeout.FIVE_SEC);
        String country = ecommerceDetails.getCountry();
        PageUtil.setText(countryField, country);

        WebElement cityField = PageUtil.getElement(getDriver(), EcommerceEnum.CITY.getByLocator(), Timeout.FIVE_SEC);
        String city = ecommerceDetails.getCity();
        PageUtil.setText(cityField, city);

        WebElement CreditCardField = PageUtil.getElement(getDriver(), EcommerceEnum.CREDIT_CARD.getByLocator(), Timeout.FIVE_SEC);
        String creditcart = ecommerceDetails.getCreditcart();
        PageUtil.setText(CreditCardField, creditcart);

        WebElement MonthField = PageUtil.getElement(getDriver(), EcommerceEnum.MONTH.getByLocator(), Timeout.FIVE_SEC);
        String month = ecommerceDetails.getMonth();
        PageUtil.setText(MonthField, month);

        WebElement YearField = PageUtil.getElement(getDriver(), EcommerceEnum.YEAR.getByLocator(), Timeout.FIVE_SEC);
        String year = ecommerceDetails.getYear();
        PageUtil.setText(YearField, year);

        LogUtil.log(Steps.START, "Validate the place order");
        WebElement purchasebtn = PageUtil.getElement(getDriver(), EcommerceEnum.PURCHASE_BUTTON.getByLocator(), Timeout.FIVE_SEC);
        purchasebtn.click();

        WebElement purchasemodelisdisplayed = PageUtil.getElement(getDriver(), EcommerceEnum.PURCHASE_MODEL.getByLocator(), Timeout.FIVE_SEC);
        Assert.assertTrue(purchasemodelisdisplayed != null && purchasemodelisdisplayed.isDisplayed(), "Place order model is not displayed");

        WebElement validatepurchase = PageUtil.getElement(getDriver(), EcommerceEnum.PURCHASE_VALIDATE.getByLocator(), Timeout.FIVE_SEC);
        String message = validatepurchase.getText();
        Assert.assertEquals(message, "Thank you for your purchase!", "Alert message mismatch");

        WebElement okbtn = PageUtil.getElement(getDriver(), EcommerceEnum.OK_BUTTON.getByLocator(), Timeout.FIVE_SEC);
        okbtn.click();
    }

    public void logoutuser() {

        LogUtil.log(Steps.START, "Logout the user");
        getDriver().get(getValue("ecommerce.logouturl"));
        WaitUtil.waitUntil(Timeout.THREE_SEC);
        WebElement logout = PageUtil.getElement(getDriver(), EcommerceEnum.LOGOUT.getByLocator(), Timeout.FIVE_SEC);
        Assert.assertTrue(logout != null && logout.isDisplayed(), "Logout button is not displayed");
        logout.click();
    }
}
