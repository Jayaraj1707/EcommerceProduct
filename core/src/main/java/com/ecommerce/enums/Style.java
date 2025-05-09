package com.ecommerce.enums;

public enum Style {

    /** The bold. */
    BOLD("\033[1m#\033[22m"),

    /** The underline. */
    UNDERLINE("\033[4m#\033[24m"),

    /** The no style. */
    NO_STYLE("NO_STYLE");

    /** The value. */
    private String value;

    /**
     * Instantiates a new style.
     *
     * @param value the value
     */
    private Style(String value) {

        this.value = value;
    }

    /**
     * Gets the value.
     *
     * @return the value
     */
    public String getValue() {

        return value;
    }
}
