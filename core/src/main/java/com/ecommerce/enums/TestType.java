package com.ecommerce.enums;

import lombok.AllArgsConstructor;

@SuppressWarnings("all")
@AllArgsConstructor
/**
 * Enum constant for marking test type.
 */
public enum TestType {
    UI,
    API;
    private static final String KEY = "testType";

    /**
     * Gets key
     *
     * @return String key
     */
    public String getKey() {
        return KEY;
    }

    /**
     * Gets value for test type
     *
     * @return String value
     */
    public String getValue() {
        return this.name();
    }
}
