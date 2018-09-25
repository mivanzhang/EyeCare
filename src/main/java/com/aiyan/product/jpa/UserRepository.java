package com.aiyan.product.jpa;

import com.aiyan.product.bean.School;
import com.aiyan.product.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByPhoneNumber(String phoneNumber);

    Optional<User> findUserByToken(String token);

    void deleteUserByUserId(int userid);

}
