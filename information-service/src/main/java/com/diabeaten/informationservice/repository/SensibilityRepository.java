package com.diabeaten.informationservice.repository;

import com.diabeaten.informationservice.model.Sensibility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensibilityRepository extends JpaRepository<Sensibility, Long> {
}
