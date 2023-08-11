package com.example.accesscontrolsystem.repository;

import com.example.accesscontrolsystem.model.AccessCheckResultModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessCheckResultRepository extends JpaRepository<AccessCheckResultModel, Long> {
}
