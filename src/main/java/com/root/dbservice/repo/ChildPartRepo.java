package com.root.dbservice.repo;

import com.root.dbservice.entities.ChildPartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildPartRepo extends JpaRepository<ChildPartEntity, Long> {
}
