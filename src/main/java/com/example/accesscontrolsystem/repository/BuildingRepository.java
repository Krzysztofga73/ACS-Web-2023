package com.example.accesscontrolsystem.repository;

import com.example.accesscontrolsystem.model.BuildingModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingRepository extends JpaRepository<BuildingModel, Long> {
}
