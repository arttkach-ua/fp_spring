package com.epam.tkach.carrent.repos;

import com.epam.tkach.carrent.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {


    @Query("SELECT u FROM User u where u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    @Query("select count(u) > 0 from User u where u.email = :email")
    public boolean existsByEmail(@Param("email") String email);

//    @Modifying
//    @Query("update User u set u.firstName = ?1, u.secondName = ?2, u.email = ?3,  u.phone = ?4, u.documentInfo=?5 where u.id = ?6")
//    public boolean updateWithOutPass(@Param("email") String email)

}
