package com.epam.tkach.carrent.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NoSuchUserException extends Exception{
    private static final Logger logger = LogManager.getLogger(NoSuchUserException.class);

    public NoSuchUserException() {
        logger.error("No such user exception was thrown");
    }

    public NoSuchUserException(String message) {
        super(message);
        logger.error("No such user exception was thrown", message);
    }

    public NoSuchUserException(String message, Throwable cause) {
        super(message, cause);
        logger.error("No such user exception was thrown", message, cause);
    }

    public NoSuchUserException(Throwable cause) {
        super(cause);
    }

    public NoSuchUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        logger.error("No such user exception was thrown", message, cause);
    }
}
