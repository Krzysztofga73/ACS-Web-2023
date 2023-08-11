package com.example.accesscontrolsystem.repository;

import com.example.accesscontrolsystem.model.RoomModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<RoomModel, Long> {
}
