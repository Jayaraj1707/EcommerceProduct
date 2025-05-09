package com.ecommerce.enums;

public enum Timeout {

    /** The atomic timeout. */
    ATOMIC_TIMEOUT(1),
    /** The three sec. */
    THREE_SEC(3),
    /** The five sec. */
    FIVE_SEC(5),
    /** The tiny timeout. */
    TINY_TIMEOUT(10),
    /** The short timeout. */
    TEN_SEC(10),
    /** The thirty sec. */
    THIRTY_SEC(30),
    /** The short timeout. */
    SHORT_TIMEOUT(60),
    /** The medium timeout. */
    MEDIUM_TIMEOUT(90),
    /** The long timeout. */
    LONG_TIMEOUT(120),
    /** The very long timeout. */
    VERY_LONG_TIMEOUT(300),
    /** The very, very long timeout. */
    VERY_VERY_LONG_TIMEOUT(2700);

    /**
     * The value.
     */
    private final int value;

    /**
     * Instantiates a new timeout.
     *
     * @param value
     *            the value
     */
    private Timeout(final int value) {

        this.value = value;
    }

    /**
     * Gets the value.
     *
     * @return the value
     */
    public int getValue() {

        return value;
    }

    /**
     * Gets timeout item by name.
     *
     * @return the timeout name
     */
    public static Timeout getByName(String name) {

        for (Timeout item : values()) {
            if (item.name().toLowerCase().contentEquals(name.toLowerCase())){
                return item;
            }
        }
        return null;
    }
}
