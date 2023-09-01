package com.root.dbservice.repo;

import com.root.dbservice.entities.MonthlyPlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthlyPlanRepo extends JpaRepository<MonthlyPlanEntity,Long> {
}
