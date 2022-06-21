package com.epam.tkach.carrent.service;

import com.epam.tkach.carrent.entity.User;
import com.epam.tkach.carrent.repos.UserRepository;
import com.epam.tkach.carrent.util.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public boolean saveUser(UserDto user) {

        if (userRepository.count()==0){
            user.setRole("ADMIN");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            System.out.println("EXISTS");
            return false;
        };
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(User.getFromDTO(user));
        System.out.println("saved");
        return true;
    }

    @Transactional
    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

}
