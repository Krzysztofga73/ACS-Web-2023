package com.example.accesscontrolsystem.repository;

import com.example.accesscontrolsystem.model.AcsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcsRepository extends JpaRepository<AcsModel, Long> {
}
