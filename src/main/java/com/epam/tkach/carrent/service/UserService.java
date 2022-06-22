package com.epam.tkach.carrent.service;

import com.epam.tkach.carrent.controller.CarBrandController;
import com.epam.tkach.carrent.entity.User;
import com.epam.tkach.carrent.exceptions.NoSuchUserException;
import com.epam.tkach.carrent.repos.UserRepository;
import com.epam.tkach.carrent.util.dto.UserDto;
import com.epam.tkach.carrent.util.pagination.Paged;
import com.epam.tkach.carrent.util.pagination.Paging;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserService {
    private static final Logger logger = LogManager.getLogger(UserService.class);
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

    public boolean createNewUser(UserDto user) {

        if (userRepository.count()==0){
            user.setRole("ADMIN");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            System.out.println("EXISTS");
            return false;
        };
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(User.getFromDTO(user));
        return true;
    }

    /**
     * Saves user to databace
     */
    @Transactional
    public void save(User user){
        userRepository.save(user);
    }

    @Transactional
    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    /**
     * Looking for user in DB.
     * @param email - email of user
     * @return user instance. If nothing found throws exception
     * @throws NoSuchUserException
     */

    public User findUserByEmail(String email) throws NoSuchUserException {
        Optional<User> opt = userRepository.findByEmail(email);
        if (!opt.isPresent()) throw new NoSuchUserException("User with email " + email + " not found");
        return opt.get();
    }

    /**
     * Gets page with users
     * @param pageNumber
     * @param size
     * @return
     */
    public Paged<User> getPage(int pageNumber, int size) {
        PageRequest request = PageRequest.of(pageNumber - 1, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<User> postPage = userRepository.findAll(request);
        return new Paged<>(postPage, Paging.of(postPage.getTotalPages(), pageNumber, size));
    }

    /**
     * Set block to user
     * @param id - User id
     * @param newStatus - true if we want to block, false if want unblock
     * @throws NoSuchUserException
     */
    public void setBlockToUser(int id, boolean newStatus) throws NoSuchUserException {
        Optional<User> opt = userRepository.findById(id);
        if (!opt.isPresent()) throw new NoSuchUserException("User with id " + id + " not found");
        User user = opt.get();
        user.setBlocked(newStatus);
        save(user);
    }

    public void updateUser(UserDto dto) throws NoSuchUserException {
        Optional<User> opt = userRepository.findById(dto.getId());
        if (!opt.isPresent()) throw new NoSuchUserException("User with id " + dto.getId() + " not found");
        User user = opt.get();
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhoneNumber());
        user.setFirstName(dto.getFirstName());
        user.setSecondName(dto.getSecondName());
        user.setDocumentInfo(dto.getDocumentInfo());
        userRepository.save(user);
    }
}
