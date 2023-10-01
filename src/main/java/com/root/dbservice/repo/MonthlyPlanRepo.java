package com.root.dbservice.repo;

import com.root.dbservice.entities.MonthlyPlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MonthlyPlanRepo extends JpaRepository<MonthlyPlanEntity,Long> {
   // List<MonthlyPlanResponseVO> findCreatedAtBetweenCreationDate(Date CreationDate, Date EndDate);
//    List<MonthlyPlanEntity> findBycreationDateBetween(Date CreationDate, Date EndDate);


//
//    @Query(value = "SELECT * FROM MonthlyPlan\n" +
//            "where CreationDate between :dateFrom AND :dateTo", nativeQuery = true)
//    List<MonthlyPlanEntity> findAllEventsBetweenDate(@Param("dateTo")Date EndDate, @Param("dateFrom")Date CreationDate);
//

    @Query(value = "SELECT * FROM MonthlyPlan\n" +
            "where CreationDate between cast(:dateFrom AS DATE) AND cast(:dateTo AS DATE)", nativeQuery = true)
    List<MonthlyPlanEntity> findBycreationDateBetween(@Param("dateFrom")Date dateFrom,@Param("dateTo")Date dateTo);

    MonthlyPlanEntity findByproductId(Long productId);

}
