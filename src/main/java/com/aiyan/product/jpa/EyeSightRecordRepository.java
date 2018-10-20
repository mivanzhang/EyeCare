package com.aiyan.product.jpa;

import com.aiyan.product.bean.EyeSightRecord;
import com.aiyan.product.bean.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EyeSightRecordRepository extends JpaRepository<EyeSightRecord, Long> {
    Optional<EyeSightRecord> findEyeSightRecordByStudentId(int studentId);
    Optional<EyeSightRecord> findEyeSightRecordByRecordId(int recordId);
}
