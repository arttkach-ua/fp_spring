package com.epam.tkach.carrent.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NoSuchCarModelException extends Exception{
    private static final Logger logger = LogManager.getLogger(NoSuchCarModelException.class);

    public NoSuchCarModelException() {
        logger.error("Car brand exception was thrown");
    }

    public NoSuchCarModelException(String message) {
        super(message);
        logger.error("Car brand exception was thrown", message);
    }

    public NoSuchCarModelException(String message, Throwable cause) {
        super(message, cause);
        logger.error("Car brand exception was thrown", message, cause);
    }

    public NoSuchCarModelException(Throwable cause) {
        super(cause);
    }

    public NoSuchCarModelException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        logger.error("Car brand exception was thrown", message, cause);
    }
}
