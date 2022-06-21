package com.epam.tkach.carrent.util.dto;

import com.epam.tkach.carrent.Messages;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

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
