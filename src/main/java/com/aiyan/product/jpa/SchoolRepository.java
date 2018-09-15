package com.aiyan.product.jpa;

import com.aiyan.product.bean.Query;
import com.aiyan.product.bean.School;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<School, Long> {

}
