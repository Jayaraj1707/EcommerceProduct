package com.ecommerce.data;

import com.ecommerce.entity.EcommerceDetails;
import org.testng.annotations.DataProvider;

/**
 * The Class MyAiEchoDataProvider.
 */
public class EcommerceDataProvider extends AbstractDataProvider {


    @DataProvider(name = "registerNewUser")
    public static Object[][] registerNewUser() {

        EcommerceDetails ecommerceDetails = getPersonaDetailsData("ecommerce.registerNewUser");
        return new Object[][]{{ecommerceDetails}};
    }

    @DataProvider(name = "paymentDetails")
    public static Object[][] loginUser() {

        EcommerceDetails ecommerceDetails = getPersonaDetailsData("ecommerce.paymentDetails");
        return new Object[][]{{ecommerceDetails}};
    }


    @DataProvider(name = "logout")
    public static Object[][] logout() {

        EcommerceDetails ecommerceDetails = getPersonaDetailsData("ecommerce.logouturl");
        return new Object[][]{{ecommerceDetails}};
    }
}
