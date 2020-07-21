package com.diabeaten.glucosebolusservice.repository;

import com.diabeaten.glucosebolusservice.model.Glucose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface GlucoseRepository extends JpaRepository<Glucose, Long> {
    List<Glucose> findByUserId(Long userId);
}
