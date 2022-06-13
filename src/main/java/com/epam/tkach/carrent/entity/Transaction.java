package com.epam.tkach.carrent.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column(name="description")
    private String description;

    @Column(name = "sum")
    private double sum;

    @Column(name="timestamp")
    private LocalDateTime timestamp;

    @Override
    public String toString() {
        return "Transaction{" +
                "user=" + user +
                ", description='" + description + '\'' +
                ", sum=" + sum +
                ", timestamp=" + timestamp +
                ", ID=" + id +
                '}';
    }
}
