package com.epam.tkach.carrent.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InvoiceProcessingException extends Exception{
    private static final Logger logger = LogManager.getLogger(InvoiceProcessingException.class);
    private final String ERROR_MESSAGE = "Invoice processing exception was thrown";


    public InvoiceProcessingException() {
        logger.error(ERROR_MESSAGE);
    }

    public InvoiceProcessingException(String message) {
        super(message);
        logger.error(ERROR_MESSAGE, message);
    }

    public InvoiceProcessingException(String message, Throwable cause) {
        super(message, cause);
        logger.error(ERROR_MESSAGE, message, cause);
    }

    public InvoiceProcessingException(Throwable cause) {
        super(cause);
    }

    public InvoiceProcessingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        logger.error(ERROR_MESSAGE, message, cause);
    }
}
