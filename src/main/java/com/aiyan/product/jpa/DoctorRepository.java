package com.aiyan.product.jpa;

import com.aiyan.product.bean.Doctor;
import com.aiyan.product.bean.School;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findDoctorByManagerName(String managerName);
    Optional<Doctor> findDoctorByDoctorId(int doctorId);
    Optional<Doctor> findDoctorByManagerPhoneNumber(String phoneNum);

    Optional<List<Doctor>> findDoctorByStatus(int status);
}
