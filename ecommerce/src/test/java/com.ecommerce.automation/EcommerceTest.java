package com.ecommerce.automation;

import com.ecommerce.data.EcommerceDataProvider;
import com.ecommerce.entity.EcommerceDetails;
import com.ecommerce.enums.LogLevel;
import com.ecommerce.enums.Steps;
import com.ecommerce.listener.AutomationListener;
import com.ecommerce.log.LogUtil;
import com.ecommerce.page.EcommercePage;
import com.ecommerce.setups.EcommerceTestBase;
import com.ecommerce.testng.CustomReport;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.ecommerce.manager.ConfigManager.getValue;
import static com.ecommerce.manager.DriverManager.getDriver;

@Listeners({CustomReport.class, AutomationListener.class})
public class EcommerceTest extends EcommerceTestBase {

    private EcommercePage ecommercePage;

    @BeforeMethod(alwaysRun = true)
    public void loadUrl(Object[] param) throws SecurityException {

        LogUtil.log("Load Url : " + getValue("ecommerce.loadUrl"), LogLevel.LOW);
        getDriver().get(getValue("ecommerce.loadUrl"));
        ecommercePage = PageFactory.initElements(getDriver(), EcommercePage.class);
    }



    @Test(dataProviderClass = EcommerceDataProvider.class, dataProvider = "registerNewUser", priority = 1)
    public void RegisterNewUser(EcommerceDetails ecommerceDetails) {

        LogUtil.log(Steps.START, "Register new user");
        ecommercePage.ClickSignUpButton();
        ecommercePage.CreateNewUser(ecommerceDetails);
    }

    @Test(dataProviderClass = EcommerceDataProvider.class, dataProvider = "registerNewUser", priority = 2)
    public void LoginUser(EcommerceDetails ecommerceDetails) {

        LogUtil.log(Steps.START, "Login user");
        ecommercePage.LoginUser(ecommerceDetails);
    }

    @Test(priority = 3)
    public void SelectProduct(){

        LogUtil.log(Steps.START, "Select product");
        ecommercePage.ClickProduct();
    }

    @Test(priority = 4)
    public void AddProductToCart(){

        LogUtil.log(Steps.START,"Add product to cart");
        ecommercePage.AddToCart();
    }

    @Test(priority = 5)
    public void CheckOutCart() {

        LogUtil.log(Steps.START, "Check out cart");
        ecommercePage.NavigateToCart();
    }

    @Test(dataProviderClass = EcommerceDataProvider.class, dataProvider = "paymentDetails", priority = 6)
    public void VerifyPaymentDetailsAndPlaceOrder(EcommerceDetails ecommerceDetails) {

        LogUtil.log(Steps.START, "Verify payment details");
        ecommercePage.FillPaymentDetails(ecommerceDetails);
    }

    @Test(priority = 7)
    public void LogoutUser() {

        LogUtil.log(Steps.START,"Log out user");
        ecommercePage.logoutuser();
    }

}