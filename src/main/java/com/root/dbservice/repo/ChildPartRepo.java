package com.root.dbservice.repo;

import com.root.dbservice.entities.ChildPartEntity;
import com.root.dbservice.entities.ProductChildPartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChildPartRepo extends JpaRepository<ChildPartEntity, Long> {
    ChildPartEntity findBychildPartId(Long productId);

}
