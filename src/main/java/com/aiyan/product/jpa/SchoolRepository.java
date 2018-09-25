package com.aiyan.product.jpa;

import com.aiyan.product.bean.Query;
import com.aiyan.product.bean.School;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SchoolRepository extends JpaRepository<School, Long> {

    Optional<School> findSchoolBySchoolName(String schoolName);

    Optional<School> findSchoolByManagerPhoneNumber(String managerPhoneNumber);

    void deleteSchoolBySchoolId(int schoolId);

    void deleteSchoolByManagerPhoneNumber(String phoneNumber);
}
