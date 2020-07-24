package com.diabeaten.glucosebolusservice.repository;

import com.diabeaten.glucosebolusservice.model.Bolus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BolusRepository extends JpaRepository<Bolus, Long> {
    List<Bolus> findByUserId(Long userId);

    List<Bolus> findByUserIdAndDateAfter(Long userId, Date date);
}
