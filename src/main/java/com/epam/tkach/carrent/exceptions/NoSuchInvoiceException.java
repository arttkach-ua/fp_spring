package com.epam.tkach.carrent.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NoSuchInvoiceException extends Exception{
    private static final Logger logger = LogManager.getLogger(NoSuchInvoiceException.class);
    private final String ERROR_MESSAGE = "No such invoice exception was thrown";

    public NoSuchInvoiceException() {
        logger.error(ERROR_MESSAGE);
    }

    public NoSuchInvoiceException(String message) {
        super(message);
        logger.error(ERROR_MESSAGE, message);
    }

    public NoSuchInvoiceException(String message, Throwable cause) {
        super(message, cause);
        logger.error(ERROR_MESSAGE, message, cause);
    }

    public NoSuchInvoiceException(Throwable cause) {
        super(cause);
    }

    public NoSuchInvoiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        logger.error(ERROR_MESSAGE, message, cause);
    }
}
