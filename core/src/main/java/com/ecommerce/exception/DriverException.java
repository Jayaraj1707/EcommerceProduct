package com.ecommerce.exception;

public class DriverException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The message. */
    private String message;

    /**
     * Instantiates a new driver exception.
     *
     * @param message the message
     */
    public DriverException(final String message) {

        super(message);
        this.setMessage(message);
    }

    /**
     * Instantiates a new driver exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public DriverException(final String message, final Throwable cause) {

        super(message, cause);
        this.setMessage(message);
    }

    /**
     * @return
     * @see Throwable#getMessage()
     */
    public String getMessage() {

        return message;
    }

    /**
     * Sets the message.
     *
     * @param message the new message
     */
    public void setMessage(String message) {

        this.message = message;
    }
}
