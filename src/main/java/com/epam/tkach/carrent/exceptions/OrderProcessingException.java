package com.epam.tkach.carrent.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrderProcessingException extends Exception{
    private static final Logger logger = LogManager.getLogger(OrderProcessingException.class);
    private final String ERROR_MESSAGE = "Order processing exception was thrown";


    public OrderProcessingException() {
        logger.error(ERROR_MESSAGE);
    }

    public OrderProcessingException(String message) {
        super(message);
        logger.error(ERROR_MESSAGE, message);
    }

    public OrderProcessingException(String message, Throwable cause) {
        super(message, cause);
        logger.error(ERROR_MESSAGE, message, cause);
    }

    public OrderProcessingException(Throwable cause) {
        super(cause);
    }

    public OrderProcessingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        logger.error(ERROR_MESSAGE, message, cause);
    }
}
