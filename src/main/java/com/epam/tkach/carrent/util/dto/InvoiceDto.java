package com.epam.tkach.carrent.util.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class InvoiceDto {
    @NotNull(message = "error.nullClient")
    private String client;
    @NotNull(message = "error.nullOrder")
    private String order;
    @NotNull(message = "error.nullDescription")
    private String description;
    @Positive(message = "error.nullAmount")
    private double amount;
    @NotNull(message = "error.nullInvoiceType")
    private String type;
    private boolean paid;
}
