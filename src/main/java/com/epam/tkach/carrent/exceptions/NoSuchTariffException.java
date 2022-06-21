package com.epam.tkach.carrent.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NoSuchTariffException extends Exception{
    private static final Logger logger = LogManager.getLogger(NoSuchTariffException.class);

    public NoSuchTariffException() {
        logger.error("No such tariff exception was thrown");
    }

    public NoSuchTariffException(String message) {
        super(message);
        logger.error("No such tariff exception was thrown", message);
    }

    public NoSuchTariffException(String message, Throwable cause) {
        super(message, cause);
        logger.error("No such tariff set exception was thrown", message, cause);
    }

    public NoSuchTariffException(Throwable cause) {
        super(cause);
    }

    public NoSuchTariffException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        logger.error("No such tariff exception was thrown", message, cause);
    }
}
