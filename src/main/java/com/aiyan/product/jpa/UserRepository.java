package com.aiyan.product.jpa;

import com.aiyan.product.bean.School;
import com.aiyan.product.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
