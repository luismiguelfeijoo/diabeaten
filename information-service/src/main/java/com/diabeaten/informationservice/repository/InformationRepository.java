package com.diabeaten.informationservice.repository;

import com.diabeaten.informationservice.model.Information;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InformationRepository extends JpaRepository<Information, Long> {
}
