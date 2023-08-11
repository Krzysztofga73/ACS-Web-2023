package com.example.accesscontrolsystem.repository;

import com.example.accesscontrolsystem.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends JpaRepository<UserModel, Long> {
}
