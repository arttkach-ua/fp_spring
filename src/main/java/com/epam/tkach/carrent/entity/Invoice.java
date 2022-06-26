package com.epam.tkach.carrent.entity;

import com.epam.tkach.carrent.entity.enums.InvoiceTypes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "invoices")
public class Invoice{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name="client_id")
    @NotNull(message = "error.nullClient")
    private User client;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @NotNull(message = "error.nullOrder")
    private Order order;

    @Column(name = "description")
    @NotNull(message = "error.nullDescription")
    private String description;

    @Column(name="amount")
    @Positive(message = "error.nullAmount")
    private double amount;

    @Column(name="type")
    @Enumerated(EnumType.ORDINAL)
    @NotNull(message = "error.nullInvoiceType")
    private InvoiceTypes type;

    @Column(name = "paid")
    private boolean paid;

    @Column(name="timestamp")
    private Date dateTime;

    public String getFormattedDate(){
        if (dateTime== null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy h:mm a");
        return sdf.format(dateTime);
    }
}
