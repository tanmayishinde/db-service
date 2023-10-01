package com.root.dbservice.repo;

import com.root.dbservice.entities.ProductChildPartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductChildPartRepo extends JpaRepository<ProductChildPartEntity, Long> {


    List<ProductChildPartEntity> findByproductId(Long productId);
    List<ProductChildPartEntity> findBychildPartId(Long childPartId);
}
