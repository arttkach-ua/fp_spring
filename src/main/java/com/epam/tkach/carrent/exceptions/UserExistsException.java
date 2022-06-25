package com.epam.tkach.carrent.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserExistsException extends Exception{
    private static final Logger logger = LogManager.getLogger(UserExistsException.class);
    private final String ERROR_MESSAGE = "Such user exists exception was thrown";


    public UserExistsException() {
        logger.error(ERROR_MESSAGE);
    }

    public UserExistsException(String message) {
        super(message);
        logger.error(ERROR_MESSAGE, message);
    }

    public UserExistsException(String message, Throwable cause) {
        super(message, cause);
        logger.error(ERROR_MESSAGE, message, cause);
    }

    public UserExistsException(Throwable cause) {
        super(cause);
    }

    public UserExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        logger.error(ERROR_MESSAGE, message, cause);
    }
}
