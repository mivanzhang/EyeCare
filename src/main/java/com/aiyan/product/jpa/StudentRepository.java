package com.aiyan.product.jpa;

import com.aiyan.product.bean.Student;
import com.aiyan.product.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    void deleteStudentByStudentId(int studentID);
}
