package com.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
public class EcommerceDetails {

    private String username;

    private String password;

    private String name;

    private String country;

    private String city;

    private String creditcart;

    private String month;

    private String year;


}
