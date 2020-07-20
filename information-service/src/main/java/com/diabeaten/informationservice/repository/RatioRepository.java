package com.diabeaten.informationservice.repository;

import com.diabeaten.informationservice.model.Ratio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatioRepository extends JpaRepository<Ratio, Long> {
}
