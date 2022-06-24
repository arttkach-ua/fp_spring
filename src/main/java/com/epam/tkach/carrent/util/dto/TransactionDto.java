package com.epam.tkach.carrent.util.dto;

import com.epam.tkach.carrent.entity.User;
import lombok.*;

import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransactionDto {

    int id;

    private User user;

    private String description;
    @Positive(message = "error.wrong_sum")
    private double sum;

    private LocalDateTime timestamp;
}
