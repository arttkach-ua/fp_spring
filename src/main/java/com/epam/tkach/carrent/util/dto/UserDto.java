package com.epam.tkach.carrent.util.dto;

import com.epam.tkach.carrent.Messages;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {
    private int Id;

    @Email(message = Messages.INVALID_EMAIL)
    private String email;

    @Size(min = 2, message = Messages.ERROR_SHORT_FIRST_NAME)
    private String firstName;

    @Size(min = 2, message = Messages.ERROR_SHORT_SECOND_NAME)
    private String secondName;

    @Size(min = 2, message = Messages.INVALID_PHONE)
    private String phoneNumber;

    @Size(min = 2, message = Messages.INVALID_DOCUMENT)
    private String documentInfo;

    private String password;

    private String confirmPassword;

    private String role;

//    @Enumerated(EnumType.ORDINAL)
//    private Role role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Id == userDto.Id &&
                Objects.equals(email, userDto.email) &&
                Objects.equals(firstName, userDto.firstName) &&
                Objects.equals(secondName, userDto.secondName) &&
                Objects.equals(phoneNumber, userDto.phoneNumber) &&
                Objects.equals(documentInfo, userDto.documentInfo) &&
                Objects.equals(role, userDto.role) &&
                Objects.equals(receiveNotifications, userDto.receiveNotifications);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, email, firstName, secondName, phoneNumber, documentInfo, role, receiveNotifications);
    }

    //private boolean blocked;

    private String receiveNotifications;

    public boolean isReceiveEmails(){
        if("receiveNotifications".equals(receiveNotifications)) {
            return true;
        }else{
            return false;
        }
    }
}
