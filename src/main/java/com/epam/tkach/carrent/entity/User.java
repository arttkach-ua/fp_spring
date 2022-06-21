package com.epam.tkach.carrent.entity;

import com.epam.tkach.carrent.Messages;
import com.epam.tkach.carrent.entity.enums.Role;
import com.epam.tkach.carrent.util.dto.UserDto;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "users")
public class User implements UserDetails {
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
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name="blocked")
    private boolean blocked;

    @Column(name="salt")
    private byte[] salt;

    @Column(name="receive_notifications")
    boolean receiveNotifications;


    public static User getFromDTO(UserDto userDTO){
        User user = new User();
        user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setSecondName(userDTO.getSecondName());
        user.setDocumentInfo(userDTO.getDocumentInfo());
        user.setPhone(userDTO.getPhoneNumber());
        user.setPassword(userDTO.getPassword());
        user.setReceiveNotifications(userDTO.isReceiveEmails());
        if (userDTO.getRole()==null||userDTO.getRole().isEmpty()){
            userDTO.setRole("CLIENT");
        }
        user.setRole(Role.valueOf(userDTO.getRole()));
        return user;
    }

    public String getFullName(){
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add(firstName);
        joiner.add(secondName);
        return joiner.toString();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> auth = new HashSet<>();
        auth.add(new SimpleGrantedAuthority("ROLE_" + "ADMIN"));
        auth.add(new SimpleGrantedAuthority("ROLE_" + "CLIENT"));
        auth.add(new SimpleGrantedAuthority("ROLE_" + "MANAGER"));
        //getAuthorities().forEach(r->auth.add(new SimpleGrantedAuthority(r.getAuthority())));
        return auth;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
