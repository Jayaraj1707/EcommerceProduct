package com.ecommerce.data;

import com.ecommerce.entity.EcommerceDetails;
import com.ecommerce.util.JsonUtil;

import static com.ecommerce.manager.ConfigManager.*;

public class AbstractDataProvider {

    public static EcommerceDetails getPersonaDetailsData(String updateData){

        EcommerceDetails ecommerceDetails = new EcommerceDetails();
        if(ecommerceDetails !=null){
            ecommerceDetails = JsonUtil.getObject(getValue(updateData), EcommerceDetails.class);
        }
        return ecommerceDetails;
    }
}
