package com.epam.tkach.carrent.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NoSuchTariffException extends Exception{
    private static final Logger logger = LogManager.getLogger(NoSuchTariffException.class);
    private final String ERROR_MESSAGE = "No such tariff exception was thrown";

    public NoSuchTariffException() {
        logger.error(ERROR_MESSAGE);
    }

    public NoSuchTariffException(String message) {
        super(message);
        logger.error(ERROR_MESSAGE, message);
    }

    public NoSuchTariffException(String message, Throwable cause) {
        super(message, cause);
        logger.error(ERROR_MESSAGE, message, cause);
    }

    public NoSuchTariffException(Throwable cause) {
        super(cause);
    }

    public NoSuchTariffException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        logger.error(ERROR_MESSAGE, message, cause);
    }
}
