package com.epam.tkach.carrent.entity;

import com.epam.tkach.carrent.Messages;
import com.epam.tkach.carrent.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.StringJoiner;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "email")
    @Email(message = Messages.INVALID_EMAIL)
    private String email;

    @Column(name="first_name")
    @Size(min = 2, message = Messages.ERROR_SHORT_FIRST_NAME)
    private String firstName;

    @Column(name="second_name")
    @Size(min = 2, message = Messages.ERROR_SHORT_SECOND_NAME)
    private String secondName;

    @Column(name="phone")
    @Size(min = 2, message = Messages.INVALID_PHONE)
    private String phone;

    @Column(name="document_info")
    @Size(min = 2, message = Messages.INVALID_DOCUMENT)
    private String documentInfo;

    @Column(name="password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.ORDINAL)
    private Role role;

    @Column(name="blocked")
    private boolean blocked;

    @Column(name="salt")
    private byte[] salt;

    @Column(name="receive_notifications")
    boolean receiveNotifications;


    public String getFullName(){
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add(firstName);
        joiner.add(secondName);
        return joiner.toString();
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", phone='" + phone + '\'' +
                ", documentInfo='" + documentInfo + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", blocked=" + blocked +
                ", salt=" + Arrays.toString(salt) +
                ", receiveNotifications=" + receiveNotifications +
                ", ID=" + id +
                '}';
    }
}
