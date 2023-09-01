package com.root.dbservice.repo;

import com.root.dbservice.entities.ProductChildPartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductChildPartRepo extends JpaRepository<ProductChildPartEntity, Long> {
}
