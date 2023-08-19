package com.example.accesscontrolsystem.repository;

import com.example.accesscontrolsystem.model.EntryEgressRecordModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntryEgressRecordRepository extends JpaRepository<EntryEgressRecordModel, Long> {
}
