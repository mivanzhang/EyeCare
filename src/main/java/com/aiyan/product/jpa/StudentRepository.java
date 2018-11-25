package com.aiyan.product.jpa;

import com.aiyan.product.bean.School;
import com.aiyan.product.bean.Student;
import com.aiyan.product.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    void deleteStudentByStudentId(int studentID);

    Optional<Student> findStudentByStudentId(int studentID);

    Optional<Student> findStudentByStudentNo(int studentNo);

    Optional<List<Student>> findStudentByNameAndBirthDay(String name, String birthday);

    Optional<List<Student>> findStudentByNameAndStudentNo(String name, String studentno);

    Optional<List<Student>> findStudentByNameAndSchool(String name, School school);

    Optional<List<Student>> findStudentBySchoolAndStudentNo(School school, String studentNo);
    Optional<List<Student>> findStudentBySchool(School school);
}
